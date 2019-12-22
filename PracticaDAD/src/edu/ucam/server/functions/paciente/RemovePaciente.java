package edu.ucam.server.functions.paciente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemovePaciente implements Comando{
	public static void run(String idPaciente, ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			Singleton.removePaciente(idPaciente, pacientes);
			pw.println("OK " + cont + " 200 " + address + " " + port);
			pw.flush();
		} 
		catch (Exception e) 
		{
			pw.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pw.flush();
		}
	}
}
