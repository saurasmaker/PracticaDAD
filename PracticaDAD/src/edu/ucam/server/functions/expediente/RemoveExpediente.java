package edu.ucam.server.functions.expediente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class RemoveExpediente implements Comando{
	public static void run(String idExpediente, ArrayList<Expediente> expedientes, int cont, String address, int port, PrintWriter pw) 
	{
		try 
		{

			for (Expediente e : expedientes) {
				if(e.getId().equals(Singleton.getExpediente(idExpediente, expedientes).getId()))
				{
					expedientes.remove(e);
					pw.println("\n OK " + cont + " 200 " + address + " " + port);
					pw.flush();
					return;
				}
			}	
		} 
		catch (Exception t) 
		{
			pw.println("FAILED " + cont + " codrespuesta " + t.getMessage());
			pw.flush();
		}	
	}
}
