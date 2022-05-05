package eu.jrc.vdsd;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.swing.JPanel;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class VDSD_CRT_Layout extends JPanel{
	String certInfo = "";



	public String getCertInfo() {
		return certInfo;
	}



	VDSD_CRT_Layout(String C, String CN, String Serial, String base64)
	{
		JLabel lblC = new JLabel("C:");
		lblC.setBounds(10, 10, 30, 20);
		add(lblC);
		
		JLabel lblCN = new JLabel("CN:");
		lblCN.setBounds(150, 10, 30, 20);
		add(lblCN);
		
		JLabel lblSerial = new JLabel("Serial:");
		lblSerial.setBounds(10, 40, 230, 20);
		add(lblSerial);
		
		JLabel txtC = new JLabel(C);
		txtC.setBounds(50, 13, 50, 20);
		add(txtC);
		
		JLabel lblCN_1 = new JLabel(CN);
		lblCN_1.setBounds(190, 10, 50, 20);
		add(lblCN_1);
		
		JTextArea textArea = new JTextArea(Serial);
		textArea.setBackground(SystemColor.controlHighlight);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 64, 230, 40);
		add(textArea);

		
		try 
		{
			byte encodedCert[] = Base64.getDecoder().decode(base64);
			ByteArrayInputStream inputStream  =  new ByteArrayInputStream(encodedCert);
			CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
			certInfo = cert.toString();
			
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
					
		
	}
}
