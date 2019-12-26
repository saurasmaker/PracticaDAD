package edu.ucam.server.functions.medico;

import java.util.ArrayList;

import edu.ucam.pojos.Medico;

public class GenerateMedicoId {
	
	public static String run(ArrayList<Medico> medicos) {
		Integer num = 0;
		try {
			String id = medicos.get(medicos.size()-1).getId();
			num = Integer.parseInt(id.substring(1));
		
			num++;
		}
		catch(Exception t) {
			
		}
		
		return ("M"+num);
	}
	
}
