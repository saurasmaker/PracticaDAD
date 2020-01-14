package edu.ucam.server.functions.expediente;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveExpediente implements Comando{
	public static void run(ArrayList<Expediente> expedientes, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{
		try 
		{
			Singleton.removeExpediente(brData.readLine(), expedientes);
			pwCommands.println("OK " + cont + " 200 " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception t) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + t.getMessage());
			pwCommands.flush();
		}	
	}
}
