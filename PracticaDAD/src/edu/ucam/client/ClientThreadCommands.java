package edu.ucam.client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.ucam.client.frames.ClientFrame;
import edu.ucam.client.frames.ClientLogin;
import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.ServerDataChannel;
import edu.ucam.server.functions.expediente.AddExpediente;
import edu.ucam.server.functions.expediente.GetExpediente;
import edu.ucam.server.functions.expediente.RemoveExpediente;
import edu.ucam.server.functions.medico.AddMedico;
import edu.ucam.server.functions.medico.CountMedicos;
import edu.ucam.server.functions.medico.GenerateMedicoId;
import edu.ucam.server.functions.medico.GetMedico;
import edu.ucam.server.functions.medico.ListMedicos;
import edu.ucam.server.functions.medico.RemoveMedico;
import edu.ucam.server.functions.medico.UpdateMedico;
import edu.ucam.server.functions.paciente.AddPaciente;
import edu.ucam.server.functions.paciente.CountPacientes;
import edu.ucam.server.functions.paciente.GeneratePacienteId;
import edu.ucam.server.functions.paciente.GetPaciente;
import edu.ucam.server.functions.paciente.ListPacientes;
import edu.ucam.server.functions.paciente.RemovePaciente;
import edu.ucam.server.functions.paciente.UpdatePaciente;
import edu.ucam.server.functions.tratamiento.AddTratamiento;
import edu.ucam.server.functions.tratamiento.CountTratamientos;
import edu.ucam.server.functions.tratamiento.GetTratamiento;
import edu.ucam.server.functions.tratamiento.ListTratamientos;
import edu.ucam.server.functions.tratamiento.RemoveTratamiento;
import edu.ucam.server.functions.tratamiento.UpdateTratamiento;

public class ClientThreadCommands extends Thread{
	
	//Atributes
	private ClientLogin clientLogin;
	private BufferedReader br;
	private PrintWriter pw;
	private Boolean suspended = false, paused = false, loged = false, usered = false;
	private String message;
	private String[] splitedMessage;
	private Integer ID;
	private String address;
	private Socket socket;
	
	private Integer cont = 0;
	
	private ArrayList<Expediente> expedientes;
	private ArrayList<Paciente> pacientes;
	private ArrayList<Medico> medicos;
	private ArrayList<Tratamiento> tratamientos;
	
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
	
	private void checkCommand(String message) {
		
		message += " 1";
		this.splitedMessage = message.split(" ");
		
		switch (splitedMessage[0]) {
		
		case "USER"://///////////////////////
			this.checkUser();
			break;
			
		case "PASS"://///////////////////////
			if(usered)
				this.checkUser();
			break;
			
		case "ADDPACIENTE"://///////////////////////
			if(loged) {
				try {
					AddPaciente.run(new Paciente(GeneratePacienteId.run(pacientes), splitedMessage[1], splitedMessage[2], new SimpleDateFormat("dd/MM/yyyy").parse(splitedMessage[3])), pacientes, cont, socket.getPort(), message, pw);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else 
				this.userNotLoged();
			
			break;
		
		case "UPDATEPACIENTE"://///////////////////////
			if(loged) {
				try {
					UpdatePaciente.run(splitedMessage[1], new Paciente(null, splitedMessage[2], splitedMessage[3], new SimpleDateFormat("dd/MM/yyyy").parse(splitedMessage[4])), pacientes, cont, socket.getPort(), address, pw);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else 
				this.userNotLoged();
			
			break;
			
		case "GETPACIENTE"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel();
				GetPaciente.run(splitedMessage[1], pacientes, cont, address, socket.getPort(), pw, sdc.getOos());
			}
			
			else 
				this.userNotLoged();
			
			break;
			
		case "REMOVEPACIENTE"://///////////////////////
			if(loged) 
				RemovePaciente.run(splitedMessage[1], pacientes, cont, address, socket.getPort(), pw);
			
			else 
				this.userNotLoged();
			
			break;
			
		case "LISTPACIENTES"://///////////////////////
			if(loged) 
				ListPacientes.run(pacientes, cont, address, socket.getPort(), pw);
			
			else 
				this.userNotLoged();
			break;
			
		case "COUNTPACIENTES"://///////////////////////
			if(loged) 
				CountPacientes.run(pacientes, cont, message, socket.getLocalPort(), pw); 
			else 
				this.userNotLoged();
			break;
			
		case "ADDEXPEDIENTE"://///////////////////////
			if(loged) {
				AddExpediente.run(splitedMessage[1], splitedMessage[2], splitedMessage[3]/*idsTratamientos*/, pacientes, medicos, tratamientos, expedientes, cont, socket.getPort(), address, pw);
			}
			else this.userNotLoged();
			
				
			break;
			
		case "GETEXPEDIENTE"://///////////////////////
			if(loged) {
				GetExpediente.run(splitedMessage[1], expedientes, cont, address, socket.getPort(), pw);
			}
			else this.userNotLoged();
			
			break;
			
		case "REMOVEEXPEDIENTE"://///////////////////////
			if(loged) 
				RemoveExpediente.run(splitedMessage[1], expedientes, cont, message, socket.getLocalPort(), pw); 
			else 
				this.userNotLoged();
			
			break;
		
		case "LISTEXPEDIENTES"://///////////////////////
			
			break;
			
		case "ADDPACIENTE2EXP"://///////////////////////
			break;
			
		case "REMOVEPACIENTEFROMEXP"://///////////////////////
			break;
			
		case "ADDMEDICO2EXP"://///////////////////////
			break;
			
		case "REMOVEMEDICOFROMEXP"://///////////////////////
			break;
			
		case "ADDTRATAM2EXP"://///////////////////////
			break;
			
		case "REMOVETRATAMFROMEXP"://///////////////////////
			break;
			
		case "ADDMEDICO"://///////////////////////
			if(loged) 
				AddMedico.run(new Medico(GenerateMedicoId.run(medicos), splitedMessage[1], splitedMessage[2], splitedMessage[3]), medicos, cont, socket.getPort(), address, pw); 
			else 
				this.userNotLoged();
			
			break;
			
		case "UPDATEMEDICO"://///////////////////////
			if(loged) 
				UpdateMedico.run(splitedMessage[1], new Medico(null, splitedMessage[2], splitedMessage[3], splitedMessage[4]), medicos, cont, socket.getPort(), address, pw); 
			else 
				this.userNotLoged();
			break;
			
		case "GETMEDICO"://///////////////////////
			if(loged) 
				GetMedico.run(splitedMessage[1], medicos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "REMOVEMEDICO"://///////////////////////
			if(loged) 
				RemoveMedico.run(splitedMessage[1], medicos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			break;
			
		case "LISTMEDICOS"://///////////////////////
			if(loged) 
				ListMedicos.run(medicos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			break;
			
		case "COUNTMEDICOS"://///////////////////////
			if(loged) 
				CountMedicos.run(medicos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "ADDTRATAMIENTO"://///////////////////////
			if(loged) 
				AddTratamiento.run(new Tratamiento(), tratamientos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "UPDATETRATAMIENTO"://///////////////////////
			if(loged) 
				UpdateTratamiento.run(splitedMessage[1], new Tratamiento(), tratamientos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "GETTRATAMIENTO"://///////////////////////
			if(loged) 
				GetTratamiento.run(splitedMessage[1], tratamientos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "REMOVETRATAMIENTO"://///////////////////////
			if(loged) 
				RemoveTratamiento.run(splitedMessage[1], tratamientos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "LISTTRATAMIENTOS"://///////////////////////
			if(loged) 
				ListTratamientos.run(tratamientos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			
			break;
			
		case "COUNTTRATAMIENTOS"://///////////////////////
			if(loged) 
				CountTratamientos.run(tratamientos, cont, address, socket.getPort(), pw);
			else 
				this.userNotLoged();
			break;
			
		case "EXIT"://///////////////////////
			this.setSuspended(true);
			System.out.println("suspended");
			break;
			
		default://///////////////////////
			pw.println("Command not found");
			pw.flush();
			break;
			
		}
		
		return;
	}
	
	
	private void checkUser() {
		
		switch (splitedMessage[0]) {
		case "USER":
			if(splitedMessage[1].equals("admin")) {
				pw.println("OK " + cont + " 200 " + splitedMessage[1] + " Envíe contraseña.");
				pw.flush();
				usered = true;
			}
			else {
				pw.println("Failed " + cont + " 400" + " Not User.");
				pw.flush();
			}
			break;
			
		case "PASS":
			if(splitedMessage[1].equals("admin")) {
				pw.println("OK " + cont + " 200" + " Welcome " + splitedMessage[1] + ".");
				pw.flush();
				loged = true;
			}
			else {
				pw.println("Failed " + cont + " 400 " + splitedMessage[1] + " Prueba de nuevo.");
				pw.flush();
			}
			break;
		}
		
		return;
	}
	
	public void userNotLoged() {
		pw.println("USER NOT LOGED");
		pw.flush();
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
		ClientFrame frame = new ClientFrame(pw, this);
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
