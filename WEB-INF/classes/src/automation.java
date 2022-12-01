package com;

import com.main;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class automation{
	private int device1;
	private int device2;
	private String operation;
	private String device1Param;
	private String device2Param;
	private String value;
	
	private boolean triggered;
	
	public automation(String device1,String device2,String operation,String device1Param,String device2Param,String value){
		this.device1 = Integer.parseInt(device1);
		this.device2 = Integer.parseInt(device2);
		this.operation = operation;
		this.device1Param = device1Param;
		this.device2Param = device2Param;
		this.value = value;
		
		this.triggered=false;
	}
	
	public void tick(){
		if(device1!=0){
			for(device dev : main.devices){
				if(this.device1==dev.getId()){
					if(dev.compare(this.device1Param,this.operation,this.value)){
						if(!this.triggered){
							sendCommand(device2,device2Param);
						}
						this.triggered=true;
					}else{
						this.triggered=false;
					}
				}
			}
		}else{
			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
			String[] formattedDate = myDateObj.format(myFormatObj).split(":");
			String[] time = value.split(":");
			//System.out.println("After formatting: " + formattedDate);
			if(time.length>=2){
				if(time[0].equals("**")){
					if(time[1].equals(formattedDate[1])){
						if(!this.triggered){
							sendCommand(device2,device2Param);
						}
						this.triggered=true;
					}else{
						this.triggered=false;
					}
				}else{
					if(time[0].equals(formattedDate[0]) && time[1].equals(formattedDate[1])){
						if(!this.triggered){
							sendCommand(device2,device2Param);
						}
						this.triggered=true;
					}else{
						this.triggered=false;
					}
				}
			}
		}
	}
	
	private void sendCommand(int id,String command){
		for(device dev : main.devices){
			if(this.device2==dev.getId()){
				dev.send(command);
				return;
			}
		}
	}
	
	public String getDevice1(){
		String tmp = " ";
		if(this.device1!=0){
			for(device dev : main.devices){
				if(this.device1==dev.getId()){
					tmp = dev.getName();
					break;
				}
			}
		}else{
			tmp="Time";
		}
		return tmp;
	}
	
	public String getDevice2(){
		String tmp = " ";
		for(device dev : main.devices){
			if(this.device2==dev.getId()){
				tmp = dev.getName();
				break;
			}
		}
		return tmp;
	}
	
	public String getOperation(){
		return this.operation;
	}
	
	public String getDevice1Param(){
		return this.device1Param;
	}
	
	public String getDevice2Param(){
		return this.device2Param;
	}
	
	public String getValue(){
		return this.value;
	}
}