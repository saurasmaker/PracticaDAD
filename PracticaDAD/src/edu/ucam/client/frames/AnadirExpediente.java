package edu.ucam.client.frames;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.ucam.client.ClientDataChannel;
import edu.ucam.client.ClientThreadCommands;
import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Paciente;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AnadirExpediente extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField textFieldPaciente;
	private JTextField textFieldMedico;
	private JList<String> listTratamientos;
	private JTextArea textAreaObservaciones;

	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	private PrintWriter pw;
	private ClientThreadCommands clientThreadCommands;
	private JTextField textFieldTratamiento;
	
	/**
	 * Create the frame.
	 */
	public AnadirExpediente(PrintWriter pw, ClientThreadCommands clientThreadCommands) {
		this.pw = pw;
		this.setClientThreadCommands(clientThreadCommands);
		setBounds(100, 100, 483, 394);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel lblNewAnadirExpediente = new JLabel("A\u00F1adir Expediente");
		lblNewAnadirExpediente.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewAnadirExpediente.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblPaciente = new JLabel("Id del Paciente:");
		
		textFieldPaciente = new JTextField();
		textFieldPaciente.setColumns(10);
		
		JLabel lblMedico = new JLabel("Id del M\u00E9dico:");
		
		textFieldMedico = new JTextField();
		textFieldMedico.setColumns(10);
		
		JLabel lblTratamientos = new JLabel("Id del Tratamiento:");
		
		JButton btnNewAnadir = new JButton("A\u00F1adir");
		btnNewAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				sendData();
			}
		});
		
		JButton btnNewButtonCancelar = new JButton("Cancelar");
		btnNewButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelAddPaciente();
				dispose();
			}
		});
		
		listTratamientos = new JList();
		
		textFieldTratamiento = new JTextField();
		textFieldTratamiento.setColumns(10);
		
		JLabel lblNewLabelObservaciones = new JLabel("Observaciones:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButtonAñadirTratamiento = new JButton("+");
		btnNewButtonAñadirTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(textFieldTratamiento.getText() != "") {
					
					((DefaultListModel<String>)listTratamientos.getModel()).addElement(textFieldTratamiento.getText());
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewAnadirExpediente, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButtonCancelar)
							.addPreferredGap(ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
							.addComponent(btnNewAnadir))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMedico, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
								.addComponent(lblPaciente, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
								.addComponent(textFieldPaciente, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
								.addComponent(lblTratamientos, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
								.addComponent(textFieldMedico, 325, 325, Short.MAX_VALUE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
								.addComponent(lblNewLabelObservaciones, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(textFieldTratamiento, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButtonAñadirTratamiento)))
							.addGap(18)
							.addComponent(listTratamientos, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewAnadirExpediente)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPaciente)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldPaciente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblMedico)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldMedico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblTratamientos)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldTratamiento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButtonAñadirTratamiento))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabelObservaciones)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
						.addComponent(listTratamientos, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButtonCancelar)
						.addComponent(btnNewAnadir))
					.addContainerGap())
		);
		
		textAreaObservaciones = new JTextArea();
		scrollPane.setViewportView(textAreaObservaciones);
		getContentPane().setLayout(groupLayout);

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
		
		DefaultListModel<String> model = (DefaultListModel<String>) listTratamientos.getModel();
		ClientDataChannel cdc;
		String tratamientos = null;
		
		if(checkData()) {
				
			try {
				this.pw.println("ADDEXPEDIENTE");
				this.pw.flush();
				cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOos(new ObjectOutputStream(cdc.getSocket().getOutputStream()));
					cdc.getOos().writeObject(textFieldPaciente.getText());
					cdc.getOos().flush();
					cdc.getOos().writeObject(textFieldMedico.getText());
					cdc.getOos().flush();
					for (int i = 0; i < model.getSize(); ++i) 				
						tratamientos += model.get(i);
					cdc.getOos().writeObject(tratamientos);
					cdc.getOos().flush();
					cdc.getOos().writeObject(textAreaObservaciones.getText());
					cdc.getOos().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
			catch(Exception t) {
			
			}
			
			textFieldPaciente.setText(null);
			textFieldMedico.setText(null);
		}
		
		return;
	}
	
	private void cancelAddPaciente() {
		try {
			this.pw.println("EXIT ADD PACIENTE");
			pw.flush();
		}
		catch(Exception t) {
		
		}
	}
	
	private Boolean checkData() {
		
		if(textFieldPaciente.getText().equals("")) {
			JOptionPane.showMessageDialog(null,"You must complete user name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(textFieldMedico.getText().equals("")) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(textAreaObservaciones.getText().equals("")) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(listTratamientos.getModel().getSize() > 0) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
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

	public ClientThreadCommands getClientThreadCommands() {
		return clientThreadCommands;
	}

	public void setClientThreadCommands(ClientThreadCommands clientThreadCommands) {
		this.clientThreadCommands = clientThreadCommands;
	}
}
