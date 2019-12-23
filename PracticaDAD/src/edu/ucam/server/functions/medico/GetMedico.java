package edu.ucam.server.functions.medico;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, String address, int port, PrintWriter pw, ObjectOutputStream oos, BufferedReader brData) 
	{		
		try 
		{
			Medico medico = Singleton.getMedico(brData.readLine(), medicos);
			oos.writeObject(medico);
			oos.flush();
			//("   +Medico: " +"\n     -Nombre: " +medico.getNombre() + "\n     -Apellidos: "  + medico.getApellidos() + "\n     -Especialidad: "  + medico.getEspecialidad());
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
