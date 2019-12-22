package edu.ucam.server.functions.medico;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;

public class CountMedicos implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			pw.println("\n >MEDICOS \n   +Cantidad: " + medicos.size());
			pw.println("\n OK " + cont + " 200 " + port + " " + address);
			pw.flush();
		} 
		catch (Exception e) 
		{
			pw.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pw.flush();
		}
	}
}
