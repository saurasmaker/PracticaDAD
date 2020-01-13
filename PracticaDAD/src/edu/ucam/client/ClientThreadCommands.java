package edu.ucam.client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import edu.ucam.client.frames.ClientFrame;
import edu.ucam.client.frames.ClientLogin;

public class ClientThreadCommands extends Thread{
	
	//Atributes
	private ClientLogin clientLogin;
	private BufferedReader br;
	private PrintWriter pw;
	private Boolean suspended = false, paused = false, loged = false, usered = false;
	private String message;
	private Integer dataPort;
	private Integer cont = 0;
	
	//Constructors
	public ClientThreadCommands(ClientLogin clientLogin, BufferedReader br, PrintWriter pw, Integer dataPort) {
		this.dataPort = dataPort;
		this.pw = pw;
		this.br = br;
		this.clientLogin = clientLogin;
	}
	
	
	//Methods
	public void run() {
			
		int cont = 0;
		
		while(!paused) {
			
			//Control del bucle
			try {
				synchronized(this){
					while(this.isPaused()) 
						wait();
					
					if(this.isSuspended())
						return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/*************************************/
			
			//Cuerpo
			readMessage();
			
			if(message!=null)
				System.out.println("From Server: " + message + " " + cont);
			
			++cont;
		}
		
	}
	
	public void readMessage() {
		
		setMessage("");
		try {
			setMessage((this.br.readLine()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		checkLoged();
		
		return;
	}
	
	
	public void checkLoged() {
			
		if(!usered & !loged) {
			try {
				if(this.message.split(" ")[0].equals("OK")) {
					usered = true;
					return;
				}
			
				else {
					JOptionPane.showMessageDialog(null, "Usuario incorrecta.");
				}
			}
			catch(Exception t) {
				
			}
		}
		
		else if(!loged) {
			try {
				if(this.message.split(" ")[0].equals("OK")) {
					loged = true;
					clientLogin.dispose();
					openClientFrame();
					return;
				}

				else  {
					JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
					usered = false;
				}
			}
			catch(Exception t) {
				
			}
		}
	
		return;
	}
	
	public void openClientFrame() {
		ClientFrame frame = new ClientFrame(pw, this, dataPort);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
							e.printStackTrace();
				}
			}
		});/************************************************************************************/
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

	
	public BufferedReader getBr() {
		return br;
	}


	public void setBr(BufferedReader br) {
		this.br = br;
	}


	public Boolean getLoged() {
		return loged;
	}


	public void setLoged(Boolean loged) {
		this.loged = loged;
	}



	public Boolean getUsered() {
		return usered;
	}


	public void setUsered(Boolean usered) {
		this.usered = usered;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Integer getCont() {
		return cont;
	}


	public void setCont(Integer cont) {
		this.cont = cont;
	}


	public PrintWriter getPw() {
		return pw;
	}


	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}


	public Integer getDataPort() {
		return dataPort;
	}


	public void setDataPort(Integer dataPort) {
		this.dataPort = dataPort;
	}


	public Boolean getSuspended() {
		return suspended;
	}


	public Boolean getPaused() {
		return paused;
	}
	
	
}
