package edu.ucam.server.functions.expediente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddExpediente implements Comando{
	
	public static void run(String idPaciente, String idMedico, String idsTratamientos, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos,
			ArrayList<Tratamiento> tratamientos, ArrayList<Expediente> expedientes, int cont, int port, String address, PrintWriter pw) 
	{
		
		String[] idsTratamiendosSplited = idsTratamientos.split(",");
		
		try 
		{
			Singleton.addExpediente(idPaciente, idMedico, idsTratamiendosSplited, pacientes, medicos, tratamientos, expedientes);
			pw.println("OK " + cont + " 200 " + port + " " + address);
			pw.flush();
		} 
		catch (Exception e) 
		{
			pw.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pw.flush();
		}
	}
}
