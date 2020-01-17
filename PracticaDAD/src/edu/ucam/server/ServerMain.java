package edu.ucam.server;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Singleton;

public class ServerMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		//Variables
		Integer port = 2020;
		ServerSocket serverSocket = null;
		Integer numThreads = 0;
		ArrayList<ServerThreadCommands> serverThreads = new ArrayList<ServerThreadCommands>();
		ArrayList<Expediente> expedientes = new ArrayList<Expediente>();
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		ArrayList<Medico> medicos = new ArrayList<Medico>();
		ArrayList<Tratamiento> tratamientos= new ArrayList<Tratamiento>();
		
		//Cargamos datos
		Singleton.loadData(pacientes, medicos, tratamientos, expedientes);
		//Guardado autómatico 
		EventQueue.invokeLater(new Runnable() {
			private int cont = 0;
			public void run() {
				try {
					while(true) {
						Thread.sleep(1000);
						setCont(getCont() + 1);
						
						if(cont > 60) {
							cont = 0;
							Singleton.saveData(pacientes, medicos, tratamientos, expedientes);
						}
					}
				} catch(Exception t) {
					t.printStackTrace();
				}
			}
			public int getCont() {
				return cont;
			}
			public void setCont(int cont) {
				this.cont = cont;
			}
		});/************************************/
		
		
		
		
		//Establecemos conexión
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server iniciado...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				serverThreads.add(new ServerThreadCommands(numThreads, serverSocket.accept(), expedientes, pacientes, medicos, tratamientos));
				serverThreads.get(numThreads).setDataPort(2021 + (numThreads*50));
				serverThreads.get(numThreads).start();
				++numThreads;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
}
