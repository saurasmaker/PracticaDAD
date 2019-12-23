package edu.ucam.server.functions.medico;

import java.util.ArrayList;

import edu.ucam.pojos.Medico;

public class GenerateMedicoId {
	
	public static String run(ArrayList<Medico> medicos) {
		
		String id = medicos.get(medicos.size()-1).getId();
		Integer num = Integer.parseInt(id.substring(1));
		
		num++;
		
		return ("M"+num);
	}
	
}
