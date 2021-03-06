package edu.ucam.server.functions.medico;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, int port, String address, PrintWriter pwCommands, ObjectInputStream oisData) 
	{		
		try 
		{
			Medico medico = (Medico)oisData.readObject();
			medico.setId(GenerateMedicoId.run(medicos));
			System.out.println("Id generada: " + medico.getId());
			Singleton.addMedico(medico, medicos);
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
