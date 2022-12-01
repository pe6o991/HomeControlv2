package com;

import com.device;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.nio.*;

public class TempSensor extends device{
	
	private float temperature;
	private float humidity;
	private float pressure;
	private float max_temperature;
	private float max_pressure;
	
	private boolean sent;
	private NRF24 radio;
	private byte[] command;
	private int timeout;
	private boolean log;
	
	public TempSensor(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		this.temperature = (float)0.0;
		this.humidity = (float)0.0;
		this.pressure = (float)0.0;
		this.command = new byte[1];
		this.log=true;
		
		if(super.getOptions().equals("")){
			this.max_pressure = (float)1500.0;
			this.max_temperature = (float)50.0;
		}else{
			parseOptions();
		}
	}
	
	private void parseOptions(){
		String[] options = super.getOptions().split(",");
		
		for(int i=0;i<options.length;i++){
			String[] hash = options[i].split("=");
			if(hash.length==2){
				switch(hash[0]){
					case"max_temp":
						this.max_temperature = Integer.parseInt(hash[1]);
					break;
					case"max_pressure":
						this.max_pressure = Integer.parseInt(hash[1]);
					break;
				}
			}
		}
		if(this.max_temperature==0.0) this.max_temperature = (float)50.0;
		if(this.max_pressure==0.0) this.max_pressure = (float)1500;
	}

	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		html.append("<div class='TempSensor'><div class='title'>"+super.getName()+"</div>");
		html.append("<div><span>Temperature:</span><div class='value'><span id='"+id+"_temperature'>"+this.temperature+"*C</span><div class='bar' id='temp'></div></div><span>"+this.max_temperature+"*C</span></div>");
		html.append("<div><span>Humidity:</span><div class='value'><span id='"+id+"_humidity'>"+this.humidity+"%</span><div class='bar' id='humidity'></div></div><span>100%</span></div>");
		html.append("<div><span>Pressure:</span><div class='value'><span id='"+id+"_pressure'>"+this.pressure+"hPa</span><div class='bar' id='pressure'></div></div><span>"+this.max_pressure+" hPa</span></div></div>");
		
		return html.toString();
	}

	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		int id = super.getId();
		DecimalFormat df = new DecimalFormat("#.##");

		params.put(id+"_temperature",df.format(this.temperature)+"*C");
		params.put(id+"_humidity",df.format(this.humidity)+"%");
		params.put(id+"_pressure",df.format(this.pressure)+"hPa");
		
		params.put(id+"_temperature-bar",(this.temperature/this.max_temperature)*100 + "%");
		params.put(id+"_humidity-bar",df.format(this.humidity)+"%");
		params.put(id+"_pressure-bar",(this.pressure/this.max_pressure)*100 + "%");
		
		return params;
	}
	
	public void tick(){
		if(log){
			
		}
		switch(super.getInterface()){
			case "nrf24":
				if(this.sent){
					timeout-=100;
					String adres = super.getAddress();
					if(radio.available(adres)){
						byte[] param = radio.getData(adres);
						if(param.length==16)
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
		String status = "OK";
		
		return status;
	}
	
	public boolean compare(String param,String operation,String value){
		boolean ret = false;
		float IntParam = (float)0.0;
		float ExtParam = (float)0.0;
		
		switch(param){
			case "temperature":
				IntParam = this.temperature;
				ExtParam = Float.parseFloat(value);
			break;
			case "humidity":
				IntParam = this.humidity;
				ExtParam = Float.parseFloat(value);
			break;
			case "pressure":
				IntParam = this.pressure;
				ExtParam = Float.parseFloat(value);
			break;
			
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
		byte[] temp = new byte[4];
		byte[] humid = new byte[4];
		byte[] press = new byte[4];
		
			for(int i=0;i<4;i++)
				temp[i]=data[i];
			
			for(int i=0;i<4;i++)
				humid[i]=data[i+4];
			
			for(int i=0;i<4;i++)
				press[i]=data[i+8];
			
			
		this.temperature = ByteBuffer.wrap(temp).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		this.humidity = ByteBuffer.wrap(humid).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		this.pressure = ByteBuffer.wrap(press).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
}