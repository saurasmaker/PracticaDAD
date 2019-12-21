package edu.ucam.server.functions;

import java.util.ArrayList;
import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;

public class Singleton {
	
	/*********Funciones Paciente************************************************************************************/
	public static void addPaciente(Paciente paciente, ArrayList<Paciente> pacientes) {
		
		pacientes.add(paciente);
		
		return;
	}
	
	public static void updatePaciente(String idPaciente, Paciente paciente, ArrayList<Paciente> pacientes) {

		Paciente actualizar = Singleton.getPaciente(idPaciente, pacientes);
		actualizar.setApellidos(paciente.getApellidos());
		actualizar.setNombre(paciente.getNombre());
		actualizar.setFechaNacimiento(paciente.getFechaNacimiento());
		
		return;
	}
	
	public static Paciente getPaciente(String idPaciente, ArrayList<Paciente> pacientes) {
		
		for(Paciente m: pacientes)
			if(m.getId().equals(idPaciente))
				return m;
		
		return null;
	}
	
	
	public static void deletePaciente(String idMedico, ArrayList<Paciente> medicos) {
		
		for(Paciente m: medicos)
			if(m.getId().equals(idMedico)) {
				medicos.remove(m);
				return;
			}
		
		return;
	}
	
	//public static void listMedicos(String idMedico, ArrayList<Medico> medicos) {return;}
	
	public static Integer countPacientes(ArrayList<Paciente> medicos) {		
		return medicos.size();
	}/*****************************************/
	
	
	
	
	/**************Funciones Expediente***********************************************************************************************/
	public static void addExpediente(String idPaciente, String idMedico, String[] idsTratamientos, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos, ArrayList<Tratamiento> tratamientos, ArrayList<Expediente> expedientes){
		
		Expediente expediente = new Expediente();
		
		expediente.setMedico(Singleton.getMedico(idMedico, medicos));
		expediente.setPaciente(Singleton.getPaciente(idPaciente, pacientes));
		expediente.setTramientos(null);
		
		expedientes.add(expediente);
		
		return;
	}
	
	//new Server_Thread(ID, socket, expedientes, pacientes, medicos, ArrayList<Tratamiento> tratamientos)
	
	
	public static Expediente getExpediente(String idExpediente, ArrayList<Expediente> expedientes){
		
		for (Expediente e : expedientes) 
			if(e.getId().equals(idExpediente))
				return e;
			
		return null;
		
	}
	
	public static void removeExpediente(String id, ArrayList<Expediente> expedientes){
		
		for (Expediente e : expedientes) 
			if(e.getId().equals(id)) {
				expedientes.remove(e);
				return;
			}
			
		return;
		
	}
	
	//public static void listExpedientes() {return;}
	
	public static void addPaciente2Expediente(String idPaciente, String idExpediente, ArrayList<Paciente> pacientes, ArrayList<Expediente> expedientes) {
		
		
		return;
	}
	
	public static void removePacienteFromExpediente(String idPaciente, String idExpediente, ArrayList<Expediente> expedientes) {
		
		return;
	}
	
	public static void addMedico2Expediente(String idMedico, String idExpediente, ArrayList<Medico> medicos, ArrayList<Expediente> expedientes) {
		
		
		return;
	}
	
	public static void removeMedicoFromExpediente(String idMedico, String idExpediente, ArrayList<Expediente> expedientes) {
		
		return;
	}
	
	public static void addTratamiento2Expediente(String idPaciente, String idExpediente, ArrayList<Paciente> pacientes, ArrayList<Expediente> expedientes) {
		
		
		return;
	}
	
	public static void removeTratamientoFromExpediente(String idPaciente, String idExpediente, ArrayList<Expediente> expedientes) {
		
		return;
	}
	
	/***************************************/
	
	
	
	
	
	
	/**********Funciones Medico***************************************************************************************/
	public static void addMedico(Medico medico, ArrayList<Medico> medicos) {
		
		medicos.add(medico);
		
		return;
	}
	
	public static void updateMedico(String idMedico, Medico medico, ArrayList<Medico> medicos) {

		Medico actualizar = Singleton.getMedico(idMedico, medicos);
		actualizar.setApellidos(medico.getApellidos());
		actualizar.setNombre(medico.getNombre());
		actualizar.setEspecialidad(medico.getEspecialidad());
		
		return;
	}
	
	public static Medico getMedico(String idMedico, ArrayList<Medico> medicos) {
		
		for(Medico m: medicos)
			if(m.getId().equals(idMedico))
				return m;
		
		return null;
	}
	
	
	public static void deleteMedico(String idMedico, ArrayList<Medico> medicos) {
		
		for(Medico m: medicos)
			if(m.getId().equals(idMedico)) {
				medicos.remove(m);
				return;
			}
		
		return;
	}
	
	//public static void listMedicos(String idMedico, ArrayList<Medico> medicos) {return;}
	
	public static Integer countMedicos(ArrayList<Medico> medicos) {		
		return medicos.size();
	}
	/****************************************/
	

	
	
	/*********Funciones Tratamiento************************************************************************************/
	public static void addTratamiento(Tratamiento tratamiento, ArrayList<Tratamiento> tratamientos) {
		
		tratamientos.add(tratamiento);
		
		return;
	}
	
	public static void updateTratamiento(String idTratamiento, Tratamiento tratamiento, ArrayList<Tratamiento> tratamientos) {

		Tratamiento actualizar = Singleton.getTratamiento(idTratamiento, tratamientos);
		actualizar.setDescripcion(actualizar.getDescripcion());
		
		return;
	}
	
	public static Tratamiento getTratamiento(String idTratamiento, ArrayList<Tratamiento> tratamientos) {
		
		for(Tratamiento m: tratamientos)
			if(m.getId().equals(idTratamiento))
				return m;
		
		return null;
	}
	
	
	public static void deleteTratamiento(String idTratamiento, ArrayList<Tratamiento> tratamientos) {
		
		for(Tratamiento m: tratamientos)
			if(m.getId().equals(idTratamiento)) {
				tratamientos.remove(m);
				return;
			}
		
		return;
	}
	
	//public static void listMedicos(String idMedico, ArrayList<Medico> medicos) {return;}
	
	public static Integer countTratamiento(ArrayList<Tratamiento> tratamientos) {		
		return tratamientos.size();
	}/*****************************************/
	
	
	
	
}
