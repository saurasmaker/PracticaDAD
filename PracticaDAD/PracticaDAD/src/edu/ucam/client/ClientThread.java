package edu.ucam.client;

import java.io.BufferedReader;
import java.io.IOException;

import edu.ucam.client.frames.ClientFrame;

public class ClientThread extends Thread{
	
	//Atributes
	private ClientFrame clientFrame;
	private BufferedReader br;
	private Boolean suspended = false, paused = false;
	
	//Constructors
	public ClientThread(ClientFrame clientFrame, BufferedReader br) {
		this.setBr(br);
		System.out.println("Puentes creados");
		this.setClientFrame(clientFrame);
		System.out.println("Enlazado con frame");
	}
	
	
	//Methods
	public void run() {
				
		while(!paused) {
			
			//Control del bucle
			try {
				synchronized(this){
					while(this.isPaused()) 
						wait();
					
					if(this.isSuspended())
						break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/*************************************/
			
			
			//Cuerpo
			readMessage();
			
		}
		
	}


	public void readMessage() {
		
		String message = null;
		try {
			message = this.br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//this.clientFrame.getEditorPaneLog().setText(this.clientFrame.getEditorPaneLog().getText() + "\n" + message); 
		this.clientFrame.repaint();
		
		
		return;
	}
	
	//Getters & Setters
	public Boolean isPaused() {
		return this.paused;
	}


	public void setPaused(Boolean paused) {
		this.paused = paused;
	}
	
	public Boolean isSuspended() {
		return this.suspended;
	}


	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}


	public ClientFrame getClientFrame() {
		return this.clientFrame;
	}


	public void setClientFrame(ClientFrame clientFrame) {
		this.clientFrame = clientFrame;
	}


	public BufferedReader getBr() {
		return br;
	}


	public void setBr(BufferedReader br) {
		this.br = br;
	}
}
