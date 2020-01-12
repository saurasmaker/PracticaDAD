package edu.ucam.server.functions.tratamiento;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetTratamiento implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData,  BufferedReader brData) 
	{		

		try 
		{
			Tratamiento tratamiento = Singleton.getTratamiento(brData.readLine(), tratamientos);
			oosData.writeObject(tratamiento);
			oosData.flush();
			pwCommands.println("OK " + cont + " 200 " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}
	}
}
