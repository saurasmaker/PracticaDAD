package edu.ucam.server.functions.expediente;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class EliminarMedicoFromExpediente implements Comando{
	
	//Methods
	public static void run(ArrayList<Expediente> expedientes, ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{
		try 
		{
			Expediente expediente = Singleton.getExpediente(brData.readLine(), expedientes);
			Medico medico = Singleton.getMedico(brData.readLine(), medicos);
			medico = null;
			expediente.setMedico(medico);
			
			pwCommands.println("OK " + cont + " 0 Médico eliminado " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}
