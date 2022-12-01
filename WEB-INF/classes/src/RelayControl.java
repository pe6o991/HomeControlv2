package com;

import com.device;
import com.wifi;
import java.io.*;
import java.util.*;

public class RelayControl extends device{
	private List<String> controls;
	private List<String> controlStatus;
	private boolean validOptions;
	private NRF24 radio;
	
	public RelayControl(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		radio = NRF24.getInstance();
		controls = new ArrayList<String>();
		controlStatus = new ArrayList<String>(); 
		validOptions = true;
		parseOptions();
	}

	private void parseOptions(){
		String temp[] = super.getOptions().split(",");
		if(temp.length==0){
			this.validOptions=false;
			return;
		}
		for(int i=0;i < temp.length;i++){
			this.controls.add(temp[i]);
			this.controlStatus.add("off");
		}
	}
	
	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		char quotes = '"';
		html.append("<div class='RelayControl'><div class='title'>"+super.getName()+"</div>");
		if(this.validOptions==true){
			for(int i=0;i< this.controls.size();i++){
				html.append("<div><span>"+this.controls.get(i)+"</span><span><button id='"+id+"_Relay_"+i+"_on"+"' onclick='send("+quotes+id+"_Relay_"+i+"_on"+quotes+")'>On</button><button id='"+id+"_Relay_"+i+"_off"+"' onclick='send("+quotes+id+"_Relay_"+i+"_off"+quotes+")'>Off</button></span></div>");
			}
			html.append("</div>");
		}else{
			html.append("<div><span>Invalid Options</span></div></div>");
		}
		//html.append("<div><span>Relay1:</span><span><button>On</button><button>Off</button></span></div></div>");
		return html.toString();
	}

	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		int id = super.getId();
		
		for(int i=0;i< this.controls.size();i++){
				String id1 = id + "_Relay_"+i+"_off-btn";
				String id2 = id + "_Relay_"+i+"_on-btn";
				
				if(this.controlStatus.get(i).equals("on")){
					params.put(id1,"off");
					params.put(id2,"on");
				}else{
					params.put(id1,"on");
					params.put(id2,"off");
				}
			}
		
		return params;
	}
	
	public void tick(){
		switch(super.getInterface()){
			case "nrf24":
				if(radio.available(super.getAddress())){
					byte[] data = radio.getData(super.getAddress());
				}
			break;
			case "wifi":
			
			break;
			case "none":
			
			break;
		}
	}
	
	public String send(String command){
		String status = command;
		String[] spltcmd = command.split("_");
		String btn = spltcmd[2];
		int pos = Integer.parseInt(spltcmd[1]);
		DBHelper db = new DBHelper();
		db.log(super.getId(),this.controls.get(pos),command);
		
		if(btn.equals("off")){
			this.controlStatus.set(pos,"off");
		}else{
			this.controlStatus.set(pos,"on");
		}
		switch(super.getInterface()){
			case "nrf24":
				radio.send(super.getAddress(),command.getBytes());
			break;
			case "wifi":
				wifi.send(super.getAddress(),command);
			break;
			case "none":
			
			break;
		}
		return status;
	}
	
	public boolean compare(String param,String operation,String value){
		boolean ret = false;
		
		return ret;
	}
}