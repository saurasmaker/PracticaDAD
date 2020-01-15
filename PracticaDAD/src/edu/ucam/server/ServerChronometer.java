package edu.ucam.server;

import java.io.PrintWriter;

public class ServerChronometer extends Thread{
	
	//Atributes
	private PrintWriter pw;
	private Boolean paused = false, suspended = false;
	
	//Constructor
	public ServerChronometer(PrintWriter pw) {
		this.pw = pw;
		
	}
	
	//Methods
	public void run() {
			
		int cont = 0;
		
		while(!isPaused()) {
				
			//Control del bucle********************/
			try {
				synchronized(this){
					while(this.isPaused()) 
						wait();
						
					if(this.isSuspended()) {
						return;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/****************************************/
				

			try {
				Thread.sleep(1000);
				++cont;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(cont > 30) {
				enviarNotificacion();
				setPaused(true);
			}
			
		}
			
	}

	
	private void enviarNotificacion() {
		
		pw.println("AFK");
		pw.flush();
		
		return;
	}
	
	//Getters & Setters
	public Boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public Boolean isPaused() {
		return paused;
	}

	public void setPaused(Boolean paused) {
		this.paused = paused;
	}
}
