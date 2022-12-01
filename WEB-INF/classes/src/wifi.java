package com;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.net.*;  
//import java.nio.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.device;

public class wifi extends HttpServlet{
	private static Map<String,String> sendBuffer = new HashMap<String,String>();
	private static Map<String,String> receiveBuffer = new HashMap<String,String>();
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public static boolean available(String address){
		 for (Map.Entry<String, String> set : receiveBuffer.entrySet()) {
            if(set.getKey().equals(address))
				return true;
        }
		return false;
	}
	
	public static String getData(String address){
		String data = receiveBuffer.get(address);
		receiveBuffer.remove(address);
		return data;
	}
	
	public static void send(String address,String data){
		sendBuffer.put(address,data);
	}
	
	public static void tick(){
		for (Map.Entry<String, String> set : sendBuffer.entrySet()) {
            String addres = set.getKey();
			try {
            URL url = new URL("http://"+addres+"/data");
            String postData = "data="+set.getValue();
 
			System.out.println(addres);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			//StringJoiner sj = new StringJoiner("&");
			//sj.add(URLEncoder.encode(postData));
			//byte[] out = sj.toString().getBytes();
			byte[] out = postData.getBytes();
			int length = out.length;
			
			conn.setFixedLengthStreamingMode(length);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			conn.connect();
			
			OutputStream os = conn.getOutputStream();
			os.write(out);
			
			//Thread.sleep(3000);
			System.out.println("code:"+conn.getResponseCode());

            
        } catch (Exception e) {
            e.printStackTrace();
			System.out.println(e.toString());
        }
			sendBuffer.remove(addres);  
        }
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		String address = req.getRemoteAddr();
		String data = req.getParameter("data");
		receiveBuffer.put(address,data);
		out.write("OK");
		out.close();
	}
	
}