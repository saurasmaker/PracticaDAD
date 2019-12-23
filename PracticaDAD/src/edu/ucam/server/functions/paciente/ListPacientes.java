package edu.ucam.server.functions.paciente;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;

public class ListPacientes implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, String address, int port, PrintWriter pwCommands, ObjectOutputStream oosData) 
	{		
		try 
		{
			for(Paciente p: pacientes) {
				oosData.writeObject(p);
				oosData.flush();
			}
				//("   +Paciente: " +"\n     -Nombre: " +p.getNombre() + "\n     -Apellidos: "  + p.getApellidos() + "\n     -Fecha de nacimiento: "  + p.getFechaNacimiento());
			
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
