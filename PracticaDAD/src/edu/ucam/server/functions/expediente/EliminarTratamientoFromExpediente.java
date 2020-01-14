package edu.ucam.server.functions.expediente;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class EliminarTratamientoFromExpediente implements Comando{
	
	//Methods
	public static void run(ArrayList<Expediente> expedientes, ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pwCommands, BufferedReader brData) 
	{
		try 
		{
			Expediente expediente = Singleton.getExpediente(brData.readLine(), expedientes);
			Tratamiento tratamiento = Singleton.getTratamiento(brData.readLine(), tratamientos);
			
			expediente.removeTratamiento(tratamiento);
			
			pwCommands.println("OK " + cont + " 0 Tratamiento eliminado " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}
