package eu.jrc.vdsd;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Color;

public class VDSD_Barcode_Overview_View extends JPanel 
{
	
	VDSD_Barcode_Overview_View(VDSD_Barcode _barcode)
	{
		setLayout(null);
		
		JPanel line1 = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawLine(0,0, 20, 35);
		    };
		};
		line1.setBackground(Color.BLACK);
		line1.setSize(520, 1);
		line1.setLocation(20, 50);
		
		add(line1);
		
		JPanel line2 = new JPanel() {
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawLine(0,0, 20, 35);
		    };
		};
		line2.setBackground(Color.BLACK);
		line2.setSize(520, 1);
		line2.setLocation(20, 350);
		
		add(line2);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(20, 80, 140, 20);
		add(lblName);

		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setBounds(20, 110, 140, 20);
		add(lblSurname);

		JLabel lblPassportNumber = new JLabel("Passport number:");
		lblPassportNumber.setBounds(20, 140, 140, 20);
		add(lblPassportNumber);

		JLabel lblDocumentNumber = new JLabel("Document number:");
		lblDocumentNumber.setBounds(20, 170, 140, 20);
		add(lblDocumentNumber);

		JLabel lblIssuingCountry = new JLabel("Issuing country:");
		lblIssuingCountry.setBounds(20, 200, 140, 20);
		add(lblIssuingCountry);

		JLabel lblDocumentIssueDate = new JLabel("Document issue date:");
		lblDocumentIssueDate.setBounds(20, 230, 140, 20);
		add(lblDocumentIssueDate);

		JLabel lblValidUntil = new JLabel("Valid until:");
		lblValidUntil.setBounds(20, 260, 140, 20);
		add(lblValidUntil);
		
		JLabel lblNOfEntries = new JLabel("Number of entries:");
		lblNOfEntries.setBounds(20, 290, 140, 20);
		add(lblNOfEntries);

		JLabel lblDurationOfStay = new JLabel("Duration of stay:");
		lblDurationOfStay.setBounds(20, 320, 140, 20);
		add(lblDurationOfStay);

		JLabel lblSignatureVerfication = new JLabel("Signature Verfication:");
		lblSignatureVerfication.setBounds(20, 360, 140, 20);
		add(lblSignatureVerfication);
		
		setBounds(10, 80, 600, 500);
	
		String mrz_r1 = _barcode.message.MRZ == null ? "" : _barcode.message.MRZ.MRZ_1r;
		String mrz_r2 = _barcode.message.MRZ == null ? "" : _barcode.message.MRZ.MRZ_2r;
		String mrz_name = _barcode.message.MRZ == null ? "" : _barcode.message.MRZ.Name;  
		String mrz_surname = _barcode.message.MRZ == null ? "" : _barcode.message.MRZ.Surname;
		String mrz_pass_visa_no = _barcode.message.MRZ == null ? "" : _barcode.message.MRZ.Pass_Visa_No;
		String mrz_date_visa_expiry = _barcode.message.MRZ == null ? "" : _barcode.message.MRZ.getDateOfVisaExpiry();
		String mrz_duration_stay = _barcode.message.DurationOfStay == null ? "" :_barcode.message.DurationOfStay.getDate();
		
		
		
		JLabel txtName = new JLabel(mrz_name);		
		txtName.setBounds(170, 80, 300, 20);
		add(txtName);
		
		JLabel txtSurname = new JLabel(mrz_surname);
		txtSurname.setBounds(170, 110, 300, 20);
		add(txtSurname);
		
		
		JLabel txtPassportNumber = new JLabel(_barcode.message.PassportNumber);		
		txtPassportNumber.setBounds(170, 140, 300, 20);
		add(txtPassportNumber);
		
		JLabel txtDocumentNumber = new JLabel(mrz_pass_visa_no);		
		txtDocumentNumber.setBounds(170, 170, 300, 20);
		add(txtDocumentNumber);
		
		JLabel txtIssuingCountry = new JLabel(_barcode.header.IssuingCountry);		
		txtIssuingCountry.setBounds(170, 200, 300, 20);
		add(txtIssuingCountry);
		
		JLabel txtDocumentIssueDate = new JLabel(_barcode.header.DocumentIssueDate);		
		txtDocumentIssueDate.setBounds(170, 230, 300, 20);
		add(txtDocumentIssueDate);
		
		JLabel txtValidUntil = new JLabel(mrz_date_visa_expiry);		
		txtValidUntil.setBounds(170, 260, 300, 20);
		add(txtValidUntil);
		

		JLabel txtNOfEntries = new JLabel(_barcode.message.nOfEntries);		
		txtNOfEntries.setBounds(170, 290, 300, 20);
		add(txtNOfEntries);
		
		JLabel txtDurationOfStay = new JLabel(mrz_duration_stay);		
		txtDurationOfStay.setBounds(170, 320, 300, 20);
		add(txtDurationOfStay);
		
		JLabel lblMRZ1 = new JLabel(mrz_r1);
		lblMRZ1.setBounds(20, 0, 400, 20);
		add(lblMRZ1);
		
		JLabel lblMRZ2 = new JLabel(mrz_r2);
		lblMRZ2.setBounds(20, 20, 400, 20);
		add(lblMRZ2);
		
		JLabel txtSignatureVerfication = new JLabel(""+_barcode.isVerified);
		txtSignatureVerfication.setBounds(170, 360, 300, 20);
		add(txtSignatureVerfication);
		
		JLabel lblError = new JLabel("Error:");
		lblError.setBounds(20, 390, 140, 20);
		add(lblError);
		
		JLabel txtError = new JLabel(_barcode.error);
		txtError.setBounds(170, 390, 300, 20);
		add(txtError);
		
        if (_barcode.message.nOfEntries == null || _barcode.message.nOfEntries.length() < 1 || _barcode.message.nOfEntries.equalsIgnoreCase(""))
        {
        	
        }

	}
}
