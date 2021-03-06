package edu.ucam.server.functions.expediente;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AnadirPaciente2Expediente implements Comando{
	
	//Methods
	public static void run(ArrayList<Expediente> expedientes, ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{
		try 
		{
			Expediente expediente = Singleton.getExpediente(brData.readLine(), expedientes);
			Paciente paciente = Singleton.getPaciente(brData.readLine(), pacientes);
			
			expediente.setPaciente(paciente);
			
			pwCommands.println("OK " + cont + " 0 Paciente a�adido " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error.");
			pwCommands.flush();
		}	
	}
}
