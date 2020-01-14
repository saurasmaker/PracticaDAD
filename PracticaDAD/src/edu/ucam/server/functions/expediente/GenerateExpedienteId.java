package edu.ucam.server.functions.expediente;

import java.util.ArrayList;

import edu.ucam.pojos.Expediente;

public class GenerateExpedienteId {
	
	public static String run(ArrayList<Expediente> expedientes) {	
		Integer num = 0;
		try {
			String id = expedientes.get(expedientes.size()-1).getId();
			num = Integer.parseInt(id.substring(1));
		
			num++;
		}
		catch(Exception t) {
			
		}
		
		return ("E"+num);
	}
}
