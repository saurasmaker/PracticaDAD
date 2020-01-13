package edu.ucam.server.functions.paciente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class UpdatePaciente implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, int port, String address, PrintWriter pwCommands, ObjectInputStream oisData, ObjectOutputStream oosData) 
	{		
		try 
		{
			Paciente paciente = Singleton.getPaciente((String)oisData.readObject(), pacientes);
			oosData.writeObject(paciente);
			oosData.flush();
			
			paciente = (Paciente)oisData.readObject();
			Singleton.updatePaciente(paciente, pacientes);
			
			pwCommands.println("OK " + cont + " 200 " + port + " " + address);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}
	}
}
