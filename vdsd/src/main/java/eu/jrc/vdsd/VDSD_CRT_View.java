package eu.jrc.vdsd;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class VDSD_CRT_View  extends JFrame implements ActionListener, VDSD_CRT_Delegate{

	public VDSD_CRT_View() 
	{

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 80, 808, 1042);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
					controller.setDelegate(this);
					controller.execute();
				}
			} 		
	        catch (Exception  ex)
	        {
	        	
	        }
			
		case "del": 

			break;

		}

	}

	@Override
	public void processFinish(byte[] output) {

	}
}
