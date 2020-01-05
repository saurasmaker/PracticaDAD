package edu.ucam.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientDataChannel{
	
	//Atributes
	private PrintWriter pw;
	private BufferedReader br;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket;
	public Integer port = 2022;
	
	
	//Constructors
	public ClientDataChannel(Integer port) {
		this.port = port;
		this.setConnection();
		this.setBridges();
	}
	
	
	//Methods
	private void setConnection() {
		
		try {
			socket = new Socket("localhost", port);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	
	private void setBridges() {
		
		try {
			//this.setOos(new ObjectOutputStream(socket.getOutputStream()));
			this.setBr(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			this.setPw(new PrintWriter(new OutputStreamWriter(socket.getOutputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	public String readData() {
		
		String data = null;
		
		try {
			data = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public Object readObject() {
		
		Object object = null;
		
		try {
			object = ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	public void closeChannel() {
		
		try {
			socket.close();
			System.out.println("cerrado " + socket.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	//Getters & Setters
	public PrintWriter getPw() {
		return pw;
	}


	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}


	public BufferedReader getBr() {
		return br;
	}


	public void setBr(BufferedReader br) {
		this.br = br;
	}


	public ObjectInputStream getOis() {
		return ois;
	}


	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}


	public ObjectOutputStream getOos() {
		return oos;
	}


	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}


	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
}
