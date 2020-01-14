package edu.ucam.server.functions.expediente;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetExpediente implements Comando{
	

	//Atributes
	
	//Methods
	public static void run(ArrayList<Expediente> expedientes, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData, BufferedReader brData) 
	{
		try 
		{
			Expediente expediente = Singleton.getExpediente(brData.readLine(), expedientes);
			oosData.writeObject(expediente);
			oosData.flush();
			pwCommands.println("OK " + cont + " 0 " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}	
	}
}
