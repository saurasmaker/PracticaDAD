package edu.ucam.server.functions.tratamiento;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;

public class ListTratamientos implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		
		try {
			oosData.writeObject(tratamientos.size());
			oosData.flush();	
			
			for(Tratamiento p: tratamientos) {
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
