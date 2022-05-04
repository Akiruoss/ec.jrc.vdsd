package eu.jrc.vdsd;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

public class VDSD_Barcode_Details_View extends JPanel {
	
	public VDSD_Barcode_Details_View(VDSD_Barcode _barcode)
	{
		setLayout(null);
				
		JPanel panelScrollContainer = new JPanel();
		panelScrollContainer.setLayout(null);
		panelScrollContainer.setBounds(20, 20, 560, 460);		
		
		JScrollPane scrollableContainer = new JScrollPane();  
		scrollableContainer.setSize(560, 500);
		scrollableContainer.setLocation(20, 20);				
		scrollableContainer.setViewportView(panelScrollContainer);		
		scrollableContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
		add(scrollableContainer);
		
		
		JPanel line1 = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawLine(0,0, 20, 35);
		    };
		};
		line1.setBackground(Color.BLACK);
		line1.setSize(520, 1);
		line1.setLocation(10, 290);		
		panelScrollContainer.add(line1);
		
		JPanel line2 = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawLine(0,0, 20, 35);
		    };
		};
		line2.setBackground(Color.BLACK);
		line2.setSize(520, 1);
		line2.setLocation(10, 460);		
		panelScrollContainer.add(line2);
		
		JPanel line3 = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawLine(0,0, 20, 35);
		    };
		};
		line3.setBackground(Color.BLACK);
		line3.setSize(520, 1);
		line3.setLocation(10, 660);		
		panelScrollContainer.add(line3);
		
		JPanel line4 = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawLine(0,0, 20, 35);
		    };
		};
		line4.setBackground(Color.BLACK);
		line4.setSize(520, 1);
		line4.setLocation(10, 850);		
		panelScrollContainer.add(line4);
		
		JLabel lblHeader = new JLabel("HEADER");
		lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblHeader.setBounds(20, 10, 500, 30);
		panelScrollContainer.add(lblHeader);
		
		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setBounds(20, 50, 140, 20);
		panelScrollContainer.add(lblVersion);
		
		JLabel lblIssuingCountry = new JLabel("Issuing Country:");
		lblIssuingCountry.setBounds(20, 80, 140, 20);
		panelScrollContainer.add(lblIssuingCountry);
		
		JLabel lblSignerIdentifier = new JLabel("Signer identifier:");
		lblSignerIdentifier.setBounds(20, 110, 140, 20);
		panelScrollContainer.add(lblSignerIdentifier);
		
		JLabel lblCertificateReference = new JLabel("Certificate reference:");
		lblCertificateReference.setBounds(20, 140, 140, 20);
		panelScrollContainer.add(lblCertificateReference);
		
		JLabel lblDocumentIssueDate = new JLabel("Document issue date:");
		lblDocumentIssueDate.setBounds(20, 170, 140, 20);
		panelScrollContainer.add(lblDocumentIssueDate);
		
		JLabel lblSignatureIssueDate = new JLabel("Signature issue date:");
		lblSignatureIssueDate.setBounds(20, 200, 140, 20);
		panelScrollContainer.add(lblSignatureIssueDate);
		
		JLabel lblDocumentReference = new JLabel("Document reference:");
		lblDocumentReference.setBounds(20, 230, 140, 20);
		panelScrollContainer.add(lblDocumentReference);
		
		JLabel lblDocumentType = new JLabel("Document type:");
		lblDocumentType.setBounds(20, 260, 140, 20);
		panelScrollContainer.add(lblDocumentType);
		
		JLabel lblMessage = new JLabel("MESSAGE");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblMessage.setBounds(20, 300, 500, 30);
		panelScrollContainer.add(lblMessage);
		
		JLabel lblMrz = new JLabel("MRZ:");
		lblMrz.setBounds(20, 340, 140, 20);
		panelScrollContainer.add(lblMrz);
		
		JLabel lblDurationOfStay = new JLabel("Duration of stay:");
		lblDurationOfStay.setBounds(20, 400, 140, 20);
		panelScrollContainer.add(lblDurationOfStay);
		
		JLabel lblPassportNumber = new JLabel("Passport number:");
		lblPassportNumber.setBounds(20, 430, 140, 20);
		panelScrollContainer.add(lblPassportNumber);
		
		JLabel lblSignature = new JLabel("SIGNATURE");
		lblSignature.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSignature.setBounds(20, 470, 500, 30);
		panelScrollContainer.add(lblSignature);
		
		JLabel lblR = new JLabel("r:");
		lblR.setBounds(20, 510, 140, 20);
		panelScrollContainer.add(lblR);
		
		JLabel lblS = new JLabel("s:");
		lblS.setBounds(20, 570, 140, 20);
		panelScrollContainer.add(lblS);
		
		JLabel lblSignatureIs = new JLabel("Signature is");
		lblSignatureIs.setBounds(20, 630, 140, 20);
		panelScrollContainer.add(lblSignatureIs);
		
		JLabel lblCertificate = new JLabel("CERTIFICATE");
		lblCertificate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblCertificate.setBounds(20, 660, 500, 30);
		panelScrollContainer.add(lblCertificate);
		
		JLabel lblIssuer = new JLabel("Issuer:");
		lblIssuer.setBounds(20, 700, 140, 20);
		panelScrollContainer.add(lblIssuer);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(20, 730, 140, 20);
		panelScrollContainer.add(lblSubject);
		
		JLabel lblNotBefore = new JLabel("Not before:");
		lblNotBefore.setBounds(20, 760, 140, 20);
		panelScrollContainer.add(lblNotBefore);
		
		JLabel lblNotAfter = new JLabel("Not after:");
		lblNotAfter.setBounds(20, 790, 140, 20);
		panelScrollContainer.add(lblNotAfter);
		
		JLabel lblSerial = new JLabel("Serial:");
		lblSerial.setBounds(20, 820, 140, 20);
		panelScrollContainer.add(lblSerial);
		
		JLabel lblBarcodeReadingTime = new JLabel("Barcode reading time:");
		lblBarcodeReadingTime.setBounds(20, 860, 140, 20);
		panelScrollContainer.add(lblBarcodeReadingTime);
		
		JLabel lblCertificateLookupTime = new JLabel("Certificate lookup time:");
		lblCertificateLookupTime.setBounds(20, 890, 140, 20);
		panelScrollContainer.add(lblCertificateLookupTime);
		
		JLabel lblSignatureVerification = new JLabel("Signature verification:");
		lblSignatureVerification.setBounds(20, 920, 140, 20);
		panelScrollContainer.add(lblSignatureVerification);
		
		JLabel lblBarcodeProcessingTime = new JLabel("Barcode processing time:");
		lblBarcodeProcessingTime.setBounds(20, 950, 140, 20);
		panelScrollContainer.add(lblBarcodeProcessingTime);
		
		JLabel lblTotalProcessingTime = new JLabel("Total processing time:");
		lblTotalProcessingTime.setBounds(20, 980, 140, 20);
		panelScrollContainer.add(lblTotalProcessingTime);
		
;
	}
}
