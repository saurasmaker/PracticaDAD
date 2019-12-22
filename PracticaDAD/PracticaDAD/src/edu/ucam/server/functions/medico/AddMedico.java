package edu.ucam.server.functions.medico;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddMedico implements Comando{
	public static void run(Medico medico, ArrayList<Medico> medicos, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			Singleton.addMedico(medico, medicos);
			pw.println("OK " + cont + " 200 " + port + " " + address);
			pw.flush();
		} 
		catch (Exception e) 
		{
			System.out.println("Error: " + e);
		}
	}
}
