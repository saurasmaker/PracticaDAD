package edu.ucam.server.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.functions.expediente.GenerateExpedienteId;

public class Singleton {
	
	/*********Funciones Generales************************************************************************************/
	public static void loadData(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos, ArrayList<Tratamiento> tratamientos, ArrayList<Expediente> expedientes) {
		File f = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    
	    try {/****************Cargar Pacientes**************/
	         f = new File (System.getProperty("user.dir") + "\\data\\pacientes\\pacientes.txt");
	         fr = new FileReader (f);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         BufferedReader brp = null;
	         String linea = null;
	         while((linea = br.readLine()) != null ) {
				Paciente p = new Paciente();
				File fp = new File (System.getProperty("user.dir") + "\\data\\pacientes\\"+linea+".txt");
				FileReader frp = new FileReader (fp);
				brp = new BufferedReader(frp);
				
				p.setId(linea);
				p.setNombre(brp.readLine());
				p.setApellidos(brp.readLine());
				String fecha[] = null;
				Date date = null;
				try {
					fecha = brp.readLine().split("/");
					date  = new SimpleDateFormat("dd/MM/yyyy").parse(Integer.parseInt(fecha[0]) + "/" + Integer.parseInt(fecha[1]) + "/" + Integer.parseInt(fecha[2]));
				}
				catch(Exception t) {
					
				}
				p.setFechaNacimiento(date);
				
				pacientes.add(p);

	         }
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	        try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }/****************************************/
	    
	    
	    try {/****************Cargar Médicos**************/
	         f = new File (System.getProperty("user.dir") + "\\data\\medicos\\medicos.txt");
	         fr = new FileReader (f);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         BufferedReader brp = null;
	         String linea = null;
	         while((linea = br.readLine()) != null ) {
				Medico m = new Medico();
				File fp = new File (System.getProperty("user.dir") + "\\data\\medicos\\" + linea + ".txt");
				FileReader frp = new FileReader (fp);
				brp = new BufferedReader(frp);
				
				m.setId(linea);
				m.setNombre(brp.readLine());
				m.setApellidos(brp.readLine());
				m.setEspecialidad(brp.readLine());
				
				medicos.add(m);
			}
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }/****************************************/
	    
	    
	    try {/****************Cargar Tratamientos**************/
	         f = new File (System.getProperty("user.dir") + "\\data\\tratamientos\\tratamientos.txt");
	         fr = new FileReader (f);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         BufferedReader brp = null;
	         String linea = null;
	         while((linea = br.readLine()) != null ) {
				Tratamiento t = new Tratamiento();
				File fp = new File (System.getProperty("user.dir") + "\\data\\tratamientos\\" + linea + ".txt");
				FileReader frp = new FileReader (fp);
				brp = new BufferedReader(frp);
				
				t.setId(linea);
				t.setDescripcion(brp.readLine());
				
				tratamientos.add(t);
			}
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }/****************************************/
	    
	    
	    try {/****************Cargar Expedientes**************/
	         f = new File (System.getProperty("user.dir") + "\\data\\expedientes\\expedientes.txt");
	         fr = new FileReader (f);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         BufferedReader brp = null;
	         String linea = null;
	         while((linea = br.readLine()) != null ) {
				Expediente e = new Expediente();
				File fp = new File (System.getProperty("user.dir") + "\\data\\expedientes\\"+linea+".txt");
				FileReader frp = new FileReader (fp);
				brp = new BufferedReader(frp);
				
				e.setId(linea);
				e.setPaciente(Singleton.getPaciente(brp.readLine(), pacientes));
				e.setMedico(Singleton.getMedico(brp.readLine(), medicos));
				e.setObservaciones(brp.readLine());
				String trat = null;
				
				try {
					while((trat = brp.readLine())!=null) 
						e.addTramiento(Singleton.getTratamiento(trat, tratamientos));
				}
				catch(Exception t) {
					
				}
				
				expedientes.add(e);
			}
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }/****************************************/

		return;
	}
	
	public static void saveData(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos, ArrayList<Tratamiento> tratamientos, ArrayList<Expediente> expedientes) {
		
		FileWriter fichero = null;
        PrintWriter pw = null;
        
        try {/****************Guardar Paciente**************/
        	fichero = new FileWriter(System.getProperty("user.dir") + "\\data\\pacientes\\pacientes.txt");
            pw = new PrintWriter(fichero);
            PrintWriter pwp = null;
            for(Paciente p: pacientes) {
            	FileWriter fp = new FileWriter(System.getProperty("user.dir") + "\\data\\pacientes\\" + p.getId() + ".txt");
            	pw.println(p.getId());
            	pw.flush();
            	pwp = new PrintWriter(fp);

            	pwp.println(p.getNombre());
            	pwp.flush();
            	pwp.println(p.getApellidos());
            	pwp.flush();
            	pwp.println(new SimpleDateFormat("dd/MM/yyyy").format(p.getFechaNacimiento()));
            	pwp.flush();
            }
            	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        	   if (null != fichero)
        		   fichero.close();
           	} catch (Exception e2) {
           		e2.printStackTrace();
           	}
        }/****************************************/
        
        
        try {/****************Guardar Medico**************/
        	fichero = new FileWriter(System.getProperty("user.dir") + "\\data\\medicos\\medicos.txt");
            pw = new PrintWriter(fichero);
            PrintWriter pwp = null;
            for(Medico m: medicos) {
            	FileWriter fp = new FileWriter(System.getProperty("user.dir") + "\\data\\medicos\\" + m.getId() + ".txt");
            	pw.println(m.getId());
            	pw.flush();
            	pwp = new PrintWriter(fp);

            	pwp.println(m.getNombre());
            	pwp.flush();
            	pwp.println(m.getApellidos());
            	pwp.flush();
            	pwp.println(m.getEspecialidad());
            	pwp.flush();
            	
            	try {
             	   if (null != fp)
             		   fp.close();
                } catch (Exception e2) {
                	e2.printStackTrace();
                }
            }
            	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        	   if (null != fichero)
        		   fichero.close();
           	} catch (Exception e2) {
           		e2.printStackTrace();
           	}
        }/****************************************/
        
        
        try {/****************Guardar Tratamiento**************/
        	fichero = new FileWriter(System.getProperty("user.dir") + "\\data\\tratamientos\\tratamientos.txt");
            pw = new PrintWriter(fichero);
            PrintWriter pwp = null;
            for(Tratamiento t: tratamientos) {
            	FileWriter fp = new FileWriter(System.getProperty("user.dir") + "\\data\\tratamientos\\" + t.getId() + ".txt");
            	pw.println(t.getId());
            	pw.flush();
            	pwp = new PrintWriter(fp);
            	
            	pwp.println(t.getDescripcion());
            	pwp.flush();
            	
            	try {
              	   if (null != fp)
              		   fp.close();
                 } catch (Exception e2) {
                 	e2.printStackTrace();
                 }
            }
            	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        	   if (null != fichero)
        		   fichero.close();
           	} catch (Exception e2) {
           		e2.printStackTrace();
           	}
        }/****************************************/
        
        
        try {/****************Guardar Expediente**************/
        	fichero = new FileWriter(System.getProperty("user.dir") + "\\data\\expedientes\\expedientes.txt");
            pw = new PrintWriter(fichero);
            PrintWriter pwp = null;
            for(Expediente e: expedientes) {
            	FileWriter fp = new FileWriter(System.getProperty("user.dir") + "\\data\\expedientes\\" + e.getId() + ".txt");
            	pw.println(e.getId());
            	pw.flush();
            	
            	pwp = new PrintWriter(fp);
            	
            	pwp.println(e.getPaciente().getId());
            	pwp.flush();
            	pwp.println(e.getMedico().getId());
            	pwp.flush();
            	pwp.println(e.getObservaciones());
            	pwp.flush();
            	
            	for(Tratamiento t: e.getTramientos()) {
            		pwp.println(t.getId());
            		pwp.flush();
            		
            	}
            	
            	try {
               	   if (null != fp)
               		   fp.close();
                  } catch (Exception e2) {
                  	e2.printStackTrace();
                  }
            }
            	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        	   if (null != fichero)
        		   fichero.close();
           	} catch (Exception e2) {
           		e2.printStackTrace();
           	}
        }/****************************************/
        
        
	}
	/*****************************************/
	
	
	
	
	/*********Funciones Paciente************************************************************************************/
	public static void addPaciente(Paciente paciente, ArrayList<Paciente> pacientes) {
		
		pacientes.add(paciente);
		
		return;
	}
	
	public static void updatePaciente(Paciente paciente, ArrayList<Paciente> pacientes) {

		Paciente actualizar = Singleton.getPaciente(paciente.getId(), pacientes);
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
	
	
	public static void removePaciente(String idMedico, ArrayList<Paciente> medicos) {
		
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
	public static void addExpediente(String idPaciente, String idMedico, String[] idsTratamientos, String observaciones, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos, ArrayList<Tratamiento> tratamientos, ArrayList<Expediente> expedientes){
		
		Expediente expediente = new Expediente();
		
		expediente.setMedico(Singleton.getMedico(idMedico, medicos));
		expediente.setPaciente(Singleton.getPaciente(idPaciente, pacientes));
		for(String s: idsTratamientos) {
			Tratamiento tr = Singleton.getTratamiento(s, tratamientos);
			if(tr!=null)
				expediente.addTramiento(tr);
		}
		expediente.setObservaciones(observaciones);
		expediente.setId(GenerateExpedienteId.run(expedientes));
		
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
	/***************************************/
	
	
	
	
	
	
	/**********Funciones Medico***************************************************************************************/
	public static void addMedico(Medico medico, ArrayList<Medico> medicos) {
		
		medicos.add(medico);
		
		return;
	}
	
	public static void updateMedico(Medico medico, ArrayList<Medico> medicos) {
		
		Medico actualizar = Singleton.getMedico(medico.getId(), medicos);
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
	
	
	public static void removeMedico(String idMedico, ArrayList<Medico> medicos) {
		
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
	
	public static void updateTratamiento(Tratamiento tratamiento, ArrayList<Tratamiento> tratamientos) {

		Tratamiento actualizar = Singleton.getTratamiento(tratamiento.getId(), tratamientos);
		actualizar.setDescripcion(tratamiento.getDescripcion());
		
		return;
	}
	
	public static Tratamiento getTratamiento(String idTratamiento, ArrayList<Tratamiento> tratamientos) {
		
		for(Tratamiento m: tratamientos)
			if(m.getId().equals(idTratamiento))
				return m;
		
		return null;
	}
	
	
	public static void removeTratamiento(String idTratamiento, ArrayList<Tratamiento> tratamientos) {
		
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
