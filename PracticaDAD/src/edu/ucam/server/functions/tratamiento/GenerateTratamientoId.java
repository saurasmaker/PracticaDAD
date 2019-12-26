package edu.ucam.server.functions.tratamiento;

import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;

public class GenerateTratamientoId{
	public static String run(ArrayList<Tratamiento> tratamientos) {
		Integer num = 0;
		try {
			String id = tratamientos.get(tratamientos.size()-1).getId();
			num = Integer.parseInt(id.substring(1));
		
			num++;
		}catch(Exception t) {
			
		}
		
		return ("T"+num);
	}
}
