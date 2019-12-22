package edu.ucam.server.functions.medico;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveMedico implements Comando{
	public static void run(String idMedico, ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			Singleton.removeMedico(idMedico, medicos);
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
