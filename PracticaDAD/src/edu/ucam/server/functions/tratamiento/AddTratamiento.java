package edu.ucam.server.functions.tratamiento;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddTratamiento implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pw, ObjectInputStream ois) 
	{		
		try 
		{
			Singleton.addTratamiento((Tratamiento)ois.readObject(), tratamientos);
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
