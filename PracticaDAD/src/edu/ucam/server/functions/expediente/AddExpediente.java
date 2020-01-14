package edu.ucam.server.functions.expediente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddExpediente implements Comando{
	
	public static void run(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos,
			ArrayList<Tratamiento> tratamientos, ArrayList<Expediente> expedientes, int cont, int port, String address, PrintWriter pwCommands, ObjectInputStream oisData) 
	{
		String idPaciente = null;
		String idMedico = null;
		String idsTratamientos = null;
		String observaciones = null;
		
		try {
			idPaciente = (String)oisData.readObject();
			idMedico = (String)oisData.readObject();
			idsTratamientos = (String)oisData.readObject();
			observaciones = (String)oisData.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		
		String[] idsTratamiendosSplited = idsTratamientos.split(";");
		
		try 
		{
			Singleton.addExpediente(idPaciente, idMedico, idsTratamiendosSplited, observaciones, pacientes, medicos, tratamientos, expedientes);
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
