package edu.ucam.client.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

import edu.ucam.client.ClientChronometer;

public class AreYouAFK extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblNewLabelCont;
	private PrintWriter pw;
	private ClientChronometer cc;
	


	/**
	 * Create the dialog.
	 */
	public AreYouAFK(ClientChronometer cc, PrintWriter pw) {
		this.cc = cc;
		this.setPw(pw);
		setBounds(100, 100, 439, 115);
		getContentPane().setLayout(new BorderLayout());
		setUndecorated(true);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblNewLabel = new JLabel("Est\u00E1s mucho tiempo sin hacer nada. Se te expulsar\u00E1 del servidor en...");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			lblNewLabelCont = new JLabel("10");
			lblNewLabelCont.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabelCont.setHorizontalAlignment(SwingConstants.CENTER);
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabelCont, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(17)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabelCont)
					.addGap(29))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Estoy aqu\u00ED");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						pw.println("IMHERE");
						pw.flush();
						cc.setSuspended(true);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	//Getters & Setters
	public JLabel getLblNewLabelCont() {
		return lblNewLabelCont;
	}


	public void setLblNewLabelCont(JLabel lblNewLabelCont) {
		this.lblNewLabelCont = lblNewLabelCont;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public ClientChronometer getCc() {
		return cc;
	}

	public void setCc(ClientChronometer cc) {
		this.cc = cc;
	}

}
