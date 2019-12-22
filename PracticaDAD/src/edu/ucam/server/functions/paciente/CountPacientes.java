package edu.ucam.server.functions.paciente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;

public class CountPacientes implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, int port, String address, PrintWriter pw) 
	{		
		try 
		{
			pw.println("\n >PACIENTES \n   +Cantidad: " + pacientes.size());
			pw.println("\n OK " + cont + " 200 " + port + " " + address);
			pw.flush();
		} 
		catch (Exception e) 
		{
			pw.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pw.flush();
		}
	}
}
