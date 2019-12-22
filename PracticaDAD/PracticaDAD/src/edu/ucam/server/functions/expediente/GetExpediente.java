package edu.ucam.server.functions.expediente;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class GetExpediente implements Comando{
	

	//Atributes
	
	//Methods
	public static void run(String idExpediente, ArrayList<Expediente> expedientes, PrintWriter pw, int cont, int port, String address) 
	{
		
		
		try 
		{

			for (Expediente expediente : expedientes) {
				if(expediente.getId().equals(Singleton.getExpediente(idExpediente, expedientes).getId()))
				{
					pw.println("\n >EXPEDIENTE \n   +ID: " + expediente.getId());
					pw.println("   +Observaciones: " + expediente.getObservaciones());
					pw.println("   +Medico: " + "\n     -Nombre: " + expediente.getMedico().getNombre() + "\n     -Apellidos: "  + expediente.getMedico().getApellidos() + "\n     -Especialidad: " + expediente.getMedico().getEspecialidad());
					pw.println("   +Paciente: " +"\n     -Nombre: " +expediente.getPaciente().getNombre() + "\n     -Apellidos: "  + expediente.getPaciente().getApellidos() + "\n     -Fecha de nacimiento: "  + expediente.getPaciente().getFechaNacimiento());
					pw.println("   +Tratamientos: ");
					for(Tratamiento t: expediente.getTramientos()) 
						pw.println("     -" + t.getDescripcion());
		
					pw.println("\n");
						
					pw.println("\nOK " + cont + " 200 " + port + " " + address);
					pw.flush();
					return;
				}
			}	
		} 
		catch (Exception e) 
		{
			System.out.println("Error: " + e);
			pw.println("Failed " + cont + " 400 Mensaje error." );
			pw.flush();
		}	
	}
}
