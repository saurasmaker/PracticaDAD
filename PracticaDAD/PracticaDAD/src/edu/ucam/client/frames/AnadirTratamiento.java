package edu.ucam.client.frames;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.ucam.pojos.Medico;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;

public class AnadirTratamiento extends JInternalFrame {

	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	
	/**
	 * Create the frame.
	 */
	public AnadirTratamiento() {
		setBounds(100, 100, 483, 343);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel labelTratamiento = new JLabel("A\u00F1adir Tratamiento");
		labelTratamiento.setHorizontalAlignment(SwingConstants.CENTER);
		labelTratamiento.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton buttonAnadir = new JButton("A\u00F1adir");
		
		JButton buttonCancelar = new JButton("Cancelar");
		
		JLabel labelDescripcion = new JLabel("Descripci\u00F3n:");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
						.addComponent(labelTratamiento, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonAnadir, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
							.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonAnadir)
						.addComponent(buttonCancelar))
					.addContainerGap())
		);
		
		JTextArea textAreaDescripcion = new JTextArea();
		scrollPane.setViewportView(textAreaDescripcion);
		getContentPane().setLayout(groupLayout);
		
		buttonAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*
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
				}*/
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
