package edu.ucam.server.functions.expediente;

import java.util.ArrayList;

import edu.ucam.pojos.Expediente;

public class GenerateExpedienteId {
	public static String run(ArrayList<Expediente> expedientes) {
		
		String id = expedientes.get(expedientes.size()-1).getId();
		Integer num = Integer.parseInt(id.substring(1));
		
		num++;
		
		return ("E"+num);
	}
}
