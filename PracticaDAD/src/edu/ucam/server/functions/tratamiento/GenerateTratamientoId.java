package edu.ucam.server.functions.tratamiento;

import java.util.ArrayList;

import edu.ucam.pojos.Tratamiento;

public class GenerateTratamientoId{
	public static String run(ArrayList<Tratamiento> tratamientos) {
		
		String id = tratamientos.get(tratamientos.size()-1).getId();
		Integer num = Integer.parseInt(id.substring(1));
		
		num++;
		
		return ("T"+num);
	}
}
