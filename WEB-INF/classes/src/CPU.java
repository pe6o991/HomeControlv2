package com;

public class CPU{
	public long idle;
	public long total;

	public CPU(){
		this.idle=0;
		this.total=0;
	}

	public CPU(long idle,long total){
		this.idle = idle;
		this.total = total;
	}

	public CPU(CPU cpu){
		this.idle = cpu.idle;
		this.total = cpu.total;
	}
}
