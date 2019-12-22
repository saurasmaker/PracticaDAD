package edu.ucam.server.functions.medico;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetMedico implements Comando{
	public static void run(String idMedico, ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pw) 
	{		
		try 
		{
			Medico medico = Singleton.getMedico(idMedico, medicos);
			pw.println("   +Medico: " +"\n     -Nombre: " +medico.getNombre() + "\n     -Apellidos: "  + medico.getApellidos() + "\n     -Especialidad: "  + medico.getEspecialidad());
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
