package edu.ucam.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;

public class ServerMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		//Variables
		Integer port = 2020;
		ServerSocket serverSocket = null;
		Integer numThreads = 0;
		ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();
		ArrayList<Expediente> expedientes = new ArrayList<Expediente>();
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		ArrayList<Medico> medicos = new ArrayList<Medico>();
		ArrayList<Tratamiento> tratamientos= new ArrayList<Tratamiento>();
		
		//Establecemos conexión
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server iniciado...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				serverThreads.add(new ServerThread(numThreads, serverSocket.accept(), expedientes, pacientes, medicos, tratamientos));
				serverThreads.get(numThreads).run();
				System.out.println(" >Nueva conexión establecida.");
				++numThreads;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
