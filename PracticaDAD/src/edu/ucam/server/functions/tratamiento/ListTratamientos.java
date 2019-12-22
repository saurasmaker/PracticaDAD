package edu.ucam.server.functions.tratamiento;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;

public class ListTratamientos implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			for(Tratamiento t: tratamientos)
				pw.println("   +Tratamiento: " +"\n     -Descripción: " +t.getDescripcion());
			
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
