package com;

import com.device;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.nio.*;

public class DigitalInput extends device{
	private List<String> Inputs;
	private List<String> InputStatus;
	private boolean validOptions;
	
	private NRF24 radio;
	private boolean sent;
	private byte[] command;
	private int timeout;
	private boolean log;
	
	public DigitalInput(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		radio = NRF24.getInstance();
		Inputs = new ArrayList<String>();
		InputStatus = new ArrayList<String>(); 
		validOptions = true;
		parseOptions();
	}
	
	private void parseOptions(){
		String temp[] = super.getOptions().split(",");
		if(temp.length==1 && !super.getOptions().equals("")){
			this.validOptions=false;
			return;
		}
		for(int i=0;i < temp.length;i++){
			this.Inputs.add(temp[i]);
			this.InputStatus.add("LOW");
		}
	}
	
	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		
		html.append("<div class='DigitalInput'><div class='title'>"+super.getName()+"</div>");
		
		if(this.validOptions==true){
			for(int i=0;i< this.Inputs.size();i++){
				html.append("<div><span>"+this.Inputs.get(i)+"=</span><span id='"+id+"_"+this.Inputs.get(i)+"'>"+this.InputStatus.get(i)+"</span></div>");
			}
			html.append("</div>");
		}else{
			html.append("<div><span>Invalid Options</span></div></div>");
		}
		
		return html.toString();
	}
	
	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		int id = super.getId();
		
		for(int i=0;i< this.Inputs.size();i++){
			params.put(id+"_"+this.Inputs.get(i),this.InputStatus.get(i));
		}
		
		return params;
	}
	
	public void tick(){
		switch(super.getInterface()){
			case "nrf24":
				if(this.sent){
					timeout-=100;
					String adres = super.getAddress();
					if(radio.available(adres)){
						byte[] param = radio.getData(adres);
						if(param.length==4)
							nrfparse(param);
						}
					
					if(timeout <=0) sent=false;
				}else{
					this.command[0]=(byte)8;
					radio.send(super.getAddress(),command);
					this.sent = true;
					timeout=8000;
				}
			break;
			case "wifi":
			
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
		String IntParam="";
		
		for(int i=0;i < this.InputStatus.size();i++){
			if(this.Inputs.get(i).equals(param)){
				IntParam = this.InputStatus.get(i);
			}
		}
		
		if(IntParam.equals(value)){
			ret=true;
		}
		
		return ret;
	}
	
	private void nrfparse(byte[] data){
		for(int i=0;i< this.Inputs.size();i++){
			byte dat  =data[i];
			if(dat==0){
				this.InputStatus.set(i,"LOW");
			}else{
				this.InputStatus.set(i,"HIGH");
			}
		}
	}
}