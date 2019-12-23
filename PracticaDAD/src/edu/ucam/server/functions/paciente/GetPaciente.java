package edu.ucam.server.functions.paciente;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetPaciente implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pw, ObjectOutputStream oos, BufferedReader brData) 
	{		
		try 
		{
			Paciente p = Singleton.getPaciente(brData.readLine(), pacientes);
			oos.writeObject(p);
			oos.flush();
		} 
		catch (Exception e) 
		{
			pw.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pw.flush();
		}
	}
}
