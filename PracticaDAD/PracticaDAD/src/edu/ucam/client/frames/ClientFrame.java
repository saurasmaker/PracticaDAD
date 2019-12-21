package edu.ucam.client.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.io.PrintWriter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4437042091596383165L;

	//Atributes
	private JPanel contentPane;
	private PrintWriter pw;
	
	
	//Constructor
	public ClientFrame(PrintWriter pw) {
		this.setPw(pw);
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 515);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.GRAY);
		
		JMenu mnAutenticarse = new JMenu("Autenticarse");
		menuBar.add(mnAutenticarse);
		
		JMenu mnPacientes = new JMenu("Pacientes");
		menuBar.add(mnPacientes);
		
		JMenuItem mntmAnadirPaciente = new JMenuItem("A\u00F1adir");
		mntmAnadirPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirPaciente anadirPaciente = new AnadirPaciente(); 
				desktopPane.add(anadirPaciente);
				try {
					anadirPaciente.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				anadirPaciente.show();
			}
		});
		mnPacientes.add(mntmAnadirPaciente);
		
		JMenuItem mntmActualizarPaciente = new JMenuItem("Actualizar");
		mnPacientes.add(mntmActualizarPaciente);
		
		JMenuItem mntmMostrarPaciente = new JMenuItem("Mostrar");
		mnPacientes.add(mntmMostrarPaciente);
		
		JMenuItem mntmEliminarPaciente = new JMenuItem("Eliminar");
		mnPacientes.add(mntmEliminarPaciente);
		
		JMenuItem mntmListarPacientes = new JMenuItem("Listar");
		mnPacientes.add(mntmListarPacientes);
		
		JMenuItem mntmContarPacientes = new JMenuItem("Contar");
		mnPacientes.add(mntmContarPacientes);
		
		JMenu mnMedicos = new JMenu("M\u00E9dicos");
		menuBar.add(mnMedicos);
		
		JMenuItem mntmAnadirMedico = new JMenuItem("A\u00F1adir");
		mnMedicos.add(mntmAnadirMedico);
		
		JMenuItem menuActualizarMedico = new JMenuItem("Actualizar");
		mnMedicos.add(menuActualizarMedico);
		
		JMenuItem mntmMostrarMedico = new JMenuItem("Mostrar");
		mnMedicos.add(mntmMostrarMedico);
		
		JMenuItem mntmEliminarMedico = new JMenuItem("Eliminar");
		mnMedicos.add(mntmEliminarMedico);
		
		JMenuItem mntmListarMedicos = new JMenuItem("Listar");
		mnMedicos.add(mntmListarMedicos);
		
		JMenuItem mntmContarMedicos = new JMenuItem("Contar");
		mnMedicos.add(mntmContarMedicos);
		
		JMenu mnTratamientos = new JMenu("Tratamientos");
		menuBar.add(mnTratamientos);
		
		JMenuItem mntmAnadirTratamiento = new JMenuItem("A\u00F1adir");
		mnTratamientos.add(mntmAnadirTratamiento);
		
		JMenuItem mntmActualizarTratamiento = new JMenuItem("Actualizar");
		mnTratamientos.add(mntmActualizarTratamiento);
		
		JMenuItem mntmMostrarTratamiento = new JMenuItem("Mostrar");
		mnTratamientos.add(mntmMostrarTratamiento);
		
		JMenuItem mntmEliminarTratamiento = new JMenuItem("Eliminar");
		mnTratamientos.add(mntmEliminarTratamiento);
		
		JMenuItem mntmListarTratamientos = new JMenuItem("Listar");
		mnTratamientos.add(mntmListarTratamientos);
		
		JMenuItem mntmContarTratamientos = new JMenuItem("Contar");
		mnTratamientos.add(mntmContarTratamientos);
		
		JMenu mnExpedientes = new JMenu("Expedientes");
		menuBar.add(mnExpedientes);
		
		JMenuItem mntmAnadirExpediente = new JMenuItem("A\u00F1adir");
		mnExpedientes.add(mntmAnadirExpediente);
		
		JMenuItem mntmMostrarExpediente = new JMenuItem("Mostrar");
		mnExpedientes.add(mntmMostrarExpediente);
		
		JMenuItem mntmEliminarExpediente = new JMenuItem("Eliminar");
		mnExpedientes.add(mntmEliminarExpediente);
		
		JMenuItem mntmListarExpedientes = new JMenuItem("Listar");
		mnExpedientes.add(mntmListarExpedientes);
		
		JMenu mnExpedientesPacientes = new JMenu("Pacientes");
		mnExpedientes.add(mnExpedientesPacientes);
		
		JMenuItem mntmAnadirPacienteExpediente = new JMenuItem("A\u00F1adir");
		mnExpedientesPacientes.add(mntmAnadirPacienteExpediente);
		
		JMenuItem mntmEliminarPacienteExpediente = new JMenuItem("Eliminar");
		mnExpedientesPacientes.add(mntmEliminarPacienteExpediente);
		
		JMenu mnExpedientesMedicos = new JMenu("M\u00E9dicos");
		mnExpedientes.add(mnExpedientesMedicos);
		
		JMenuItem mntmAnadirMedicoExpediente = new JMenuItem("A\u00F1adir");
		mnExpedientesMedicos.add(mntmAnadirMedicoExpediente);
		
		JMenuItem mntmEliminarMedicoExpediente = new JMenuItem("Eliminar");
		mnExpedientesMedicos.add(mntmEliminarMedicoExpediente);
		
		JMenu mnExpedientesTratamientos = new JMenu("Tratamientos");
		mnExpedientes.add(mnExpedientesTratamientos);
		
		JMenuItem mntmAnadirTratamientoExpediente = new JMenuItem("A\u00F1adir");
		mnExpedientesTratamientos.add(mntmAnadirTratamientoExpediente);
		
		JMenuItem mntmEliminarTratamientoExpediente = new JMenuItem("Eliminar");
		mnExpedientesTratamientos.add(mntmEliminarTratamientoExpediente);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(3);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
		);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		
		
		splitPane.setLeftComponent(desktopPane);
		splitPane.setDividerLocation(300);
		contentPane.setLayout(gl_contentPane);
	}

	
	//Getters & Setters
	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
}
