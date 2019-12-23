package edu.ucam.server.functions.medico;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class UpdateMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, int port, String address, PrintWriter pwCommands, ObjectInputStream oisData) 
	{		
		try 
		{
			Medico medico = (Medico)oisData.readObject();
			Singleton.updateMedico(medico.getId(), medico, medicos);
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
