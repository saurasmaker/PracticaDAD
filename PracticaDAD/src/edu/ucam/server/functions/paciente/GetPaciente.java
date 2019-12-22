package edu.ucam.server.functions.paciente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetPaciente implements Comando{
	public static void run(String idPaciente, ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			Paciente paciente = Singleton.getPaciente(idPaciente, pacientes);
			pw.println("   +Paciente: " +"\n     -Nombre: " +paciente.getNombre() + "\n     -Apellidos: "  + paciente.getApellidos() + "\n     -Fecha de nacimiento: "  + paciente.getFechaNacimiento());
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
