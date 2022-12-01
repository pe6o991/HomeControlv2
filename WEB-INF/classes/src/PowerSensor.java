package com;

import com.device;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.nio.*;

public class PowerSensor extends device{
	
	private double volts;
	private double amps;
	private double power;
	private double energy;
	
	private double max_volts;
	private double max_amps;
	private double max_power;
	
	private boolean sent;
	private NRF24 radio;
	private byte[] command;
	private int timeout;
	private boolean log;
	
	public PowerSensor(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		this.volts = 0.0;
		this.amps =0.0;
		this.power = 0.0;
		this.energy = 0.0;
		this.sent=false;
		radio = NRF24.getInstance();
		this.command = new byte[1];
		this.log=true;
		
		if(super.getOptions().equals("")){
			this.max_volts = 260;
			this.max_amps = 100;
			this.max_power = 3000;
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
					case"max_volts":
						this.max_volts = Integer.parseInt(hash[1]);
					break;
					case"max_amps":
						this.max_amps = Integer.parseInt(hash[1]);
					break;
					case"max_power":
						this.max_power = Integer.parseInt(hash[1]);
					break;
				}
			}
		}
		if(this.max_volts==0.0) this.max_volts = 260.0;
		if(this.max_amps==0.0) this.max_amps = 100;
		if(this.max_power==0.0) this.max_power = 1500;
	}

	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		html.append("<div class='PowerSensor'><div class='title'>"+super.getName()+"</div>");
		html.append("<div><span>Volts:</span><div class='value'><span id='"+id+"_volts'>"+this.volts+"V</span><div class='bar' id='volts'></div></div></div>");
		html.append("<div><span>Amps:</span><div class='value'><span id='"+id+"_amps'>"+this.amps+"A</span><div class='bar' id='amps'></div></div></div>");
		html.append("<div><span>Power:</span><div class='value'><span id='"+id+"_power'>"+this.power+"W</span><div class='bar' id='power'></div></div></div>");
		html.append("<div><span>Energy:</span><div class='value'><span id='"+id+"_energy'>"+this.energy+"kWh</span><div class='bar' id='energy'></div></div></div></div>");
		return html.toString();
	}

	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		int id = super.getId();
		DecimalFormat df = new DecimalFormat("#.##");
		params.put(id+"_volts",df.format(this.volts)+"V");
		params.put(id+"_amps",df.format(this.amps)+"A");
		params.put(id+"_power",df.format(this.power)+"W");
		params.put(id+"_energy",df.format(this.energy)+"kWh");
		
		params.put(id+"_volts-bar",(this.volts/this.max_volts)*100+"%");
		params.put(id+"_amps-bar",(this.amps/this.max_amps)*100+"%");
		params.put(id+"_power-bar",(this.power/this.max_power)*100+"%");
		
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
		return "OK";
	}
	
	public boolean compare(String param,String operation,String value){
		boolean ret = false;
		double IntParam = 0.0;
		double ExtParam = 0.0;
		
		switch(param){
			case "volts":
				IntParam = this.volts;
				ExtParam = Double.parseDouble(value);
			break;
			case "amps":
				IntParam = this.amps;
				ExtParam = Double.parseDouble(value);
			break;
			case "power":
				IntParam = this.power;
				ExtParam = Double.parseDouble(value);
			break;
			case "energy":
				IntParam = this.energy;
				ExtParam = Double.parseDouble(value);
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
		byte[] volts = new byte[4];
		byte[] amps = new byte[4];
		byte[] power = new byte[4];
		byte[] energy = new byte[4];
		
			for(int i=0;i<4;i++)
				volts[i]=data[i];
			
			for(int i=0;i<4;i++)
				amps[i]=data[i+4];
			
			for(int i=0;i<4;i++)
				power[i]=data[i+8];
			
			for(int i=0;i<4;i++)
				energy[i]=data[i+12];
			
		this.volts = ByteBuffer.wrap(volts).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		this.amps = ByteBuffer.wrap(amps).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		this.power = ByteBuffer.wrap(power).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		this.energy = ByteBuffer.wrap(energy).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
}