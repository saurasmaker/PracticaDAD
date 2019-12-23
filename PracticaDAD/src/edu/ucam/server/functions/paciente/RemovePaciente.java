package edu.ucam.server.functions.paciente;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemovePaciente implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{		
		try 
		{
			Singleton.removePaciente(brData.readLine(), pacientes);
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
