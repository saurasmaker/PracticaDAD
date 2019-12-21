package edu.ucam.client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.client.frames.ClientFrame;

public class ClientMain{

	public static void main(String[] args) {
		
		//Variables
		ClientThread clientThread = null;
		Socket socket;
		BufferedReader br = null;
		PrintWriter pw = null; 
		/****************************/
		
		//Establecemos conexion***************************************************************/
		try {
			socket = new Socket("localhost", 2020);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
		} catch (Exception e) {
			e.printStackTrace();
		}/*************************************************************************************/
		
		
		
		//Ejecutamos ventana del Cliente********************************************************/
		ClientFrame frame = new ClientFrame(pw);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});/************************************************************************************/
		
		
		//Ejecutamos el hilo que escuchará al servidor******************************************/
		clientThread = new ClientThread(frame, br);
		clientThread.run();
		
		
		
	}

}
