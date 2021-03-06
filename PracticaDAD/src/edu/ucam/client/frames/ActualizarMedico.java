package edu.ucam.client.frames;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

import edu.ucam.client.ClientDataChannel;
import edu.ucam.client.ClientThreadCommands;
import edu.ucam.pojos.Medico;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class ActualizarMedico extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8689482413161798506L;
	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	private ClientDataChannel cdc;
	private ClientThreadCommands clientThreadCommands;
	private Integer port;
	
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JComboBox<String> comboBoxEspecialidad;
	
	private Medico medicoActualizar;
	/**
	 * Create the frame.
	 */
	public ActualizarMedico(Medico medico, ClientDataChannel cdc, ClientThreadCommands clientThreadCommands) {
		this.cdc = cdc;
		this.setMedicoActualizar(medico);
		this.setClientThreadCommands(clientThreadCommands);
		setBounds(100, 100, 483, 295);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel lblActlzrMdico = new JLabel("Actualizar M\u00E9dico");
		lblActlzrMdico.setHorizontalAlignment(SwingConstants.CENTER);
		lblActlzrMdico.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblNewLabelNombre = new JLabel("Nombre:");
		lblNewLabelNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabelApellidos = new JLabel("Apellidos:\r\n");
		lblNewLabelApellidos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		
		JLabel lblNewLabelEspecialidad = new JLabel("Especialidad:\r\n");
		lblNewLabelEspecialidad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		comboBoxEspecialidad = new JComboBox<String>();
		comboBoxEspecialidad.setModel(new DefaultComboBoxModel<String>(new String[] {"Psiquiatr\u00EDa", "Aparato digestivo", "Cardiolog\u00EDa"}));
		
		JButton btnNewButtonAnadir = new JButton("Actualizar");
		btnNewButtonAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendData();
				dispose();
			}
		});
		
		JButton btnNewButtonCancelar = new JButton("Cancelar");
		btnNewButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendNull();
				dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActlzrMdico, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(lblNewLabelNombre)
						.addComponent(lblNewLabelApellidos)
						.addComponent(lblNewLabelEspecialidad)
						.addComponent(textFieldNombre, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(textFieldApellidos, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(comboBoxEspecialidad, 0, 447, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButtonCancelar)
							.addPreferredGap(ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
							.addComponent(btnNewButtonAnadir)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblActlzrMdico)
					.addGap(18)
					.addComponent(lblNewLabelNombre)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabelApellidos)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldApellidos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabelEspecialidad)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxEspecialidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButtonCancelar)
						.addComponent(btnNewButtonAnadir))
					.addGap(7))
		);
		getContentPane().setLayout(groupLayout);

		textFieldNombre.setText(medicoActualizar.getNombre());
		textFieldApellidos.setText(medicoActualizar.getApellidos());
		comboBoxEspecialidad.setSelectedItem(medicoActualizar.getEspecialidad());
		
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
			
			medicoActualizar.setNombre(textFieldNombre.getText());
			medicoActualizar.setApellidos(textFieldApellidos.getText());
			medicoActualizar.setEspecialidad(comboBoxEspecialidad.getSelectedItem().toString());
			
		
			try {
				cdc.getOos().writeObject(medicoActualizar);
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
		if(textFieldNombre.getText() == null) {
			JOptionPane.showMessageDialog(null,"You must complete user name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(textFieldApellidos.getText() == null) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(comboBoxEspecialidad.getSelectedItem().toString() == null) {
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

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Medico getMedicoActualizar() {
		return medicoActualizar;
	}

	public void setMedicoActualizar(Medico medicoActualizar) {
		this.medicoActualizar = medicoActualizar;
	}

	public ClientThreadCommands getClientThreadCommands() {
		return clientThreadCommands;
	}

	public void setClientThreadCommands(ClientThreadCommands clientThreadCommands) {
		this.clientThreadCommands = clientThreadCommands;
	}

}
