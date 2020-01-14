package edu.ucam.server.functions.medico;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{		
		try 
		{
			Singleton.removeMedico(brData.readLine(), medicos);
			pwCommands.println("OK " + cont + " 0 Médico eliminado " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "No autorizado.");
			pwCommands.flush();
		}
	}
}
