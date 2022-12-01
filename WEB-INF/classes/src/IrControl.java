package com;

import com.device;
import com.DBHelper;
import com.wifi;
import java.io.*;
import java.util.*;

public class IrControl extends device{
	private List<String> controls;
	private boolean validOptions;
	private NRF24 radio;
	
	public IrControl(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		controls = new ArrayList<String>();
		radio = NRF24.getInstance();
		validOptions = true;
		parseOptions();
	}
	
	private void parseOptions(){
		String[] temp = super.getOptions().split(",");
		if(temp.length==0){
			this.validOptions=false;
			return;
		}
		for(int i=0;i < temp.length;i++){
			this.controls.add(temp[i]);
		}
	}

	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		html.append("<div class='IrControl'><div class='title'>"+super.getName()+"</div>");
		char quotes = '"';
		if(controls.size()==1){
			html.append("<div><span><button>"+this.controls.get(0)+"</button></span></div></div>");
		}else{
			for(int i=0;i < this.controls.size()-1;i++){
				if((i+1)%2==0){
					html.append("<div><span><button onclick='send("+quotes+id+"_"+this.controls.get(i)+quotes+")'>"+this.controls.get(i)+"</button></span><span><button onclick='send("+quotes+id+"_"+this.controls.get(i+1)+quotes+")'>"+this.controls.get(i+1)+"</button></span></div>");
					i++;
				}else{
					html.append("<div><span><button onclick='send("+quotes+id+"_"+this.controls.get(i)+quotes+")'>"+this.controls.get(i)+"</button></span></div>");
				}
			}
			html.append("</div>");
		}
		return html.toString();
	}

	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		
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
		String status = "OK";
		DBHelper db = new DBHelper();
		db.log(super.getId(),"command",command);
		
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
		
		switch(operation){
			case "==":
			
			break;
			case ">=":
			
			break;
			case "<=":
			
			break;
			case ">":
			
			break;
			case "<":
			
			break;
		}
		
		return ret;
	}
}