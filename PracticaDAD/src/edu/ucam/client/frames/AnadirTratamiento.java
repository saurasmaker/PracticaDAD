package edu.ucam.client.frames;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.ucam.pojos.Tratamiento;
import edu.ucam.server.ServerDataChannel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class AnadirTratamiento extends JInternalFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private JTextArea textAreaDescripcion;
	private Dimension dimensionBarra = null;
	private PrintWriter pw;
	private Integer dataPort;
	
	/**
	 * Create the frame.
	 */
	public AnadirTratamiento(Integer dataPort) {
		this.setDataPort(dataPort);
		setBounds(100, 100, 483, 343);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel labelTratamiento = new JLabel("A\u00F1adir Tratamiento");
		labelTratamiento.setHorizontalAlignment(SwingConstants.CENTER);
		labelTratamiento.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton buttonAnadir = new JButton("A\u00F1adir");
		
		JButton buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cancelAddTratamiento();
				dispose();
			}
		});
		
		JLabel labelDescripcion = new JLabel("Descripci\u00F3n:");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
						.addComponent(labelTratamiento, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
							.addComponent(buttonAnadir, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addComponent(labelDescripcion))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelTratamiento, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(labelDescripcion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonCancelar)
						.addComponent(buttonAnadir))
					.addContainerGap())
		);
		
		textAreaDescripcion = new JTextArea();
		scrollPane.setViewportView(textAreaDescripcion);
		getContentPane().setLayout(groupLayout);
		
		buttonAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				sendData();
				System.out.println("sended");
			}
		});
	}
	
	//Methods
	public void quitarLaBarraTitulo()
	{
		Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
		setDimensionBarra((Barra.getPreferredSize()));
		Barra.setSize(0,0);
		Barra.setPreferredSize(new Dimension(0,0));
		repaint();
	}
	
	private void sendData() {
		
		Tratamiento tratamiento = new Tratamiento();
		ServerDataChannel sdc = new ServerDataChannel(dataPort);
		
		if(checkData()) {
			
			tratamiento.setDescripcion(textAreaDescripcion.getText());
			
			try {
				this.pw.println("ADDTRATAMIENTO");
				this.pw.flush();
				sdc.getOos().writeObject(tratamiento);
			}
			catch(Exception t) {
			
			}
			
			textAreaDescripcion.setText(null);
		}
		
		return;
	}
	
	private void cancelAddTratamiento() {
		try {
			this.pw.println("EXIT ADD TRATAMIENTO");
			pw.flush();
		}
		catch(Exception t) {
		
		}
	}
	
	private Boolean checkData() {
		if(textAreaDescripcion.getText() == null) {
			JOptionPane.showMessageDialog(null,"You must complete user name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
		
	//Getters & Setters
	public Dimension getDimensionBarra() {
		return dimensionBarra;
	}

	public void setDimensionBarra(Dimension dimensionBarra) {
		this.dimensionBarra = dimensionBarra;
	}
	
	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public Integer getDataPort() {
		return dataPort;
	}

	public void setDataPort(Integer dataPort) {
		this.dataPort = dataPort;
	}
}
