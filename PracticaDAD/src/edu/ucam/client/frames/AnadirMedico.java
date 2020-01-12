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
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class AnadirMedico extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8689482413161798506L;
	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	private PrintWriter pw;
	private ClientThreadCommands clientThreadCommands;
	private Integer port;
	
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JComboBox<String> comboBoxEspecialidad;

	/**
	 * Create the frame.
	 */
	public AnadirMedico(PrintWriter pw, ClientThreadCommands clientThreadCommands) {
		this.pw = pw;
		this.clientThreadCommands = clientThreadCommands;
		setBounds(100, 100, 483, 295);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel lblAadirMdico = new JLabel("A\u00F1adir M\u00E9dico");
		lblAadirMdico.setHorizontalAlignment(SwingConstants.CENTER);
		lblAadirMdico.setFont(new Font("Tahoma", Font.BOLD, 18));
		
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
		
		JButton btnNewButtonAnadir = new JButton("A\u00F1adir");
		btnNewButtonAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendData();
				System.out.println("sended");
			}
		});
		
		JButton btnNewButtonCancelar = new JButton("Cancelar");
		btnNewButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAadirMdico, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
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
					.addComponent(lblAadirMdico)
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
		
		Medico medico = new Medico();
		ClientDataChannel cdc = null;
		
		if(checkData()) {
			
			medico.setNombre(textFieldNombre.getText());
			medico.setApellidos(textFieldApellidos.getText());
			medico.setEspecialidad(comboBoxEspecialidad.getSelectedItem().toString());
			
			try {
				this.pw.println("ADDMEDICO");
				this.pw.flush();
				cdc = new ClientDataChannel(clientThreadCommands.getDataPort());

				try {
					cdc.setOos(new ObjectOutputStream(cdc.getSocket().getOutputStream()));
					cdc.getOos().writeObject(medico);
					cdc.getOos().flush();
				}
				catch(Exception t) {
					
				}
								
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);

			}
			catch(Exception t) {
				
			}
			textFieldNombre.setText(null);
			textFieldApellidos.setText(null);
			
			
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
}
