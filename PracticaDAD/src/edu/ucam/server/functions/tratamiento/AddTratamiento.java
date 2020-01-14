package edu.ucam.server.functions.tratamiento;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddTratamiento implements Comando{
	public static void run(ArrayList<Tratamiento> tratamientos, int cont, String address, int port, PrintWriter pwCommands, ObjectInputStream oisData) 
	{		
		
		try {
			Tratamiento tratamiento = (Tratamiento)oisData.readObject();
			tratamiento.setId(GenerateTratamientoId.run(tratamientos));
			System.out.println("Id generada: " + tratamiento.getId());
			Singleton.addTratamiento(tratamiento, tratamientos);
			pwCommands.println("OK " + cont + " 0 " + address + " " + port);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " -1 " + "Mensaje error");
			pwCommands.flush();
		}
	}
}
