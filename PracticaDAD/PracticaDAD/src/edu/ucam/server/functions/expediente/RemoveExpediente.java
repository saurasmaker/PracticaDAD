package edu.ucam.server.functions.expediente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveExpediente implements Comando{
	
	public static void run(String idExpediente, ArrayList<Expediente> expedientes, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			Singleton.removeExpediente(idExpediente, expedientes);
			pw.println("OK " + cont + " 200 " + port + " " + address);
			pw.flush();
		} 
		catch (Exception e) 
		{
			System.out.println("Error: " + e);
		}
	}

}
