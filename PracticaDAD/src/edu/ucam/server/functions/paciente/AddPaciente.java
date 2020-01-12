package edu.ucam.server.functions.paciente;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Paciente;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class AddPaciente implements Comando{
	public static void run(ArrayList<Paciente> pacientes, int cont, int port, String address, PrintWriter pwCommands, ObjectInputStream oisData) 
	{		
		try 
		{
			Paciente paciente = (Paciente)oisData.readObject();
			paciente.setId(GeneratePacienteId.run(pacientes));
			System.out.println("Id Generada: " + paciente.getId());
			Singleton.addPaciente(paciente, pacientes);
			pwCommands.println("OK " + cont + " 200 " + port + " " + address);
			pwCommands.flush();
		} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}
	}
}
