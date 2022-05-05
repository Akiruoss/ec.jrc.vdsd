package eu.jrc.vdsd;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class VDSD_History_View extends JFrame implements ActionListener
{
	VDSD_History_Container History_Container;
	private JTable table;
	VDSD_History_View()
	{

		History_Container = VDSD_History_Container.getIstance();

		getContentPane().setLayout(null);
		
		table = new JTable(new DefaultTableModel(
			new Object[][] {
				{"Version", "IssuingCountry", "CertificateAuthorityAndReference", "DocumentIssueDate", "SignatureCreationDate", "DocumentFeatureDefinitionReference", "DocumentTypeCategory", "MRZ", "nOfEntries", "DurationOfStay", "PassportNumber", "R", "S", "ReadTime", "CertLookUp", "SignatureVerification", "ProcessingTime", "TotalTime", "Barcode"},
			},
			new String[] {
				"Version", "IssuingCountry", "CertificateAuthorityAndReference", "DocumentIssueDate", "SignatureCreationDate", "DocumentFeatureDefinitionReference", "DocumentTypeCategory", "MRZ", "nOfEntries", "DurationOfStay", "PassportNumber", "R", "S", "ReadTime", "CertLookUp", "SignatureVerification", "ProcessingTime", "TotalTime", "Barcode"
			}
		));
		table.setBounds(10, 11, 764, 644);
		getContentPane().add(table);
		setBounds(10, 80, 800, 730);


		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton btnAddCertDir = new JButton("SAVE");
		btnAddCertDir.addActionListener(this);
		btnAddCertDir.setActionCommand("add");		
		menuBar.add(btnAddCertDir);

		JButton btnDelCertFile = new JButton("DELETE");
		btnDelCertFile.addActionListener(this);
		btnDelCertFile.setActionCommand("del");
		menuBar.add(btnDelCertFile);

		VDSD_History_Container History = History_Container.getIstance();

		for (Entry<Integer, VDSD_History_Entry> entry : History.entrySet())
		{

		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {


	}
}