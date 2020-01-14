package edu.ucam.client.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.ucam.client.ClientDataChannel;
import edu.ucam.client.ClientThreadCommands;
import edu.ucam.pojos.Expediente;
import edu.ucam.pojos.Medico;
import edu.ucam.pojos.Paciente;
import edu.ucam.pojos.Tratamiento;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

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
	private JEditorPane editorPaneLog;

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
		setBounds(100, 100, 503, 665);
		
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
				//Envio comando
				pw.println("UPDATEPACIENTE");
				pw.flush();
				
				//Lectura Id
				String idPaciente = JOptionPane.showInputDialog("Introduce el id del paciente a ACTUALIZAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOos(new ObjectOutputStream(cdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					cdc.getOos().writeObject(idPaciente);
					cdc.getOos().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				//Lectura objeto
				Paciente paciente = null;
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					paciente = (Paciente)cdc.getOis().readObject();
					System.out.println(paciente.getId());
				}catch(Exception t) {
					
				}	
				
				ActualizarPaciente ap = new ActualizarPaciente(paciente, cdc, clientThreadCommands);
				desktopPane.add(ap);
				try {
					ap.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				ap.show();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnPacientes.add(mntmActualizarPaciente);
		
		JMenuItem mntmMostrarPaciente = new JMenuItem("Mostrar");
		mntmMostrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("GETPACIENTE");
				pw.flush();
				
				//Lectura Id
				String idPaciente = JOptionPane.showInputDialog("Introduce el id del paciente a mostrar: ");
				
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
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					paciente = (Paciente)cdc.getOis().readObject();
					editorPaneData.setText("");
					mostrarPaciente(paciente);
				}catch(Exception t) {
					
				}	
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnPacientes.add(mntmMostrarPaciente);
		
		
		JMenuItem mntmEliminarPaciente = new JMenuItem("Eliminar");
		mntmEliminarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("REMOVEPACIENTE");
				pw.flush();
				
				//Lectura Id
				String idPaciente = JOptionPane.showInputDialog("Introduce el id del paciente a ELIMINAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idPaciente);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnPacientes.add(mntmEliminarPaciente);
		
		
		JMenuItem mntmListarPacientes = new JMenuItem("Listar");
		mntmListarPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("LISTPACIENTES");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					ObjectInputStream ois = cdc.getOis();
					Integer tam = 0;
					try {
						
						tam = (Integer)ois.readObject();
						editorPaneData.setText("");
						for(int i = 0;i < tam;++i)
							mostrarPaciente((Paciente)ois.readObject());
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnPacientes.add(mntmListarPacientes);
		
		JMenuItem mntmContarPacientes = new JMenuItem("Contar");
		mntmContarPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("COUNTPACIENTES");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					try {
						editorPaneData.setText("");
						editorPaneData.setText((String)cdc.getOis().readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnPacientes.add(mntmContarPacientes);
		
		JMenu mnMedicos = new JMenu("M\u00E9dicos");
		menuBar.add(mnMedicos);
		
		JMenuItem mntmAnadirMedico = new JMenuItem("A\u00F1adir");
		mntmAnadirMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirMedico anadirMedico = new AnadirMedico(pw, clientThreadCommands); 
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
		menuActualizarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("UPDATEMEDICO");
				pw.flush();
				
				//Lectura Id
				String idMedico = JOptionPane.showInputDialog("Introduce el id del médico a ACTUALIZAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOos(new ObjectOutputStream(cdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					cdc.getOos().writeObject(idMedico);
					cdc.getOos().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				//Lectura objeto
				Medico medico = null;
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					medico = (Medico)cdc.getOis().readObject();
					System.out.println(medico.getId());
				}catch(Exception t) {
					
				}	
				
				ActualizarMedico am = new ActualizarMedico(medico, cdc, clientThreadCommands);
				desktopPane.add(am);
				try {
					am.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				am.show();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnMedicos.add(menuActualizarMedico);
		
		JMenuItem mntmMostrarMedico = new JMenuItem("Mostrar");
		mntmMostrarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("GETMEDICO");
				pw.flush();
				
				//Lectura Id
				String idMedico = JOptionPane.showInputDialog("Introduce el id del médico a mostrar: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idMedico);
				cdc.getPw().flush();

				//Lectura objeto
				Medico medico = null;
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					medico = (Medico)cdc.getOis().readObject();
					editorPaneData.setText("");
					mostrarMedico(medico);
				}catch(Exception t) {
					
				}	
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnMedicos.add(mntmMostrarMedico);
		
		JMenuItem mntmEliminarMedico = new JMenuItem("Eliminar");
		mntmEliminarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("REMOVEMEDICO");
				pw.flush();
				
				//Lectura Id
				String idMedico = JOptionPane.showInputDialog("Introduce el id del médico a ELIMINAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idMedico);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnMedicos.add(mntmEliminarMedico);
		
		JMenuItem mntmListarMedicos = new JMenuItem("Listar");
		mntmListarMedicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("LISTMEDICOS");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					ObjectInputStream ois = cdc.getOis();
					Integer tam = 0;
					try {
						
						tam = (Integer)ois.readObject();
						editorPaneData.setText("");
						for(int i = 0;i < tam;++i)
							mostrarMedico((Medico)ois.readObject());
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnMedicos.add(mntmListarMedicos);
		
		JMenuItem mntmContarMedicos = new JMenuItem("Contar");
		mntmContarMedicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("COUNTMEDICOS");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					try {
						editorPaneData.setText("");
						editorPaneData.setText((String)cdc.getOis().readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnMedicos.add(mntmContarMedicos);
		
		JMenu mnTratamientos = new JMenu("Tratamientos");
		menuBar.add(mnTratamientos);
		
		JMenuItem mntmAnadirTratamiento = new JMenuItem("A\u00F1adir");
		mntmAnadirTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirTratamiento anadirTratamiento = new AnadirTratamiento(pw, clientThreadCommands); 
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
		mntmActualizarTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("UPDATETRATAMIENTO");
				pw.flush();
				
				//Lectura Id
				String idTratamiento = JOptionPane.showInputDialog("Introduce el id del tratamiento a ACTUALIZAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOos(new ObjectOutputStream(cdc.getSocket().getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					cdc.getOos().writeObject(idTratamiento);
					cdc.getOos().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				//Lectura objeto
				Tratamiento tratamiento = null;
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					tratamiento = (Tratamiento)cdc.getOis().readObject();
					System.out.println(tratamiento.getId());
				}catch(Exception t) {
					
				}	
				
				ActualizarTratamiento at = new ActualizarTratamiento(tratamiento, cdc, clientThreadCommands);
				desktopPane.add(at);
				try {
					at.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				at.show();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnTratamientos.add(mntmActualizarTratamiento);
		
		JMenuItem mntmMostrarTratamiento = new JMenuItem("Mostrar");
		mntmMostrarTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("GETTRATAMIENTO");
				pw.flush();
				
				//Lectura Id
				String idTratamiento = JOptionPane.showInputDialog("Introduce el id del tratamiento a mostrar: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idTratamiento);
				cdc.getPw().flush();

				//Lectura objeto
				Tratamiento tratamiento = null;
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					tratamiento = (Tratamiento)cdc.getOis().readObject();
					editorPaneData.setText("");
					mostrarTratamiento(tratamiento);
				}catch(Exception t) {
					
				}	
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnTratamientos.add(mntmMostrarTratamiento);
		
		JMenuItem mntmEliminarTratamiento = new JMenuItem("Eliminar");
		mntmEliminarTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("REMOVETRATAMIENTO");
				pw.flush();
				
				//Lectura Id
				String idTratamiento = JOptionPane.showInputDialog("Introduce el id del tratamiento a ELIMINAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idTratamiento);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnTratamientos.add(mntmEliminarTratamiento);
		
		JMenuItem mntmListarTratamientos = new JMenuItem("Listar");
		mntmListarTratamientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("LISTTRATAMIENTOS");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					ObjectInputStream ois = cdc.getOis();
					Integer tam = 0;
					try {
						
						tam = (Integer)ois.readObject();
						editorPaneData.setText("");
						for(int i = 0;i < tam;++i)
							mostrarTratamiento((Tratamiento)ois.readObject());
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnTratamientos.add(mntmListarTratamientos);
		
		JMenuItem mntmContarTratamientos = new JMenuItem("Contar");
		mntmContarTratamientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("COUNTTRATAMIENTOS");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					try {
						editorPaneData.setText("");
						editorPaneData.setText((String)cdc.getOis().readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnTratamientos.add(mntmContarTratamientos);
		
		JMenu mnExpedientes = new JMenu("Expedientes");
		menuBar.add(mnExpedientes);
		
		JMenuItem mntmAnadirExpediente = new JMenuItem("A\u00F1adir");
		mntmAnadirExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnadirExpediente anadirExpediente = new AnadirExpediente(pw, clientThreadCommands); 
				desktopPane.add(anadirExpediente);
				try {
					anadirExpediente.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				anadirExpediente.show();
			}
		});
		mnExpedientes.add(mntmAnadirExpediente);
		
		
		JMenuItem mntmMostrarExpediente = new JMenuItem("Mostrar");
		mntmMostrarExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("GETEXPEDIENTE");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del expediente a mostrar: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();

				//Lectura objeto
				Expediente expediente = null;
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					expediente = (Expediente)cdc.getOis().readObject();
					editorPaneData.setText("");
					mostrarExpediente(expediente);
				}catch(Exception t) {
					
				}	
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientes.add(mntmMostrarExpediente);
		
		JMenuItem mntmEliminarExpediente = new JMenuItem("Eliminar");
		mntmEliminarExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("REMOVEEXPEDIENTE");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del expediente a ELIMINAR: ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientes.add(mntmEliminarExpediente);
		
		JMenuItem mntmListarExpedientes = new JMenuItem("Listar");
		mntmListarExpedientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//Envio comando
				pw.println("LISTEXPEDIENTES");
				pw.flush();
				
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setOis(new ObjectInputStream(cdc.getSocket().getInputStream()));
					ObjectInputStream ois = cdc.getOis();
					Integer tam = 0;
					try {
						
						tam = (Integer)ois.readObject();
						editorPaneData.setText("");
						for(int i = 0;i < tam;++i)
							mostrarExpediente((Expediente)ois.readObject());
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientes.add(mntmListarExpedientes);
		
		JMenu mnExpedientesPacientes = new JMenu("Pacientes");
		mnExpedientes.add(mnExpedientesPacientes);
		
		JMenuItem mntmAnadirPacienteExpediente = new JMenuItem("A\u00F1adir");
		mntmAnadirPacienteExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("ADDPACIENTE2EXP");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del Expediente a AÑADIR el paciente: ");
				String idPaciente = JOptionPane.showInputDialog("Introduce el id del Paciente a AÑADIR al expediente " + idExpediente +  ": ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				cdc.getPw().println(idPaciente);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientesPacientes.add(mntmAnadirPacienteExpediente);
		
		JMenuItem mntmEliminarPacienteExpediente = new JMenuItem("Eliminar");
		mntmEliminarPacienteExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("REMOVEPACIENTEFROMEXP");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del Expediente a ELIMINAR el paciente: ");
				String idPaciente = JOptionPane.showInputDialog("Introduce el id del Paciente a ELIMINAR del expediente " + idExpediente +  ": ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				cdc.getPw().println(idPaciente);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientesPacientes.add(mntmEliminarPacienteExpediente);
		
		JMenu mnExpedientesMedicos = new JMenu("M\u00E9dicos");
		mnExpedientes.add(mnExpedientesMedicos);
		
		JMenuItem mntmAnadirMedicoExpediente = new JMenuItem("A\u00F1adir");
		mntmAnadirMedicoExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("ADDMEDICO2EXP");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del Expediente a AÑADIR el paciente: ");
				String idMedico = JOptionPane.showInputDialog("Introduce el id del Medico a AÑADIR al expediente " + idExpediente +  ": ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				cdc.getPw().println(idMedico);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientesMedicos.add(mntmAnadirMedicoExpediente);
		
		JMenuItem mntmEliminarMedicoExpediente = new JMenuItem("Eliminar");
		mntmEliminarMedicoExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("REMOVEMEDICOFROMEXP");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del Expediente a ELIMINAR el medico: ");
				String idMedico = JOptionPane.showInputDialog("Introduce el id del Medico a ELIMINAR del expediente " + idExpediente +  ": ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				cdc.getPw().println(idMedico);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientesMedicos.add(mntmEliminarMedicoExpediente);
		
		JMenu mnExpedientesTratamientos = new JMenu("Tratamientos");
		mnExpedientes.add(mnExpedientesTratamientos);
		
		JMenuItem mntmAnadirTratamientoExpediente = new JMenuItem("A\u00F1adir");
		mntmAnadirTratamientoExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("ADDTRATAM2EXP");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del Expediente a AÑADIR el Tratamiento: ");
				String idTratamiento = JOptionPane.showInputDialog("Introduce el id del Tratmiento a AÑADIR al Expediente " + idExpediente +  ": ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				cdc.getPw().println(idTratamiento);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
		mnExpedientesTratamientos.add(mntmAnadirTratamientoExpediente);
		
		JMenuItem mntmEliminarTratamientoExpediente = new JMenuItem("Eliminar");
		mntmEliminarTratamientoExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Envio comando
				pw.println("REMOVETRATAMFROMEXP");
				pw.flush();
				
				//Lectura Id
				String idExpediente = JOptionPane.showInputDialog("Introduce el id del Expediente a ELIMINAR el tratamiento: ");
				String idTratamiento = JOptionPane.showInputDialog("Introduce el id del Tratamiento a ELIMINAR del expediente " + idExpediente +  ": ");
				
				//Envio Id
				ClientDataChannel cdc = new ClientDataChannel(clientThreadCommands.getDataPort());
				try {
					cdc.setPw(new PrintWriter(new OutputStreamWriter(cdc.getSocket().getOutputStream())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				cdc.getPw().println(idExpediente);
				cdc.getPw().flush();
				cdc.getPw().println(idTratamiento);
				cdc.getPw().flush();
				
				clientThreadCommands.setDataPort(clientThreadCommands.getDataPort()+1);
			}
		});
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
		
		editorPaneLog = new JEditorPane();
		scrollPane.setViewportView(editorPaneLog);
		panel.setLayout(gl_panel);
		
		
		splitPane.setLeftComponent(desktopPane);
		splitPane.setDividerLocation(300);
		contentPane.setLayout(gl_contentPane);
	}

	
	//Methods
	void mostrarPaciente(Paciente paciente) {
	
		if(paciente != null)
			editorPaneData.setText(editorPaneData.getText() + " >Paciente: " + paciente.getId() + "\n\t-Nombre: "+paciente.getNombre()
			+ "\n\t-Apellidos: "+paciente.getApellidos()
			+ "\n\t-Fecha nacimiento: " + new SimpleDateFormat("dd/MM/yyyy").format(paciente.getFechaNacimiento()) + "\n\n");
		
		else
			editorPaneData.setText(" >Paciente no encontrado.");
		
		return;
	}
	
	void mostrarMedico(Medico medico) {
		
		if(medico != null)
			editorPaneData.setText(editorPaneData.getText() + " >Medico: " + medico.getId() + "\n\t-Nombre: "+medico.getNombre()
			+ "\n\t-Apellidos: "+medico.getApellidos()
			+ "\n\t-Especialidad: "+medico.getEspecialidad() + "\n\n");
		
		else
			editorPaneData.setText(" >Médico no encontrado.");
		
		return;
	}
	
	void mostrarTratamiento(Tratamiento tratamiento) {
		
		if(tratamiento != null)
			editorPaneData.setText(editorPaneData.getText() + " >Tratamiento: " + tratamiento.getId() 
			+ "\n\t-Descripción: "+tratamiento.getDescripcion() + "\n\n");
		
		else
			editorPaneData.setText(" >Tratamiento no encontrado.");
		
		return;
	}
	
	void mostrarExpediente(Expediente expediente) {
		
		String mensaje = "";		
		
		if(expediente != null) {
			
			try {
				mensaje += editorPaneData.getText() + " >Expediente: " + expediente.getId() + "\n";
			}
			catch(Exception t) {
				
			}
			try {
				mensaje += "\n\t-Paciente: " + expediente.getPaciente().getNombre() + " " + expediente.getPaciente().getApellidos() + "\n";
			}
			catch(Exception t) {
				mensaje += "\n\t-Paciente: NULL\n";
			}
			try {
				mensaje += "\n\t-Medico: "+ expediente.getMedico().getNombre() + "\n\t   Especialidad: " + expediente.getMedico().getEspecialidad() + "\n";
			}
			catch(Exception t) {
				mensaje += "\n\t-Medico: NULL\n";
			}

			mensaje += "\n\t-Tratamientos: ";
			
			for (int i = 0; i < expediente.getTramientos().size()-1; ++i) {
				try {
					mensaje += "\n\t\t-Tratamiento " + i + ":"  + expediente.getTramientos().get(i+1).getDescripcion();
				}
				catch(Exception t) {
					mensaje += "\n\t\t-Tratamiento " + i + ": NULL";
				}
			}
			
			try {
				mensaje += "\n\n\t-Observaciones: "+ expediente.getObservaciones() + "\n\n";
			}
			catch(Exception t) {
				mensaje += "\n\t-Observaciones: NULL\n\n";
			}
			
			editorPaneData.setText(mensaje);
		}
		else 
			editorPaneData.setText(" >Expediente no encontrado.");
				
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
	public JEditorPane getEditorPaneData() {
		return editorPaneData;
	}


	public void setEditorPaneData(JEditorPane editorPaneData) {
		this.editorPaneData = editorPaneData;
	}


	public JEditorPane getEditorPaneLog() {
		return editorPaneLog;
	}

	public void setEditorPaneLog(JEditorPane editorPaneLog) {
		this.editorPaneLog = editorPaneLog;
	}
}
