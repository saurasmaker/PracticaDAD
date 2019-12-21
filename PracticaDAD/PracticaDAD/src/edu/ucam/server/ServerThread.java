package edu.ucam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.expediente.AddExpediente;
import edu.ucam.server.functions.expediente.GetExpediente;

public class ServerThread extends Thread{

	//Atributes
	private Integer ID;
	private String address;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	
	private Integer cont = 0;
	private String[] splitedMessage;
	private String message;
	
	private ArrayList<Expediente> expedientes;
	private ArrayList<Paciente> pacientes;
	private ArrayList<Medico> medicos;
	private ArrayList<Tratamiento> tratamientos;
	
	private Boolean suspended = false, paused = false, usered = false, loged = false;
	
	//Constructors
	public ServerThread(Integer ID, Socket socket, ArrayList<Expediente> expedientes, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos, ArrayList<Tratamiento> tratamientos) {
		
		this.setExpedientes(expedientes);
		this.setSocket(socket);
		System.out.println("SOCKET establecido.");
		this.setID(ID);
		System.out.println("Thread_ID: " + this.ID);
		this.setBridges();
		System.out.println("Puentes creados");
		
		try {
			this.address = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	
	//Methods
	public void run() {
		
		while(!isPaused()) {
			
			//Control del bucle********************/
			try {
				synchronized(this){
					while(this.isPaused()) 
						wait();
					
					if(this.isSuspended()) {
						System.out.println(" >Conexión cerrada.");
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/****************************************/
			
			
			//Cuerpo
			try {
				message = this.getBr().readLine();
				System.out.println("Mensaje recibido: " + message);
				this.checkCommand(message);
				++cont;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
		
	}
			
			
	void checkCommand(String message) {
		message += " 1";
		this.splitedMessage = message.split(" ");
		
		switch (splitedMessage[0]) {
		case "USER":
			this.checkUser();
			break;
			
		case "PASS":
			if(usered)
				this.checkUser();
			break;
			
		case "ADDPACIENTE":
			break;
		case "UPDATEPACIENTE":
			break;
			
		case "GETPACIENTE":
			break;
			
		case "REMOVEPACIENTE":
			break;
			
		case "LISTPACIENTES":
			break;
			
		case "COUNTPACIENTES":
			break;
			
		case "ADDEXPEDIENTE":
			if(loged) {
				AddExpediente.run(splitedMessage[1], splitedMessage[2], splitedMessage[3]/*idsTratamientos*/, pacientes, medicos, tratamientos, expedientes, cont, socket.getPort(), address, pw);
			}
			else this.userNotLoged();
			
				
			break;
			
		case "GETEXPEDIENTE":
			if(loged) {
				GetExpediente.run(splitedMessage[1], expedientes, pw, cont, socket.getLocalPort(), address.toString());
			}
			else this.userNotLoged();
			
			break;
			
		case "REMOVEEXPEDIENTE":
			break;
		
		case "LISTEXPEDIENTES":
			break;
			
		case "ADDPACIENTE2EXP":
			break;
			
		case "REMOVEPACIENTEFROMEXP":
			break;
			
		case "ADDMEDICO2EXP":
			break;
			
		case "REMOVEMEDICOFROMEXP":
			break;
			
		case "ADDTRATAM2EXP":
			break;
			
		case "REMOVETRATAMFROMEXP":
			break;
			
		case "ADDMEDICO":
			break;
			
		case "UPDATEMEDICO":
			break;
			
		case "GETMEDICO":
			break;
			
		case "REMOVEMEDICO":
			break;
			
		case "LISTMEDICOS":
			break;
			
		case "COUNTMEDICOS":
			break;
			
		case "ADDTRATAMIENTO":
			break;
			
		case "UPDATETRATAMIENTO":
			break;
			
		case "GETTRATAMIENTO":
			break;
			
		case "REMOVETRATAMIENTO":
			break;
			
		case "LISTTRATAMIENTOS":
			break;
			
		case "COUNTTRATAMIENTOS":
			break;
			
		case "EXIT":
			break;
			
		default:
			pw.println("Command not found");
			pw.flush();
			System.out.println("Command not found");
			break;
			
		}
		
		return;
	}
	
	
	void checkUser() {
		
		switch (splitedMessage[0]) {
		case "USER":
			if(splitedMessage[1].equals("admin")) {
				System.out.println("OK " + cont + " 200" + " Welcome " + splitedMessage[1] + ".");
				pw.println("OK " + cont + " 200" + " Welcome " + splitedMessage[1] + ".");
				pw.flush();
				usered = true;
			}
			else {
				System.out.println("Failed " + cont + " 400" + " Not User.");
				pw.println("Failed " + cont + " 400" + " Not User.");
				pw.flush();
			}
			break;
			
		case "PASS":
			if(splitedMessage[1].equals("admin")) {
				System.out.println("OK " + cont + " 200 " + splitedMessage[1] + " Envíe contraseña.");
				pw.println("OK " + cont + " 200 " + splitedMessage[1] + " Envíe contraseña.");
				pw.flush();
				loged = true;
			}
			else {
				System.out.println("Failed " + cont + " 400 " + splitedMessage[1] + " Prueba de nuevo.");
				pw.println("Failed " + cont + " 400 " + splitedMessage[1] + " Prueba de nuevo.");
				pw.flush();
			}
			break;
		}
		
		return;
	}
	
	
	public void setBridges() {
		
		
		try {
			this.setBr(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			this.setPw(new PrintWriter(new OutputStreamWriter(socket.getOutputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}

	public void userNotLoged() {
		pw.println("USER NOT LOGED");
		pw.flush();
		return;
	}

	//Getters & Setters
	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public BufferedReader getBr() {
		return br;
	}


	public void setBr(BufferedReader br) {
		this.br = br;
	}


	public PrintWriter getPw() {
		return pw;
	}


	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}


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


	public Boolean getLoged() {
		return loged;
	}


	public void setLoged(Boolean loged) {
		this.loged = loged;
	}


	public Integer getCont() {
		return cont;
	}


	public void setCont(Integer cont) {
		this.cont = cont;
	}


	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public ArrayList<Expediente> getExpedientes() {
		return expedientes;
	}


	public void setExpedientes(ArrayList<Expediente> expedientes) {
		this.expedientes = expedientes;
	}


	public ArrayList<Paciente> getPacientes() {
		return pacientes;
	}


	public void setPacientes(ArrayList<Paciente> pacientes) {
		this.pacientes = pacientes;
	}


	public ArrayList<Medico> getMedicos() {
		return medicos;
	}


	public void setMedicos(ArrayList<Medico> medicos) {
		this.medicos = medicos;
	}


	public ArrayList<Tratamiento> getTratamientos() {
		return tratamientos;
	}


	public void setTratamientos(ArrayList<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}
	
		
}
