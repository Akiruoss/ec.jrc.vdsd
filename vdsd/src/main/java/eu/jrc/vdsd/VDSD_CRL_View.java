package eu.jrc.vdsd;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VDSD_CRL_View extends JFrame implements ActionListener{

	public VDSD_CRL_View() {
		
		getContentPane().setLayout(null);
		JLabel lblTitle = new JLabel("2D Barcode Generation");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setBounds(20, 20, 540, 50);
		getContentPane().add(lblTitle);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 80, 600, 500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();


		switch (actionCommand) {
		case "connect": 

		case "CRT": 

			break;
		case "CRL": 

			break;

		}	
		
	}
}
