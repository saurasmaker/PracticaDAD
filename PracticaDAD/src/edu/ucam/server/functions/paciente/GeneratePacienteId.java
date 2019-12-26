package edu.ucam.server.functions.paciente;

import java.util.ArrayList;

import edu.ucam.pojos.Paciente;

public class GeneratePacienteId{
	public static String run(ArrayList<Paciente> pacientes) {
		Integer num = 0;
		try {
			String id = pacientes.get(pacientes.size()-1).getId();
			num = Integer.parseInt(id.substring(1));
		
			num++;
		}catch(Exception t) {
			
		}
		
		return ("P"+num);
	}
}
