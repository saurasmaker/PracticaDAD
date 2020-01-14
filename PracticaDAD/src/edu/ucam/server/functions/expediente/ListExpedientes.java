package edu.ucam.server.functions.expediente;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.server.functions.Comando;

public class ListExpedientes implements Comando{
	public static void run(ArrayList<Expediente> expedientes, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		
		try {
			oosData.writeObject(expedientes.size());
			oosData.flush();	
			
			for(Expediente e: expedientes) {
				oosData.writeObject(e);
				oosData.flush();			
			}
			
			pwCommands.println("OK " + cont + " 0 " + expedientes.size() + " " + address + " " + port);
			pwCommands.flush();
		}
		 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}