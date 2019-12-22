package edu.ucam.server.functions.tratamiento;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddTratamiento implements Comando{
	public static void run(Tratamiento tratamiento, ArrayList<Tratamiento> tratamientos, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			Singleton.addTratamiento(tratamiento, tratamientos);
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
