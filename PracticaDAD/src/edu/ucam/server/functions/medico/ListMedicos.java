package edu.ucam.server.functions.medico;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;

public class ListMedicos implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		
		try {
			oosData.writeObject(medicos.size());
			oosData.flush();	
			
			for(Medico p: medicos) {
				oosData.writeObject(p);
				oosData.flush();			
			}
			
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
