package com;

import java.util.*;
import java.sql.*;
import java.io.*;
import com.SystemMonitor;
import com.TempSensor;
import com.PowerSensor;
import com.IrControl;
import com.RelayControl;

public class DBHelper{
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DB_URL = "jdbc:mariadb://localhost/diplomna2";
	private static final String USER = "admin";
	private static final String PASS = "073703";

	private Connection conn;
	private Statement stmt;

	public DBHelper(){
		conn = null;
		stmt = null;
	}
	
	private void connect(){
		try{
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		}catch(SQLException e){ e.printStackTrace();
		}catch(Exception ee){ ee.printStackTrace(); }
	}
	
	private void disconnect(){
		try{
			if(stmt != null) conn.close();
		}catch(SQLException se){ se.printStackTrace(); }
		
		try{
			if(conn != null) conn.close();
		}catch(SQLException se){ se.printStackTrace(); }	
	}
	
	private int newId(List<Integer> ids){
			int id = 0;
			Collections.sort(ids);  
			for(int i=0;i < ids.size();i++){
				if(ids.get(i) != (i+1)){
					id = i+1;
					break;
				}
			}
			if(id==0)
				return ids.size()+1;
			else
				return id;
	}

	public List<device> getDevices(){
		List<device> temp = new ArrayList<device>();
		String query = "SELECT device_id,device_name,device_options,t.device_type_name,i.interface_type_name,device_address FROM device JOIN interface_type i ON device.interface_type_id=i.interface_type_id JOIN device_type t ON device.device_type_id=t.device_type_id;";
		try{
			connect();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				int id = rs.getInt("device_id");
				String name = rs.getString("device_name");
				String deviceType = rs.getString("device_type_name");
				String intrface  = rs.getString("interface_type_name");
				String address = rs.getString("device_address");
				String options = rs.getString("device_options");
				
				switch(deviceType){
					case "SystemMonitor":
						temp.add(new SystemMonitor(id,name,deviceType,intrface,address,options));
					break;
					case "TempSensor":
						temp.add(new TempSensor(id,name,deviceType,intrface,address,options));
					break;
					case "PowerSensor":
						temp.add(new PowerSensor(id,name,deviceType,intrface,address,options));
					break;
					case "IrControl":
						temp.add(new IrControl(id,name,deviceType,intrface,address,options));
					break;
					case "RelayControl":
						temp.add(new RelayControl(id,name,deviceType,intrface,address,options));
					break;
					case "DoorSensor":
						temp.add(new DoorSensor(id,name,deviceType,intrface,address,options));
					break;
					case "DigitalInput":
						temp.add(new DigitalInput(id,name,deviceType,intrface,address,options));
					break;
					case "AnalogInput":
						temp.add(new AnalogInput(id,name,deviceType,intrface,address,options));
					break;
				}
			}
		}catch(SQLException e){ e.printStackTrace();
		}finally{ disconnect(); }
		
		return temp;
	}

	public void addDevice(String name,String type,String intrface,String address,String options){
		String selectdevice = "SELECT device_id FROM device";
		List<Integer> ids = new ArrayList<Integer>();
		String query;
		try{
			connect();
			ResultSet rs = stmt.executeQuery(selectdevice);
			while(rs.next()){ ids.add(rs.getInt("device_id")); }
			query = "INSERT INTO device(device_id,device_name,device_type_id,interface_type_id,device_address,device_options) VALUES("+newId(ids)+",'"+name+"',"+type+","+intrface+",'"+address+"','"+options+"');";
			stmt.executeUpdate(query);
		}catch(SQLException e){ e.printStackTrace();
		}finally{ disconnect(); }
	}

	public void removeDevice(String id){
		String query = "DELETE FROM device WHERE device_id="+id+";";
		try{
			connect();
			stmt.executeUpdate(query);
		}catch(SQLException e){ e.printStackTrace();
		}finally{ disconnect(); }
	}
	
	public Map<Integer,String> getDeviceTypes(){
		Map<Integer,String> params = new HashMap<>();
		String query = "SELECT * FROM device_type;";
		try{
			connect();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				int id = rs.getInt("device_type_id");
				String name = rs.getString("device_type_name");
				params.put(id,name);
			}
		}catch(SQLException e){ e.printStackTrace(); 
		}finally{ disconnect(); }
		
		return params;
	}
	
	public Map<Integer,String> getInterfaceTypes(){
		Map<Integer,String> params = new HashMap<>();
		String query = "SELECT * FROM interface_type;";
		try{
			connect();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				int id = rs.getInt("interface_type_id");
				String name = rs.getString("interface_type_name");
				params.put(id,name);
			}
		}catch(SQLException e){ e.printStackTrace(); 
		}finally{ disconnect(); }
		
		return params;
	}
	
	public void log(int devid,String data_name,String data){
		String selectdevice = "SELECT event_id FROM log";
		List<Integer> ids = new ArrayList<Integer>();
		String query;
		try{
			connect();
			ResultSet rs = stmt.executeQuery(selectdevice);
			while(rs.next()){ ids.add(rs.getInt("event_id")); }
			query = "INSERT INTO log(event_id,device_id,data_name,data) VALUES("+newId(ids)+","+devid+",'"+data_name+"','"+data+"');";
			stmt.executeUpdate(query);
		}catch(SQLException e){ e.printStackTrace();
		}finally{ disconnect(); }
	}
	
	public void deleteLog(){
		String query;
		try{
			connect();
			query = "DELETE FROM log;";
			stmt.executeUpdate(query);
		}catch(SQLException e){ e.printStackTrace();
		}finally{ disconnect(); }
	}
	
	public void updateDeviceAddress(String address,String name){
		String selectdevice = "SELECT device_id,device_name FROM device;";
		String query = "";
		try{
			connect();
			ResultSet rs = stmt.executeQuery(selectdevice);
			while(rs.next()){ 
				int id = rs.getInt("device_id");
				String device_name = rs.getString("device_name");
				if(device_name.equals(name)){
					query = "UPDATE device SET device_address="+address+" WHERE device_id="+id+";";
				}
			}
			stmt.executeUpdate(query);
		}catch(SQLException e){ e.printStackTrace();
		}finally{ disconnect(); }
	}
}
