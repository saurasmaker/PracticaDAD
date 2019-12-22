package edu.ucam.server.functions.tratamiento;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveTratamiento implements Comando{
	public static void run(String idTratamiento, ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			Singleton.removeTratamiento(idTratamiento, tratamientos);
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
