package com;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class JavaSystemMonitor{
	
	private CPU cpu;
	private double cpuUsage = 0.0;
	private int cpuCores = 0;
	private int totalRam = 0;
	private int usedRam = 0;
	private double hdd = 0.0;
	private double usedHdd = 0.0;
	private List<CPU> cpus;
	private List<Double> coreUsage;

	private OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

		public JavaSystemMonitor(){
			this.cpuCores = osmxb.getAvailableProcessors();
			this.cpu = new CPU();
			this.cpus = new ArrayList<CPU>();
			this.coreUsage = new ArrayList<Double>();
			for(int i = 0;i < this.cpuCores;i++){this.cpus.add(new CPU());}
			for(int j = 0;j < this.cpuCores;j++){this.coreUsage.add(0.0);}
			try{
				RandomAccessFile reader = new RandomAccessFile("/proc/meminfo","r");
				String read = reader.readLine();
				String[] splt = read.split(" ");
				this.totalRam = Integer.parseInt(splt[9]) / 1024;
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			String line = null;
			String[] strstatus;
			String output;
		        try {
			        String[] cmd = { "/bin/sh", "-c", "df -h" };
			        Process p = Runtime.getRuntime().exec(cmd);
			        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			        line = in.readLine();
				line = in.readLine();
				strstatus = line.split(" ");
				output = strstatus[8];
				this.hdd = Double.parseDouble(output.substring(0,output.length()-1));
				output = strstatus[10];
				this.usedHdd = Double.parseDouble(output.substring(0,output.length()-1));
			       in.close();
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }

		}

		public double getCPU(){
			return this.cpuUsage;
		}

		public int getCores(){
			return this.cpuCores;	
		}

		public double getCoreUsage(int core){
			if(core < this.cpuCores){
				return this.coreUsage.get(core);
			}else{
				return 0.0;
			}
		}

		public int getMaxRAM(){
			return this.totalRam;
		}

		public int getUsedRAM(){
			return this.usedRam;
		}

		public double getHDD(){
			return this.hdd;
		}

		public double getUsedHDD(){
			return this.usedHdd;
		}

		public void update(){
			DecimalFormat df = new DecimalFormat("##.##");
			CPU cpu2 = new CPU();
			List<String> cores = new ArrayList<String>();
			long usage = 0;
			long total = 0;
			String[] toks;
			String load = "";
			try{
				RandomAccessFile reader = new RandomAccessFile("/proc/stat","r");
				load = reader.readLine();

				for(int i = 0;i < this.cpuCores;i++){
					cores.add(reader.readLine());
				
				}

				reader.close();
				}catch(IOException ex){ ex.printStackTrace(); }

				toks = load.split(" ");
				cpu2.idle = Long.parseLong(toks[5]);
				cpu2.total = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4]) + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
				usage = cpu2.total - this.cpu.total;
				total = (cpu2.total + cpu2.idle) - (this.cpu.total + this.cpu.idle);
				this.cpuUsage = Double.parseDouble(df.format(usage * 100.0 / total));
				this.cpu = cpu2;

				for(int j = 0;j < this.cpuCores;j++){
					String[] toks2 = cores.get(j).split(" ");
					long coreUsage = 0;
					long coreTotal = 0;
					CPU core1 = this.cpus.get(j);
					CPU core2 = new CPU();
					core2.idle = Long.parseLong(toks2[4]);
					core2.total = Long.parseLong(toks2[1]) + Long.parseLong(toks2[2]) + Long.parseLong(toks2[3]) + Long.parseLong(toks2[5]) + Long.parseLong(toks2[6]) + Long.parseLong(toks2[7]);
					coreUsage = core2.total - core1.total;
					coreTotal = (core2.total + core2.idle) - (core1.total + core1.idle);
					this.coreUsage.set(j,Double.parseDouble(df.format(coreUsage * 100.0 / coreTotal)));
					this.cpus.set(j,core2);
				}

			try{
				RandomAccessFile reader = new RandomAccessFile("/proc/meminfo","r");
				load = reader.readLine();
				load = reader.readLine();
				String[] mem = load.split(" ");
				//try{Thread.sleep(2000);}catch(Exception e){}
				this.usedRam = Integer.parseInt(mem[10]) / 1024;
				reader.close();
			
			}catch(IOException e){ e.printStackTrace(); }
	
		}

	

}
