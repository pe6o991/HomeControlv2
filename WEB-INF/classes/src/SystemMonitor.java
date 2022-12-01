package com;

import com.device;
import java.io.*;
import java.util.*;

public class SystemMonitor extends device{
	
	private JavaSystemMonitor mon;	

	public SystemMonitor(int id,String name,String type,String intrface,String address,String options){
		super(id,name,type,intrface,address,options);
		mon = new JavaSystemMonitor();
	}

	public String toHTML(){
		StringBuilder html = new StringBuilder();
		int id = super.getId();
		html.append("<div class='SystemMonitor'><div class='title'>" + super.getName() + "</div>");
		html.append("<div><span>All CPU:</span><div class='value'><span id='"+id+"_cpu'>"+mon.getCPU()+"%</span><div class='bar' id='cpu'></div></div><span>100%</span></div>");
		for(int i=0;i<mon.getCores();i++){
			html.append("<div><span>CPU"+i+":</span><div class='value'><span id='"+id+"_cpu"+i+"'>"+mon.getCoreUsage(i)+"%</span><div class='bar' id='cpu'></div></div><span>100%</span></div>");
		}
		html.append("<div><span>RAM:</span><div class='value'><span id='"+id+"_ram'>"+mon.getUsedRAM()+"MB</span><div class='bar' id='ram'></div></div><span id='"+id+"_ramMax'>"+mon.getMaxRAM()+"MB</span></div>");
		html.append("<div><span>HDD:</span><div class='value'><span id='"+id+"_hdd'>"+mon.getUsedHDD()+"G</span><div class='bar' id='hdd'></div></div><span id='"+id+"_hddMax'>"+mon.getHDD()+"G</span></div></div>");
		return html.toString();
	}

	public Map<String,String> getParam(){
		Map<String,String> params = new HashMap<>();
		int id = super.getId();
		int ram = mon.getUsedRAM();
		int maxram = mon.getMaxRAM();
		
		params.put(id+"_cpu",mon.getCPU() +"%");
		params.put(id+"_cpu-bar",mon.getCPU() +"%");
		
		for(int i=0;i<mon.getCores();i++){
			params.put(id+"_cpu"+i,mon.getCoreUsage(i) +"%");
			params.put(id+"_cpu"+i+"-bar",mon.getCoreUsage(i) +"%");
		}
		
		params.put(id+"_ram",ram+"MB");
		params.put(id+"_hdd",mon.getUsedHDD()+"G");	
		
		params.put(id+"_hdd-bar",(mon.getUsedHDD()/mon.getHDD())*100+"%");	
		params.put(id+"_ram-bar",((double)ram/maxram)*100+"%");
		
		return params;
	}
	
	public void tick(){
		mon.update();
	}
	
	public String send(String command){
		String status = "OK";
		
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
