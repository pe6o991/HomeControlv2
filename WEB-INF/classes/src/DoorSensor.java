package com;

import com.device;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.nio.*;

public class DoorSensor extends device{
	
	private String doorStatus;
	private boolean log;
	
	public DoorSensor(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		this.doorStatus="closed";
		this.log=true;
	}

	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		html.append("<div class='DoorSensor'><div class='title'>"+super.getName()+"</div>");
		html.append("<div><span>Door:</span><div class='value'><span id='"+id+"_door'>"+this.doorStatus+"</span></div></div></div>");
		
		return html.toString();
	}

	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		int id = super.getId();
		params.put(id+"_door",this.doorStatus);
		
		return params;
	}
	
	public void tick(){
		switch(super.getInterface()){
			case "nrf24":
				
			break;
			case "wifi":
			 String adres = super.getAddress();
			 String data;
			 if(wifi.available(adres)){
				 data = wifi.getData(adres);
				 parse(data);
			 }
			break;
			case "none":
			
			break;
		}
	}
	
	public String send(String command){
		return "OK";
	}
	
	public boolean compare(String param,String operation,String value){
		boolean ret = false;
		
		if(this.doorStatus.equals(value)){
			ret=true;
		}
		
		return ret;
	}
	
	private void parse(String data){
		if(data.equals("open")){
			this.doorStatus = "open";
		}else{
			this.doorStatus = "closed";
		}
	}
}