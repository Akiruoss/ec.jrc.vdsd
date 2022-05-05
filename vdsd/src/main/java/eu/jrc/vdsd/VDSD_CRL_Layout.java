package eu.jrc.vdsd;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VDSD_CRL_Layout extends JPanel{
	String crlInfo = "";
	
	public String getCRLInfo() {
		return crlInfo;
	}
	
	VDSD_CRL_Layout(String C, String CN, String base64)
	{
	
		setLayout(null);
		
		JLabel lblC = new JLabel("C:");
		lblC.setBounds(10, 10, 30, 20);
		add(lblC);
		
		JLabel lblCN = new JLabel("CN:");
		lblCN.setBounds(150, 10, 30, 20);
		add(lblCN);
		
		JLabel txtC = new JLabel(C);
		txtC.setBounds(50, 13, 50, 20);
		add(txtC);
		
		JLabel lblCN_1 = new JLabel(CN);
		lblCN_1.setBounds(190, 10, 50, 20);
		add(lblCN_1);
		


	}

}
