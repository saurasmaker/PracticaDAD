package edu.ucam.server.functions.paciente;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class UpdatePaciente implements Comando{
	public static void run(String idPaciente, ArrayList<Paciente> pacientes, int cont, int port, String address, PrintWriter pw, ObjectInputStream ois) 
	{		
		try 
		{
			Singleton.updatePaciente(idPaciente, (Paciente)ois.readObject(), pacientes);
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
