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
	private ClientFrame clientFrame = null;
	private BufferedReader br;
	private PrintWriter pw;
	private Boolean suspended = false, paused = false, loged = false, usered = false;
	private String message;
	private Integer dataPort;
	private Integer cont = 0;
	
	//Constructors
	public ClientThreadCommands(ClientLogin clientLogin, BufferedReader br, PrintWriter pw) {
		
		this.pw = pw;
		this.br = br;
		this.clientLogin = clientLogin;
		try {
			this.dataPort = Integer.parseInt(br.readLine());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

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
			if(!loged) {
				if(!usered)
					checkLoged();
				
				else
					checkPass();
			}
			
			if(message!=null) {
				if(clientFrame == null)
					System.out.println("From Server: " + message + " " + cont);
				else {
					clientFrame.getEditorPaneLog().setText(clientFrame.getEditorPaneLog().getText()+ "From Server: " + message + " " + cont + "\n");
				}
			}

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

		return;
	}
	
	
	public void checkLoged() {
			
		try {
			if(this.message.split(" ")[0].equals("OK")) {
				usered = true;
				return;
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Usuario incorrecto.");
				readMessage();
				return;
			}
		}
		catch(Exception t) {
				
		}
	}
	
		
	public void checkPass() {
			

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
		
		return;
	}
	
	public void openClientFrame() {
		this.clientFrame = new ClientFrame(pw, this, dataPort);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clientFrame.setVisible(true);
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


	public ClientFrame getClientFrame() {
		return clientFrame;
	}


	public void setClientFrame(ClientFrame clientFrame) {
		this.clientFrame = clientFrame;
	}
	
	
}
