package edu.ucam.server.functions.medico;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;

public class CountMedicos implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		try {
			oosData.writeObject("\n >Médicos \n   +Cantidad: " + medicos.size());
			oosData.flush();
			pwCommands.println("OK " + cont + " 0 " + medicos.size() + " " + address + " " + port);
			pwCommands.flush();	
		}
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}
