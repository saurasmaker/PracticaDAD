package edu.ucam.server.functions.paciente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;

public class ListPacientes implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			for(Paciente p: pacientes)
				pw.println("   +Paciente: " +"\n     -Nombre: " +p.getNombre() + "\n     -Apellidos: "  + p.getApellidos() + "\n     -Fecha de nacimiento: "  + p.getFechaNacimiento());
			
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
