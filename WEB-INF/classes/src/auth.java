package com;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class auth extends HttpServlet implements Runnable {

	public static Map<String,Integer> authenticated = new HashMap<String,Integer>();
	private Thread deauth;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		authenticated.put("192.168.x.x",300);
		deauth = new Thread(this);
		deauth.setPriority(Thread.MIN_PRIORITY);
		deauth.start();
		System.out.println("##### Authentication servlet loaded #####");
	}

	public void run() {
		while(true){
			for(String key : authenticated.keySet()){
				//authenticated.put(key,authenticated.get(key)-1);
				authenticated.computeIfPresent(key,(k,v)->v-1);
				if(authenticated.get(key)<=0){
					authenticated.remove(key);
				}
			}
			try{
			deauth.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}	
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = request.getRemoteAddr();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("You have:"+authenticated.get(address)+" seconds left.");
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String address = req.getRemoteAddr();
		String token = req.getParameter("token");
		if(token.equals("073703")){
			authenticated.put(address,300);
			res.sendRedirect("index.jsp");
		}	
	}

	public void destroy() {
		deauth.stop();	
	}
}
