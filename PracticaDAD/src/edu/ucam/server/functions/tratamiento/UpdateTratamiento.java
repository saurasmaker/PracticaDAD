package edu.ucam.server.functions.tratamiento;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class UpdateTratamiento implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pwCommands, ObjectInputStream oisData, ObjectOutputStream oosData) 
	{		
		try {
			Tratamiento tratamiento = null;
			tratamiento = Singleton.getTratamiento((String)oisData.readObject(), tratamientos);

			oosData.writeObject(tratamiento);
			oosData.flush();
			tratamiento = (Tratamiento)oisData.readObject();
			
			Singleton.updateTratamiento(tratamiento, tratamientos);
			
			pwCommands.println("OK " + cont + " 200 " + port + " " + address);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}
	}
}
