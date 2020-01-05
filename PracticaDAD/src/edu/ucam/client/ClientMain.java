package edu.ucam.client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.client.frames.ClientLogin;

public class ClientMain{

	public static void main(String[] args) {
		
		//Variables
		ClientThreadCommands clientThreadCommands = null;
		Socket socket;
		BufferedReader br = null;
		PrintWriter pw = null; 
		Integer dataPort = 2021;
		/****************************/
		
		//Establecemos conexion***************************************************************/
		try {
			socket = new Socket("localhost", 2020);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
		} catch (Exception e) {
			e.printStackTrace();
		}/*************************************************************************************/
		
		
		
		//Ejecutamos ventana de Login****************************/
		ClientLogin login = new ClientLogin(pw, clientThreadCommands, dataPort);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login.setVisible(true);
				} catch(Exception t) {
					t.printStackTrace();
				}
			}
		});
		

		//Ejecutamos el hilo que escuchará al servidor******************************************/
		clientThreadCommands = new ClientThreadCommands(login, br, pw, dataPort);
		clientThreadCommands.run();	
	}
}
