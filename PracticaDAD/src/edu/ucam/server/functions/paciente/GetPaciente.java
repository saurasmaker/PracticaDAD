package edu.ucam.server.functions.paciente;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetPaciente implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData, BufferedReader brData) 
	{		
		try 
		{
			Paciente p = Singleton.getPaciente(brData.readLine(), pacientes);
			oosData.writeObject(p);
			oosData.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}
	}
}
