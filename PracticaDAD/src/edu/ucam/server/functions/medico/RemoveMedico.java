package edu.ucam.server.functions.medico;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pwCommand, BufferedReader brData) 
	{		
		try 
		{
			Singleton.removeMedico(brData.readLine(), medicos);
			pwCommand.println("OK " + cont + " 200 " + address + " " + port);
			pwCommand.flush();
		} 
		catch (Exception e) 
		{
			pwCommand.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommand.flush();
		}
	}
}
