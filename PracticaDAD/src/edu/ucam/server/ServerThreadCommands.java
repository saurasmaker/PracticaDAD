package edu.ucam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import edu.ucam.server.functions.medico.AddMedico;
import edu.ucam.server.functions.medico.CountMedicos;
import edu.ucam.server.functions.medico.GetMedico;
import edu.ucam.server.functions.medico.ListMedicos;
import edu.ucam.server.functions.medico.RemoveMedico;
import edu.ucam.server.functions.medico.UpdateMedico;
import edu.ucam.server.functions.paciente.AddPaciente;
import edu.ucam.server.functions.paciente.CountPacientes;
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

public class ServerThreadCommands extends Thread{

	//Atributes
	private Integer ID;
	private String address;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	
	private Integer cont = 0;
	private String message;
	
	private ArrayList<Expediente> expedientes;
	private ArrayList<Paciente> pacientes;
	private ArrayList<Medico> medicos;
	private ArrayList<Tratamiento> tratamientos;
	
	private Integer dataPort = 2021;
	
	private Boolean suspended = false, paused = false, usered = false, loged = false;
	
	//Constructors
	public ServerThreadCommands(Integer ID, Socket socket, ArrayList<Expediente> expedientes, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos, ArrayList<Tratamiento> tratamientos) {
		this.expedientes = expedientes;
		this.pacientes = pacientes;
		this.medicos = medicos;
		this.tratamientos = tratamientos;
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
						System.out.println(" >Conexi�n cerrada.");
						return;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/****************************************/
			
			
			//Cuerpo
			readCommand();

		}
		
	}
	
	private void readCommand() {
		
		try {
			message = this.getBr().readLine();
			System.out.println("Comando recibido: " + message);
			this.checkCommand(message);
			++cont;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
			
			
	private void checkCommand(String command) {
		
		switch (command) {
		
		case "USER":
			
			try {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
				
				String text = "";
				
				if("admin".equals(text = sdc.getBr().readLine())) {
					pw.println("OK " + cont + " 200 " + text + " Env�e contrase�a.");
					pw.flush();
					setUsered(true);
				}
				else {
					pw.println("Failed " + cont + " 400" + " Not User.");
					pw.flush();
				}
				
				sdc.closeChannel();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			++dataPort;
			
			break;
			
		case "PASS":
			
			try {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
				
				String text = "";
				if("admin".equals(text = sdc.getBr().readLine())) {
					pw.println("OK " + cont + " 200" + " Welcome " + text + ".");
					pw.flush();
					loged = true;
				}
				else {
					pw.println("Failed " + cont + " 400 " + text + " Prueba de nuevo.");
					pw.flush();
				}
				
				sdc.closeChannel();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			++dataPort;
			
			break;
			
		case "ADDPACIENTE"://///////////////////////
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setOis(new ObjectInputStream(sdc.getSocket().getInputStream()));
					AddPaciente.run(pacientes, cont, socket.getPort(), address, pw, sdc.getOis());
				}
				catch (IOException e) {
					 e.printStackTrace();
				}
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
		
		case "UPDATEPACIENTE"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
					sdc.setOis(new ObjectInputStream(sdc.getSocket().getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				UpdatePaciente.run(pacientes, cont, socket.getPort(), address, pw, sdc.getOis(), sdc.getOos()); 
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "GETPACIENTE"://///////////////////////
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
					GetPaciente.run(pacientes, cont, address, socket.getPort(), pw, sdc.getOos(), sdc.getBr());
				}
				catch(IOException t) {
					
				}
			}
			
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "REMOVEPACIENTE"://///////////////////////
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
					RemovePaciente.run(pacientes, cont, address, socket.getPort(), pw, sdc.getBr());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "LISTPACIENTES"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				ListPacientes.run(pacientes, cont, address, socket.getPort(), pw, sdc.getOos());
			}
			
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "COUNTPACIENTES"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				CountPacientes.run(pacientes, cont, address, socket.getPort(), pw, sdc.getOos()); 
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "ADDEXPEDIENTE"://///////////////////////
			
			
				
			break;
			
		case "GETEXPEDIENTE"://///////////////////////
			
			
			break;
			
		case "REMOVEEXPEDIENTE"://///////////////////////
			
			
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
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setOis(new ObjectInputStream(sdc.getSocket().getInputStream()));
					AddMedico.run(medicos, cont, socket.getPort(), address, pw, sdc.getOis());
				}	
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "UPDATEMEDICO"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
					sdc.setOis(new ObjectInputStream(sdc.getSocket().getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				UpdateMedico.run(medicos, cont, socket.getPort(), address, pw, sdc.getOis(), sdc.getOos()); 
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "GETMEDICO"://///////////////////////
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
					GetMedico.run(medicos, cont, address, socket.getPort(), pw, sdc.getOos(), sdc.getBr());
				}
				catch(IOException t) {
					
				}
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "REMOVEMEDICO"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				RemoveMedico.run(medicos, cont, address, socket.getPort(), pw, sdc.getBr());
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "LISTMEDICOS"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				ListMedicos.run(medicos, cont, address, socket.getPort(), pw, sdc.getOos());
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "COUNTMEDICOS"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				CountMedicos.run(medicos, cont, address, socket.getPort(), pw, sdc.getOos()); 
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "ADDTRATAMIENTO"://///////////////////////
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setOis(new ObjectInputStream(sdc.getSocket().getInputStream()));
					AddTratamiento.run(tratamientos, cont, address, socket.getPort(), pw, sdc.getOis());
				}	
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "UPDATETRATAMIENTO"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
					sdc.setOis(new ObjectInputStream(sdc.getSocket().getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				UpdateTratamiento.run(tratamientos, cont, address, socket.getPort(), pw, sdc.getOis(), sdc.getOos()); 
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "GETTRATAMIENTO"://///////////////////////
			if(loged) {
				try {
					ServerDataChannel sdc = new ServerDataChannel(dataPort);
					sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
					GetTratamiento.run(tratamientos, cont, address, socket.getPort(), pw, sdc.getOos(), sdc.getBr());
				}
				catch(IOException t) {
					
				}
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "REMOVETRATAMIENTO"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setBr(new BufferedReader(new InputStreamReader(sdc.getSocket().getInputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				RemoveTratamiento.run(tratamientos, cont, address, socket.getPort(), pw, sdc.getBr());
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "LISTTRATAMIENTOS"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				ListTratamientos.run(tratamientos, cont, address, socket.getPort(), pw, sdc.getOos());
			}
				
			else 
				this.userNotLoged();
			
			++dataPort;
			
			break;
			
		case "COUNTTRATAMIENTOS"://///////////////////////
			if(loged) {
				ServerDataChannel sdc = new ServerDataChannel(dataPort);
				try {
					sdc.setOos(new ObjectOutputStream(sdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				CountTratamientos.run(tratamientos, cont, address, socket.getPort(), pw, sdc.getOos());
			}
			else 
				this.userNotLoged();
			
			++dataPort;
			
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


	public Boolean getUsered() {
		return usered;
	}


	public void setUsered(Boolean usered) {
		this.usered = usered;
	}
	
		
}
