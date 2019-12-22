package edu.ucam.client.frames;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

import edu.ucam.pojos.Medico;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;

public class EliminarMedico extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8689482413161798506L;
	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	
	private JTextField textFieldID;

	/**
	 * Create the frame.
	 */
	public EliminarMedico() {
		setBounds(100, 100, 483, 295);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		quitarLaBarraTitulo();
		
		JLabel lblEliminarMdico = new JLabel("Eliminar M\u00E9dico");
		lblEliminarMdico.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminarMdico.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblNewLabelID = new JLabel("ID:");
		lblNewLabelID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		
		JButton btnNewButtonEliminar = new JButton("Eliminar");
		btnNewButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textFieldID.getText() == null)
					{
					    JOptionPane.showMessageDialog(null, "Los datos no son correctos",
						    "InfoBox: Error Datos Incompletos", JOptionPane.INFORMATION_MESSAGE);
					} else

					{
					    //Hacer algoritmo que elimina medico

					{

					    textFieldID.setText(null);					    
					}
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
						.addComponent(lblEliminarMdico, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
						.addComponent(lblNewLabelID)
						.addComponent(textFieldID, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButtonCancelar)
							.addPreferredGap(ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
							.addComponent(btnNewButtonEliminar)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEliminarMdico)
					.addGap(18)
					.addComponent(lblNewLabelID)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButtonCancelar)
						.addComponent(btnNewButtonEliminar))
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
	
	//Getters & Setters
	public Dimension getDimensionBarra() {
		return dimensionBarra;
	}

	public void setDimensionBarra(Dimension dimensionBarra) {
		this.dimensionBarra = dimensionBarra;
	}

}
