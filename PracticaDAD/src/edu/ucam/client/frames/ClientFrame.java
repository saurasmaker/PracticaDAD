package edu.ucam.client.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.ucam.client.ClientDataChannel;
import edu.ucam.client.ClientThreadCommands;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4437042091596383165L;

	//Atributes
	private JPanel contentPane;
	private PrintWriter pw;
	private ClientThreadCommands clientThreadCommands;
	
	private JEditorPane editorPaneData;
	
	private Integer dataPort;
	
	//Constructor
	public ClientFrame(PrintWriter pw, ClientThreadCommands clientThreadCommands, Integer dataPort) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				pw.println("EXIT");
				pw.flush();
				clientThreadCommands.setSuspended(true);
			}
		});
		this.dataPort = dataPort;
		this.setClientThreadCommands(clientThreadCommands);
		this.setPw(pw);
		
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 515);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.GRAY);
		
		JMenu mnPacientes = new JMenu("Pacientes");
		menuBar.add(mnPacientes);
		
		JMenuItem mntmAnadirPaciente = new JMenuItem("A\u00F1adir");
		mntmAnadirPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirPaciente anadirPaciente = new AnadirPaciente(pw, clientThreadCommands); 
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
		mntmActualizarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//String idPaciente = JOptionPane.showInputDialog("Introduce el id del paciente a actualizar: ");
			}
		});
		mnPacientes.add(mntmActualizarPaciente);
		
		JMenuItem mntmMostrarPaciente = new JMenuItem("Mostrar");
		mntmMostrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("hola");
				
				//Envio comando
				pw.println("GETPACIENTE");
				pw.flush();
				
				//Lectura Id
				String idPaciente = JOptionPane.showInputDialog("Introduce el id del paciente a actualizar: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idPaciente);
				cdc.getPw().flush();

				//Lectura objeto
				Paciente paciente = null;
				System.out.println();
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					paciente = (Paciente)cdc.getOis().readObject();
				}catch(Exception t) {
					
				}	
				
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
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
		mntmAnadirMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirMedico anadirMedico = new AnadirMedico(pw, dataPort); 
				desktopPane.add(anadirMedico);
				try {
					anadirMedico.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				anadirMedico.show();
			}
		});
		mnMedicos.add(mntmAnadirMedico);
		
		JMenuItem menuActualizarMedico = new JMenuItem("Actualizar");
		mnMedicos.add(menuActualizarMedico);
		
		JMenuItem mntmMostrarMedico = new JMenuItem("Mostrar");
		mntmMostrarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClientDataChannel cdc = new ClientDataChannel(dataPort);
				cdc.getPw().println(JOptionPane.showInputDialog("Introduce el ID del medico a mostrar:"));
				try {
					mostrarMedico((Medico)cdc.getOis().readObject());
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});
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
		mntmAnadirTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirTratamiento anadirTratamiento = new AnadirTratamiento(dataPort); 
				desktopPane.add(anadirTratamiento);
				try {
					anadirTratamiento.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				anadirTratamiento.show();
			}
		});
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
		
		JPanel panelData = new JPanel();
		tabbedPane.addTab("Data", null, panelData, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_panelData = new GroupLayout(panelData);
		gl_panelData.setHorizontalGroup(
			gl_panelData.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
		);
		gl_panelData.setVerticalGroup(
			gl_panelData.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
		);
		
		editorPaneData = new JEditorPane();
		scrollPane_1.setViewportView(editorPaneData);
		panelData.setLayout(gl_panelData);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Log", null, panel, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
		);
		
		JEditorPane editorPaneLog = new JEditorPane();
		scrollPane.setViewportView(editorPaneLog);
		panel.setLayout(gl_panel);
		
		
		splitPane.setLeftComponent(desktopPane);
		splitPane.setDividerLocation(300);
		contentPane.setLayout(gl_contentPane);
	}

	
	//Methods
	void mostrarPaciente(Paciente paciente) {
	
		if(paciente != null)
			editorPaneData.setText(" >Paciente: " + paciente.getId() + "\n\t-Nombre: "+paciente.getNombre()
			+ "\n\t-Apellidos: "+paciente.getApellidos()
			+ "\n\t-Fecha nacimiento: "+paciente.getFechaNacimiento());
		
		else
			editorPaneData.setText(" >Paciente no encontrado.");
		
		return;
	}
	
	void mostrarMedico(Medico medico) {
		
		if(medico != null)
			editorPaneData.setText(" >Medico: " + medico.getId() + "\n\t-Nombre: "+medico.getNombre()
			+ "\n\t-Apellidos: "+medico.getApellidos()
			+ "\n\t-Especialidad: "+medico.getEspecialidad());
		
		else
			editorPaneData.setText(" >Médico no encontrado.");
		
		return;
	}
	
	void mostrarTratamiento(Tratamiento tratamiento) {
		
		if(tratamiento != null)
			editorPaneData.setText(" >Medico: " + tratamiento.getId() + "\n\t-Nombre: "+tratamiento.getDescripcion());
		
		else
			editorPaneData.setText(" >Tratamiento no encontrado.");
		
		return;
	}
	
	//Getters & Setters
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


	public Integer getDataPort() {
		return dataPort;
	}


	public void setDataPort(Integer dataPort) {
		this.dataPort = dataPort;
	}
}
