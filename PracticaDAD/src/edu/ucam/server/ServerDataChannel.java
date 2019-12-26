package edu.ucam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDataChannel{
	
	//Atributes
	private PrintWriter pw;
	private BufferedReader br;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerSocket serverSocket;
	private Socket socket;
	private Integer port = 2022;
	
	
	//Constructors
	public ServerDataChannel() {
		this.setConnection();
		this.setBridges();
	}
	
	
	//Methods
	private void setConnection() {
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("waiting for client...");
			socket = serverSocket.accept();	
			System.out.println("connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	
	private void setBridges() {
		
		try {
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

	
}
