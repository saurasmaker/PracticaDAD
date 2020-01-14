package edu.ucam.server.functions.expediente;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class EliminarPacienteFromExpediente implements Comando{
	
	//Methods
	public static void run(ArrayList<Expediente> expedientes, ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{
		try 
		{
			Expediente expediente = Singleton.getExpediente(brData.readLine(), expedientes);
			Paciente paciente = Singleton.getPaciente(brData.readLine(), pacientes);
			paciente = null;
			expediente.setPaciente(paciente);
			
			pwCommands.println("OK " + cont + " 200 " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}	
	}
}
