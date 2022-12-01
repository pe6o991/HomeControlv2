package com;

import com.device;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.nio.*;

public class AnalogInput extends device{
	private List<String> Inputs;
	private List<Double> InputStatus;
	private boolean validOptions;
	
	private NRF24 radio;
	private boolean sent;
	private byte[] command;
	private int timeout;
	private boolean log;
	
	public AnalogInput(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		radio = NRF24.getInstance();
		Inputs = new ArrayList<String>();
		InputStatus = new ArrayList<Double>(); 
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
			this.InputStatus.add(0.0);
		}
	}
	
	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		
		html.append("<div class='AnalogInput'><div class='title'>"+super.getName()+"</div>");
		
		if(this.validOptions==true){
			for(int i=0;i< this.Inputs.size();i++){
				html.append("<div><span>"+this.Inputs.get(i)+"=</span><span id='"+id+"_"+this.Inputs.get(i)+"'>"+this.InputStatus.get(i)+" V</span></div>");
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
		DecimalFormat df = new DecimalFormat("#.##");
		
		for(int i=0;i< this.Inputs.size();i++){
			params.put(id+"_"+this.Inputs.get(i),df.format(this.InputStatus.get(i))+" V");
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
						if(param.length%4==0)
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
		double IntParam = 0.0;
		double ExtParam = Double.parseDouble(value);
		
		for(int i=0;i < this.InputStatus.size();i++){
			if(this.Inputs.get(i).equals(param)){
				IntParam = this.InputStatus.get(i);
			}
		}
		
		switch(operation){
			case "==":
				if(IntParam==ExtParam) ret=true;
			break;
			case ">=":
				if(IntParam>=ExtParam) ret=true;
			break;
			case "<=":
				if(IntParam<=ExtParam) ret=true;
			break;
			case ">":
				if(IntParam>ExtParam) ret=true;
			break;
			case "<":
				if(IntParam<ExtParam) ret=true;
			break;
		}
		
		return ret;
	}
	
	private void nrfparse(byte[] data){
		int index = 0;
		
		for(int i=0;i<data.length;i+=4){
			byte[] value = new byte[4];
			for(int j=0;j<4;j++)
				value[i]=data[i+j];
			
			this.InputStatus.set(index,(double)ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN).getFloat());
			index++;
		}
	}
}