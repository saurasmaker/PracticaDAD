package edu.ucam.client.frames;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.ucam.client.ClientDataChannel;
import edu.ucam.client.ClientThreadCommands;
import edu.ucam.pojos.Paciente;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class ActualizarPaciente extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JSpinner spinnerDia;
	private JSpinner spinnerMes;
	private JSpinner spinnerAnio;

	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	private ClientThreadCommands clientThreadCommands;
	private ClientDataChannel cdc;
	
	
	private Paciente pacienteActualizar;
	/**
	 * Create the frame.
	 */
	public ActualizarPaciente(Paciente paciente, ClientDataChannel cdc, ClientThreadCommands clientThreadCommands) {
		this.cdc = cdc;
		this.pacienteActualizar = paciente;
		this.setClientThreadCommands(clientThreadCommands);
		setBounds(100, 100, 483, 291);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel lblNewActlzrPaciente = new JLabel("Actualizar Paciente");
		lblNewActlzrPaciente.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewActlzrPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNombre = new JLabel("Nombre:");
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		
		JButton btnNewAnadir = new JButton("Actualizar");
		btnNewAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				sendData();
				dispose();
			}
		});
		
		JButton btnNewButtonCancelar = new JButton("Cancelar");
		btnNewButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendNull();
				dispose();
			}
		});
		
		spinnerDia = new JSpinner();
		
		spinnerMes = new JSpinner();
		
		spinnerAnio = new JSpinner();
		
		JLabel lblDia = new JLabel("D\u00EDa:");
		
		JLabel lblMes = new JLabel("Mes:");
		
		JLabel lblAnio = new JLabel("A\u00F1o");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFechaNacimiento, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addComponent(lblNewActlzrPaciente, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addComponent(textFieldApellidos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addComponent(textFieldNombre, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addComponent(lblNombre, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addComponent(lblApellidos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButtonCancelar)
							.addPreferredGap(ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
							.addComponent(btnNewAnadir))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDia)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerDia, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblMes)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerMes, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(lblAnio)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerAnio, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewActlzrPaciente)
					.addGap(18)
					.addComponent(lblNombre)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblApellidos)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldApellidos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblFechaNacimiento)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDia)
						.addComponent(spinnerDia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinnerMes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMes)
						.addComponent(spinnerAnio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAnio))
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButtonCancelar)
						.addComponent(btnNewAnadir))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		String[] fecha = new SimpleDateFormat("dd/MM/yyyy").format(paciente.getFechaNacimiento()).toString().split("/");
		
		textFieldNombre.setText(pacienteActualizar.getNombre());
		textFieldApellidos.setText(pacienteActualizar.getApellidos());
		spinnerDia.setValue(Integer.parseInt(fecha[0]));
		spinnerMes.setValue(Integer.parseInt(fecha[1]));
		spinnerAnio.setValue(Integer.parseInt(fecha[2]));
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
		
		Date date = null;

		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(Integer.parseInt(spinnerDia.getValue().toString()) + "/" + Integer.parseInt(spinnerMes.getValue().toString()) + "/" + Integer.parseInt(spinnerAnio.getValue().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(checkData()) {
			
			pacienteActualizar.setNombre(textFieldNombre.getText());
			pacienteActualizar.setApellidos(textFieldApellidos.getText());
			pacienteActualizar.setFechaNacimiento(date);	
			
			try {

				cdc.getOos().writeObject(pacienteActualizar);
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
		
		if(textFieldNombre.getText().equals("")) {
			JOptionPane.showMessageDialog(null,"You must complete user name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(textFieldApellidos.getText().equals("")) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if((Integer.parseInt(spinnerDia.getValue().toString()) <= 0 || Integer.parseInt(spinnerDia.getValue().toString()) > 31 ) || (Integer.parseInt(spinnerMes.getValue().toString()) <= 0 || Integer.parseInt(spinnerMes.getValue().toString()) > 12) || (Integer.parseInt(spinnerAnio.getValue().toString()) <= 0)) {
			JOptionPane.showMessageDialog(null,"You must complete date field.","Field error", JOptionPane.WARNING_MESSAGE);
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
	

	public ClientThreadCommands getClientThreadCommands() {
		return clientThreadCommands;
	}

	public void setClientThreadCommands(ClientThreadCommands clientThreadCommands) {
		this.clientThreadCommands = clientThreadCommands;
	}
}
