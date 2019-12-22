package edu.ucam.server.functions.tratamiento;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;

public class CountTratamientos implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			pw.println("\n >TRATAMIENTOS \n   +Cantidad: " + tratamientos.size());
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
