package edu.ucam.server.functions.paciente;

import java.util.ArrayList;

import edu.ucam.pojos.Paciente;

public class GeneratePacienteId{
	public static String run(ArrayList<Paciente> pacientes) {
		
		String id = pacientes.get(pacientes.size()-1).getId();
		Integer num = Integer.parseInt(id.substring(1));
		
		num++;
		
		return ("P"+num);
	}
}
