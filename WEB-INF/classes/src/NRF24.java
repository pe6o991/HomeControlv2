package com;

import com.nrf24lib;
import java.util.*;
import java.io.*;

public class NRF24{
	private nrf24lib radio;
	private static NRF24 INSTANCE = new NRF24();
	private Map<String,byte[]> sendBuffer;
	private Map<String,byte[]> receiveBuffer;
	
	private NRF24(){
		radio = new nrf24lib();
		reset();
		radio.begin();
		radio.setDataRate(nrf24lib.RF24_250KBPS);
		radio.setPALevel(nrf24lib.RF24_PA_MAX);
		radio.enableAckPayload();
		radio.setRetries(5,5);
		sendBuffer = new HashMap<String,byte[]>();
		receiveBuffer = new HashMap<String,byte[]>();
		
	}
	
	public void send(String address,byte[] data){
		this.sendBuffer.put(address,data);
	}
	
	public boolean available(String address){
		 for (Map.Entry<String, byte[]> set : receiveBuffer.entrySet()) {
            if(set.getKey().equals(address))
				return true;
        }
		return false;
	}
	
	public byte[] getData(String address){
		byte[] data = (byte[])receiveBuffer.get(address);
		receiveBuffer.remove(address);
		return data;
	}
	
	public void tick(){
		for (Map.Entry<String, byte[]> set : sendBuffer.entrySet()) {
            String addres = set.getKey();
			byte[] data = set.getValue();
			radio.openWritingPipe(parseAddress(addres));
			//System.out.println("Address:"+addres);
			try{Thread.sleep(10);}catch(InterruptedException e){ e.printStackTrace();}
			if(radio.write(data,data.length)){
				//System.out.println("TX succesfull");
				while(!radio.available());
				byte[] received = radio.read(radio.getDynamicPayloadSize());
				this.receiveBuffer.put(addres,received);
				this.sendBuffer.remove(addres);
			}else{
				byte[] param = {0};
				this.sendBuffer.remove(addres);
				//System.out.println("TX fail");
				//receiveBuffer.put(addres,param);
			}
        }
	}
	
	public static NRF24 getInstance(){
		return INSTANCE;
	}
	
	private void reset(){
		radio.write_register(nrf24lib.CONFIG,8);
		radio.write_register(nrf24lib.EN_AA,63);
		radio.write_register(nrf24lib.EN_RXADDR,3);
		radio.write_register(nrf24lib.SETUP_AW,3);
		radio.write_register(nrf24lib.SETUP_RETR,3);
		radio.write_register(nrf24lib.RF_CH,2);
		radio.write_register(nrf24lib.RF_SETUP,7);
		radio.write_register(nrf24lib.RX_PW_P0,0);
		radio.write_register(nrf24lib.RX_PW_P1,0);
		radio.write_register(nrf24lib.RX_PW_P2,0);
		radio.write_register(nrf24lib.RX_PW_P3,0);
		radio.write_register(nrf24lib.RX_PW_P4,0);
		radio.write_register(nrf24lib.RX_PW_P5,0);
		radio.write_register(nrf24lib.DYNPD,0);
		radio.write_register(nrf24lib.FEATURE,0);
	}
	
	private byte[] parseAddress(String address){
		byte[] adres = new byte[5];
		String[] tmp = address.split(":",0);
		for(int i=0;i<5;i++){
			adres[i] = (byte)Integer.parseInt(tmp[i]);
		}
		return adres;
	}
}