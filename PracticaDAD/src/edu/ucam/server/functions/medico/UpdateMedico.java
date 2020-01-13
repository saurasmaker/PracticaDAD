package edu.ucam.server.functions.medico;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ucam.pojos.Medico;
import edu.ucam.server.functions.Comando;
import edu.ucam.server.functions.Singleton;

public class UpdateMedico implements Comando{
	public static void run(ArrayList<Medico> medicos, int cont, int port, String address, PrintWriter pwCommands, ObjectInputStream oisData, ObjectOutputStream oosData) 
	{		
		
			Medico medico = null;
			try {
				medico = Singleton.getMedico((String)oisData.readObject(), medicos);
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				oosData.writeObject(medico);
				oosData.flush();

			} catch (IOException e) {
				System.out.println("Falla al enviar medico");
				e.printStackTrace();
			}
			
			try {
				medico = (Medico)oisData.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Falla al leer MEDICO");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Falla al leer MEDICO");

				e.printStackTrace();
			}
			
			Singleton.updateMedico(medico, medicos);
			
			pwCommands.println("OK " + cont + " 200 " + port + " " + address);
			pwCommands.flush();
		/*} 
		catch (Exception e) 
		{
			pwCommands.println("FAILED " + cont + " codrespuesta " + e.getMessage());
			pwCommands.flush();
		}*/
	}	
}
