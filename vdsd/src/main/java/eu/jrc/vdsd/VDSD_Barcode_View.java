package eu.jrc.vdsd;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class VDSD_Barcode_View extends JFrame {

	public VDSD_Barcode_View(VDSD_Barcode _barcode) {

		getContentPane().setLayout(null);

		setTitle("VDS Desktop Reader");
		setSize(600,600);
		getContentPane().setLayout(new BorderLayout());
		
		
		JTabbedPane tabbedPane = new JTabbedPane();		
		tabbedPane.addTab("Overview", new VDSD_Barcode_Overview_View(_barcode));
		tabbedPane.addTab("Details", new VDSD_Barcode_Details_View(_barcode));
		tabbedPane.addTab("Advanced", new VDSD_Barcode_Advanced_View(_barcode));
		tabbedPane.setBounds(0, 0, 600, 500);
		getContentPane().add(tabbedPane);
		




	}
}
