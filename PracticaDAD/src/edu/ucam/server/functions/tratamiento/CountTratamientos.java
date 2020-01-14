package edu.ucam.server.functions.tratamiento;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;

public class CountTratamientos implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		try {
			oosData.writeObject("\n >TRATAMIENTOS \n   +Cantidad: " + tratamientos.size());
			oosData.flush();
			pwCommands.println("OK " + cont + " 0 " + tratamientos.size() + " " + address + " " + port);
			pwCommands.flush();	
		}
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}
