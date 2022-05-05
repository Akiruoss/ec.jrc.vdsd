package eu.jrc.vdsd;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import eu.jrc.vdsd.VDSD_CRL_Controller.VDSD_CRL_ACTION;
import eu.jrc.vdsd.VDSD_CRT_Controller.VDSD_CRT_ACTION;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map.Entry;

public class VDSD_CRL_View extends JFrame implements ActionListener, VDSD_CRL_Delegate{

	VDSD_CRL_Container CRL_Container;

	public VDSD_CRL_View() {
		
		CRL_Container = VDSD_CRL_Container.getIstance();
		
		getContentPane().setLayout(null);
		setBounds(10, 80, 800, 730);

		JPanel certPanel = new JPanel();
		certPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		certPanel.setBounds(10, 11, 250, 640);
		getContentPane().add(certPanel);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoPanel.setBounds(270, 11, 500, 640);
		getContentPane().add(infoPanel);

		JTextArea certInfoArea = new JTextArea();
		infoPanel.add(certInfoArea);


		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton btnAddCertDir = new JButton("Add CRLs");
		btnAddCertDir.addActionListener(this);
		btnAddCertDir.setActionCommand("add");		
		menuBar.add(btnAddCertDir);

		JButton btnDelCertFile = new JButton("Del CRLs database");
		btnDelCertFile.addActionListener(this);
		btnDelCertFile.setActionCommand("del");
		menuBar.add(btnDelCertFile);

		VDSD_CRL_Map CRLs = CRL_Container.getCRLs();

		if (CRLs != null) {
			for (String CA : CRLs.getAllC())
			{
				for (Entry<String, String> CRL : CRLs.getAllCRL(CA))
				{
					VDSD_CRL_Layout certLayout = new VDSD_CRL_Layout(CA, CRL.getKey(), CRL.getValue());
					certPanel.add(certLayout);
					certInfoArea.setText(CRL.getValue());
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case "add":

			try {
				JFileChooser dir = new JFileChooser(new File(VDSD_Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
				// file.setCurrentDirectory(new File(System.getProperty("user.home")));
				dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = dir.showSaveDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDir = dir.getSelectedFile();				
					VDSD_CRL_Controller controller = new VDSD_CRL_Controller();
					controller.setPath(selectedDir);
					controller.setAction(VDSD_CRL_ACTION.CREATE);
					controller.setDelegate(this);
					controller.execute();
				}
			} 		
			catch (Exception  ex)
			{

			}
			break;
		case "del": 
			VDSD_CRL_Controller controller = new VDSD_CRL_Controller();			
			controller.setAction(VDSD_CRL_ACTION.DELETE);
			controller.setDelegate(this);
			controller.execute();
			break;

		}

	}

	@Override
	public void processFinishCRL(VDSD_CRL_Map output) {
		CRL_Container.setCRLs(output);
		System.out.println("CRL_MAP LOADED");

	}
}
