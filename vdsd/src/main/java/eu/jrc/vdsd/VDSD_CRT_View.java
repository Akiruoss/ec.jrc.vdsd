package eu.jrc.vdsd;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import eu.jrc.vdsd.VDSD_CRT_Controller.VDSD_CRT_ACTION;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class VDSD_CRT_View  extends JFrame implements ActionListener, VDSD_CRT_Delegate
{
	VDSD_CRT_Container CRT_Container;

	public VDSD_CRT_View() 
	{
		CRT_Container = VDSD_CRT_Container.getIstance();
		
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

		JButton btnAddCertDir = new JButton("Add Certs");
		btnAddCertDir.addActionListener(this);
		btnAddCertDir.setActionCommand("add");		
		menuBar.add(btnAddCertDir);

		JButton btnDelCertFile = new JButton("Del database");
		btnDelCertFile.addActionListener(this);
		btnDelCertFile.setActionCommand("del");
		menuBar.add(btnDelCertFile);
		
		VDSD_CRT_Map CRT = CRT_Container.getCERTS();
		if (CRT != null) {
			for (String CA : CRT.getAllC())
			{
				for (String CNA : CRT.getAllCN(CA))
				{
					for (Entry<String, String> Cert : CRT.getAllCert(CA,CNA))
					{
						VDSD_CRT_Layout certLayout = new VDSD_CRT_Layout(CA, CNA, Cert.getKey(), Cert.getValue());
						certPanel.add(certLayout);
						certInfoArea.setText(Cert.getValue());
					}
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
					VDSD_CRT_Controller controller = new VDSD_CRT_Controller();
					controller.setPath(selectedDir);
					controller.setAction(VDSD_CRT_ACTION.CREATE);
					controller.setDelegate(this);
					controller.execute();
				}
			} 		
			catch (Exception  ex)
			{

			}
			break;
		case "del": 
			VDSD_CRT_Controller controller = new VDSD_CRT_Controller();			
			controller.setAction(VDSD_CRT_ACTION.DELETE);
			controller.setDelegate(this);
			controller.execute();
			break;

		}

	}

	@Override
	public void processFinishCRT(VDSD_CRT_Map output) {
		CRT_Container.setCERTS(output);
		System.out.println("CRT_MAP LOADED");
	}
}
