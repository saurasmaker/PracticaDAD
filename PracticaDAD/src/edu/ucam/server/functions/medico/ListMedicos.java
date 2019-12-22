package edu.ucam.server.functions.medico;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;

public class ListMedicos implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			for(Medico m: medicos)
				pw.println("   +Medico: " +"\n     -Nombre: " +m.getNombre() + "\n     -Apellidos: "  + m.getApellidos() + "\n     -Especialidad: "  + m.getEspecialidad());
			
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
