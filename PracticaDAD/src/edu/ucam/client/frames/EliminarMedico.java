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
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class EliminarMedico extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8689482413161798506L;
	
	private JComponent Barra = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane();
	private Dimension dimensionBarra = null;
	private PrintWriter pw;
	
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
				
				sendData();
				System.out.println("sended");
			}
		});
		
		JButton btnNewButtonCancelar = new JButton("Cancelar");
		btnNewButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelRemoveMedico();
				dispose();
			}
		});
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
	
	private void sendData() {
		
		if(checkData()) {
			try {
				this.pw.println("REMOVEMEDICO " + this.textFieldID.getText());
				pw.flush();
			}
			catch(Exception t) {
			
			}
			
			this.textFieldID.setText(null);
		}
		
		return;
	}
	
	private void cancelRemoveMedico() {
		try {
			this.pw.println("EXIT REMOVE MEDICO");
			pw.flush();
		}
		catch(Exception t) {
		
		}
	}
	
	private Boolean checkData() {
		if(textFieldID.getText() == null) {
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
}
