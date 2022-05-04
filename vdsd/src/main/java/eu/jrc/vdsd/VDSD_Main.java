package eu.jrc.vdsd;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import com.fazecast.jSerialComm.SerialPort;

import eu.jrc.vdsd.VDSD_CRT_Controller.VDSD_CRT_ACTION;

public class VDSD_Main extends JFrame implements VDSD_ScannerListener_Delegate, ActionListener, VDSD_CRT_Delegate, VDSD_CRL_Delegate
{
	//https://www.youtube.com/watch?v=Q8beQ6xW0s0
	private static final long serialVersionUID = 1L;

	static JComboBox<String> portList;
	static SerialPort[] portNames;
	public static SerialPort scannerComPort;
	VDSD_CRT_Container CRT_Container;

	public static void main( String[] args )
	{
		EventQueue.invokeLater(new Runnable() {

			public void run() {


				// populate the drop-down box
				portNames = SerialPort.getCommPorts();
				VDSD_Main mainWindow = new VDSD_Main();
				mainWindow.setVisible(true);

			}
		});
	}


	public VDSD_Main() 
	{

		setTitle("VDS Desktop Reader");
		setSize(600,400);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnCrtCrl = new JMenu("CRT/CRL");
		menuBar.add(mnCrtCrl);

		JMenuItem mntmCRT = new JMenuItem("Certificate");
		mntmCRT.setActionCommand("CRT");
		mntmCRT.addActionListener(this);
		mnCrtCrl.add(mntmCRT);

		JMenuItem mntmCRL = new JMenuItem("CRL");
		mntmCRL.setActionCommand("CRL");
		mntmCRL.addActionListener(this);
		mnCrtCrl.add(mntmCRL);


		JPanel topPanel = new JPanel();
		portList = new JComboBox<String>();
		for (int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());

		JButton connectButton = new JButton("Connect");
		connectButton.setActionCommand("connect");
		connectButton.addActionListener(this);
		topPanel.add(connectButton);    
		topPanel.add(portList);

		add(topPanel,BorderLayout.NORTH);
		
		CRT_Container = VDSD_CRT_Container.getIstance();
		
		VDSD_CRT_Controller controller = new VDSD_CRT_Controller();		
		controller.setAction(VDSD_CRT_ACTION.LOAD);
		controller.setDelegate(this);
		controller.execute();

	}

	@Override
	public void processFinishCRT(VDSD_CRT_Map output) {
		CRT_Container.setCERTS(output);
		System.out.println("CRT_MAP LOADED");
		
		
	}
	
	@Override
	public void processFinishScannerListener(byte[] output) {
		
		VDSD_ByteStringDecoder decoder = new  VDSD_ByteStringDecoder(output);
		VDSD_Barcode barcode = decoder.decode();
		VDSD_Barcode_View viewBarcode = new VDSD_Barcode_View(barcode);
		viewBarcode.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();


		switch (actionCommand) {
		case "connect": 
			scannerComPort = portNames[portList.getSelectedIndex()];
			scannerComPort.openPort();
			VDSD_ScannerListener scannerListener = new VDSD_ScannerListener(); 
			scannerListener.delegate = this;
			scannerComPort.addDataListener(scannerListener);
			break;
		case "CRT": 
			VDSD_CRT_View viewCRT = new VDSD_CRT_View();
			viewCRT.setVisible(true);	
			break;
		case "CRL": 
			VDSD_CRL_View viewCRL = new VDSD_CRL_View();
			viewCRL.setVisible(true);	
			break;

		}	
	}


	@Override
	public void processFinishCRL(VDSD_CRL_Map output) {
		// TODO Auto-generated method stub
		
	}



}