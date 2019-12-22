package edu.ucam.client.frames;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class ElininarMedico extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElininarMedico frame = new ElininarMedico();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ElininarMedico() {
		setBounds(100, 100, 450, 300);

	}

}
