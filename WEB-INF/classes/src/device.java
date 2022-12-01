package com;

import java.io.*;
import java.util.*;

public abstract class device{

	private int deviceId;
	private String name;
	private String type;
	private String intrface;
	private String address;
	private String options;

	public device(){
		this.deviceId = 0;
		this.name="Default";
		this.type="TempSensor";
		this.intrface="NRF24";
		this.address="localhost";
		this.options = " ";
	}

	public device(int deviceId,String name,String type,String intrface,String address,String options){
		this.deviceId = deviceId; 
		this.name = name;
		this.type = type;
		this.intrface = intrface;
		this.address = address;
		this.options = options;
	}

	public int getId(){ return this.deviceId; }

	public String getName(){ return this.name; }

	public String getType(){ return this.type; }

	public String getInterface(){ return this.intrface; }

	public String getAddress(){ return this.address; }
	
	public String getOptions(){ return this.options; }
	
	public void setAddress(String address){ this.address=address; }

	abstract public String toHTML();
	abstract public Map<String,String> getParam();
	abstract public void tick();
	abstract public String send(String command);
	abstract public boolean compare(String param,String operation,String value);
}
