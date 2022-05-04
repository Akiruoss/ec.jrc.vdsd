package eu.jrc.vdsd;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class VDSD_Barcode_Advanced_View extends JPanel {

	public VDSD_Barcode_Advanced_View(VDSD_Barcode _barcode) 
	{
		setLayout(null);
		
		JLabel lblBarcodeBinaryString = new JLabel("Barcode binary string");
		lblBarcodeBinaryString.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblBarcodeBinaryString.setBounds(20, 20, 560, 50);
		add(lblBarcodeBinaryString);
		
		JLabel lblBarcodeBinaryStringLength = new JLabel("Length:");
		lblBarcodeBinaryStringLength.setBounds(20, 80, 100, 20);
		add(lblBarcodeBinaryStringLength);
		
		JTextArea txtlblBarcodeBinaryString = new JTextArea(_barcode.latinBarcode);
		txtlblBarcodeBinaryString.setEditable(false);
		txtlblBarcodeBinaryString.setEnabled(false);
		txtlblBarcodeBinaryString.setLineWrap(true);
		txtlblBarcodeBinaryString.setWrapStyleWord(true);
		txtlblBarcodeBinaryString.setBounds(20, 110, 520, 100);
		add(txtlblBarcodeBinaryString);
		
		JLabel lblBarcodeBinaryStringHex = new JLabel("Barcode binary string");
		lblBarcodeBinaryStringHex.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblBarcodeBinaryStringHex.setBounds(20, 240, 560, 50);
		add(lblBarcodeBinaryStringHex);
		
		JLabel lblBarcodeBinaryStringHexLength = new JLabel("Length:");
		lblBarcodeBinaryStringHexLength.setBounds(20, 300, 100, 20);
		add(lblBarcodeBinaryStringHexLength);
		
		JTextArea txtlblBarcodeBinaryStringHex = new JTextArea(_barcode.rawBarcode);
		txtlblBarcodeBinaryStringHex.setEnabled(false);
		txtlblBarcodeBinaryStringHex.setEditable(false);
		txtlblBarcodeBinaryStringHex.setLineWrap(true);
		txtlblBarcodeBinaryStringHex.setWrapStyleWord(true);
		txtlblBarcodeBinaryStringHex.setBounds(20, 330, 520, 100);
		add(txtlblBarcodeBinaryStringHex);
		
		JLabel txtBarcodeBinaryStringLength = new JLabel(""+_barcode.latinBarcode.length());
		txtBarcodeBinaryStringLength.setBounds(130, 80, 50, 20);
		add(txtBarcodeBinaryStringLength);
		
		JLabel txtBarcodeBinaryStringHexLength = new JLabel(""+_barcode.rawBarcode.length());
		txtBarcodeBinaryStringHexLength.setBounds(130, 300, 50, 20);
		add(txtBarcodeBinaryStringHexLength);
		
	}
}
