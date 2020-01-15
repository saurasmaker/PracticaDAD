package edu.ucam.client;

import java.awt.EventQueue;

import edu.ucam.client.frames.AreYouAFK;
import edu.ucam.client.frames.ClientFrame;
import edu.ucam.client.frames.ClientLogin;

public class ClientChronometer extends Thread{
	
	//Atributes
	private Boolean paused = false, suspended = false;
	private ClientThreadCommands ctc;
	private ClientFrame cf;
	private ClientLogin cl;
	private AreYouAFK aya;
	//Constructor
	public ClientChronometer(ClientThreadCommands ctc, ClientFrame cf, ClientLogin cl) {
		this.setCf(cf);
		this.setCl(cl);
		this.setCtc(ctc);
	}
	
	//Methods
	public void run() {
			
		Integer cont = 10;
		this.aya = new AreYouAFK(this, ctc.getPw());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					aya.setVisible(true);
				} catch(Exception t) {
					t.printStackTrace();
				}
			}
		});
		
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
				--cont;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			aya.getLblNewLabelCont().setText(cont.toString());

			if(cont < 0) {
				shutDown();
				setSuspended(true);
			}
			
		}
			
	}

	
	private void shutDown() {
		try {
			cl.dispose();
		}
		catch(Exception t) {
			
		}
		
		try {
			cf.dispose();
		}
		catch(Exception t){
			
		}
		
		try {
			aya.dispose();
		}
		catch(Exception t) {
			
		}
		 
		ctc.getPw().println("EXIT");
		ctc.getPw().flush();
		
		ctc.setSuspended(true);
		
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

	public ClientThreadCommands getCtc() {
		return ctc;
	}

	public void setCtc(ClientThreadCommands ctc) {
		this.ctc = ctc;
	}

	public ClientFrame getCf() {
		return cf;
	}

	public void setCf(ClientFrame cf) {
		this.cf = cf;
	}

	public ClientLogin getCl() {
		return cl;
	}

	public void setCl(ClientLogin cl) {
		this.cl = cl;
	}
}
