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

import edu.ucam.client.ClientDataChannel;
import edu.ucam.client.ClientThreadCommands;
import edu.ucam.pojos.Tratamiento;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class ActualizarTratamiento extends JInternalFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private JTextArea textAreaDescripcion;
	private Dimension dimensionBarra = null;
	private PrintWriter pw;
	private Integer dataPort;
	private ClientThreadCommands clientThreadCommands;
	private ClientDataChannel cdc;
	
	
	private Tratamiento tratamientoActualizar;
	/**
	 * Create the frame.
	 */
	public ActualizarTratamiento(Tratamiento tratamiento, ClientDataChannel cdc, ClientThreadCommands clientThreadCommands) {
		this.setCdc(cdc);
		this.setTratamientoActualizar(tratamiento);
		this.setClientThreadCommands(clientThreadCommands);
		this.setDataPort(dataPort);
		setBounds(100, 100, 483, 343);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel labelTratamiento = new JLabel("Actualizar Tratamiento");
		labelTratamiento.setHorizontalAlignment(SwingConstants.CENTER);
		labelTratamiento.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton buttonAnadir = new JButton("Actualizar");
		buttonAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendData();
				dispose();
			}
		});
		
		JButton buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendNull();
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
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
							.addComponent(buttonAnadir))
						.addComponent(labelDescripcion, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addComponent(labelTratamiento, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonCancelar)
						.addComponent(buttonAnadir))
					.addContainerGap())
		);
		
		textAreaDescripcion = new JTextArea();
		scrollPane.setViewportView(textAreaDescripcion);
		getContentPane().setLayout(groupLayout);
		
		
		textAreaDescripcion.setText(tratamientoActualizar.getDescripcion());
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
		
		if(checkData()) {
			
			tratamientoActualizar.setDescripcion(textAreaDescripcion.getText());
			
			try {
				cdc.getOos().writeObject(tratamientoActualizar);
				cdc.getOos().flush();
			}
			catch(Exception t) {
					
			}
			
		}
		
		return;
	}
	

	private void sendNull() {

		try {

			cdc.getOos().writeObject(null);
			cdc.getOos().flush();
		}
		catch(Exception t) {
		
		}

		return;
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

	public ClientThreadCommands getClientThreadCommands() {
		return clientThreadCommands;
	}

	public void setClientThreadCommands(ClientThreadCommands clientThreadCommands) {
		this.clientThreadCommands = clientThreadCommands;
	}

	public Tratamiento getTratamientoActualizar() {
		return tratamientoActualizar;
	}

	public void setTratamientoActualizar(Tratamiento tratamientoActualizar) {
		this.tratamientoActualizar = tratamientoActualizar;
	}

	public ClientDataChannel getCdc() {
		return cdc;
	}

	public void setCdc(ClientDataChannel cdc) {
		this.cdc = cdc;
	}
}
