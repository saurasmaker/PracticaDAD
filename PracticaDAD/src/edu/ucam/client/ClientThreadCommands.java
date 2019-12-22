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
	private Boolean suspended = false, paused = false, loged = false;
	private String message;
	
	//Constructors
	public ClientThreadCommands(ClientLogin clientLogin, BufferedReader br, PrintWriter pw) {
		this.pw = pw;
		this.br = br;
		this.clientLogin = clientLogin;
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
						return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/*************************************/
			
			
			//Cuerpo
			readMessage();
			
		}
		
	}


	public void readMessage() {
		
		setMessage(null);
		try {
			setMessage(this.br.readLine());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		checkLoged();
		
		return;
	}
	
	public void checkLoged() {
		
		System.out.println(this.getMessage());
		
		if(!loged) {
			try {
				if(this.getMessage().split(" ")[4].equals("admin.")) {
					System.out.println("yas");
					loged = true;
					clientLogin.dispose();
					openClientFrame();
					return;
				}
				else if(this.getMessage().split(" ")[4].equals("User."))
					JOptionPane.showMessageDialog(null, "Usuario incorrecto.");
			
				else if(this.getMessage().split(" ")[6].equals("nuevo.")) 
					JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
				}
			catch(Exception t) {
				
			}
		}
		
		else
			JOptionPane.showMessageDialog(null, "Este usuario ya ha sido logeado.");
					
		return;
	}
	
	public void openClientFrame() {
		ClientFrame frame = new ClientFrame(pw);
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


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}
