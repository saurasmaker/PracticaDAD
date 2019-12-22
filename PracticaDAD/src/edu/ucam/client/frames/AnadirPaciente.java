package edu.ucam.client.frames;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.ucam.pojos.Medico;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;

public class AnadirPaciente extends JInternalFrame {
	
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldFechaNacimiento;

	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	
	
	/**
	 * Create the frame.
	 */
	public AnadirPaciente() {
		setBounds(100, 100, 483, 291);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel lblNewAnadirPaciente = new JLabel("A\u00F1adir Paciente");
		lblNewAnadirPaciente.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewAnadirPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNombre = new JLabel("Nombre:");
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setColumns(10);
		
		textFieldFechaNacimiento = new JTextField();
		textFieldFechaNacimiento.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		
		JButton btnNewAnadir = new JButton("A\u00F1adir");
		btnNewAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textFieldNombre.getText() == null || textFieldApellidos.getText() == null || textFieldFechaNacimiento.getText() == null)
				{
				    JOptionPane.showMessageDialog(null, "Los datos no son correctos",
					    "InfoBox: Error Datos Incompletos", JOptionPane.INFORMATION_MESSAGE);
				} else
				{
				    Medico medico = new Medico("MED"+0, textFieldNombre.getText(), textFieldApellidos.getText(), textFieldFechaNacimiento.getText());
				    //Hacer algoritmo que recoja id paciente
				    textFieldNombre.setText(null);
				    textFieldApellidos.setText(null);	
				    textFieldFechaNacimiento.setText(null);
				}
			}
		});
		
		JButton btnNewButtonCancelar = new JButton("Cancelar");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFechaNacimiento, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(lblNewAnadirPaciente, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(textFieldFechaNacimiento, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(textFieldApellidos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(textFieldNombre, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(lblNombre, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addComponent(lblApellidos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButtonCancelar)
							.addPreferredGap(ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
							.addComponent(btnNewAnadir)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewAnadirPaciente)
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
					.addComponent(textFieldFechaNacimiento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButtonCancelar)
						.addComponent(btnNewAnadir))
					.addContainerGap())
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
		
	private String generateID() {
			
			
			
			
		return null;
	}

		
	//Getters & Setters
	public Dimension getDimensionBarra() {
		return dimensionBarra;
	}

	public void setDimensionBarra(Dimension dimensionBarra) {
		this.dimensionBarra = dimensionBarra;
	}
}
