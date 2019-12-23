package edu.ucam.server.functions.medico;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData, BufferedReader brData) 
	{		
		try 
		{
			Medico medico = Singleton.getMedico(brData.readLine(), medicos);
			oosData.writeObject(medico);
			oosData.flush();
			//("   +Medico: " +"\n     -Nombre: " +medico.getNombre() + "\n     -Apellidos: "  + medico.getApellidos() + "\n     -Especialidad: "  + medico.getEspecialidad());
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
