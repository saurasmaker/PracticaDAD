package edu.ucam.server.functions.medico;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;

public class ListMedicos implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pw, ObjectOutputStream oos) 
	{		
		try 
		{
			for(Medico p: medicos) {
				oos.writeObject(p);
				oos.flush();
			}
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
