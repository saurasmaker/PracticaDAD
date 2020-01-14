package edu.ucam.server.functions.paciente;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;

public class ListPacientes implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		try {
			oosData.writeObject(pacientes.size());
			oosData.flush();	
			
			for(Paciente p: pacientes) {
				oosData.writeObject(p);
				oosData.flush();			
			}
			
			pwCommands.println("OK " + cont + " 0 " + pacientes.size() + " " + address + " " + port);
			pwCommands.flush();
		}
		 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}
