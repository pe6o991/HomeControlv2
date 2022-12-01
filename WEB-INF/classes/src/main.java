package com;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.concurrent.*;
import com.google.gson.Gson;
import com.device;
import com.automation;
import com.wifi;

public class main extends HttpServlet implements Runnable {

	public static List<device> devices = new ArrayList<device>();
	public static List<automation> automations = new ArrayList<automation>();

	private Thread updater;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		DBHelper db = new DBHelper();
		devices = db.getDevices();

		updater= new Thread(this);
		updater.setPriority(Thread.MIN_PRIORITY);
		updater.start();
		System.out.println("###########Main Servlet Loaded##########");
		System.out.println("############# NUM OF Devices:" + devices.size() + " ##############");
	}

	public void run() {
		NRF24 radio = NRF24.getInstance();
		while(true){
			for(device dev : devices){
				dev.tick();
			}
			for(automation automat : automations){
				automat.tick();
			}
			radio.tick();
			wifi.tick();
			try{Thread.sleep(500);}catch(Exception e){ e.printStackTrace(); }
		}

	} 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String type = req.getParameter("type");
		PrintWriter out = res.getWriter();
		DBHelper db = new DBHelper();
		
		switch(type){
			case "read":
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				Gson encoder = new Gson();
				Map<String,String> data = new HashMap<>();
				for(int i=0;i<devices.size();i++) data.putAll(devices.get(i).getParam());
				out.write(encoder.toJson(data));
				out.close();
			break;
			case "add":
				String name = req.getParameter("name");
				String devtype = req.getParameter("devtype");
				String intrface = req.getParameter("interface");
				String address = req.getParameter("address");
				String options = req.getParameter("options");
				db.addDevice(name,devtype,intrface,address,options);
				devices = db.getDevices();
				out.write("OK");
				out.close();
			break;
			case "remove":
				String id = req.getParameter("id");
				db.removeDevice(id);
				out.write("Removed Device");
				devices = db.getDevices();
				out.close();
			break;
			case "command":
				String command = req.getParameter("command");
				String iD = command.substring(0,1);
				String dat = command.substring(2);
				int ID = Integer.parseInt(iD);
				for(device dev : devices){
					if(dev.getId()==ID)
						out.write(dev.send(dat));
						
				}
				out.close();
			break;
			case "automation":
				String device1 = req.getParameter("device1");
				String device2 = req.getParameter("device2");
				String operation = req.getParameter("operation");
				String device1Param = req.getParameter("device1Param");
				String device2Param = req.getParameter("device2Param");
				String value = req.getParameter("value");
				
				automations.add(new automation(device1,device2,operation,device1Param,device2Param,value));
				out.write("OK");
				out.close();
			break;
			case "rmrule":
				int index = Integer.parseInt(req.getParameter("id"));
				automations.remove(index);
				out.write("OK");
				out.close();
			break;
			case "dellog":
				db.deleteLog();
				out.write("Deleted Logs");
				out.close();
			break;
			case "register":
				String adress = req.getRemoteAddr();
				String device_name = req.getParameter("name");
				db.updateDeviceAddress(adress,device_name);
				for(device dev : devices){
					if(dev.getName().equals(device_name))
						dev.setAddress(adress);
				}
				out.write("Successfull register");
				out.close();
			break;
			case "log":
				
				out.write("OK");
				out.close();
			break;
			
		}
	}

	public void destroy() { updater.stop(); }
}
