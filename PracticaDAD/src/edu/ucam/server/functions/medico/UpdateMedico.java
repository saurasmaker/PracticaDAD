package edu.ucam.server.functions.medico;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class UpdateMedico implements Comando{
	public static void run(String idMedico, Medico medico, ArrayList<Medico> medicos, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			Singleton.updateMedico(idMedico, medico, medicos);
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
