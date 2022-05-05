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

import eu.jrc.vdsd.VDSD_CRL_Controller.VDSD_CRL_ACTION;
import eu.jrc.vdsd.VDSD_CRT_Controller.VDSD_CRT_ACTION;
import javax.swing.JLabel;

public class VDSD_Main extends JFrame implements VDSD_ScannerListener_Delegate, ActionListener, VDSD_CRT_Delegate, VDSD_CRL_Delegate
{
	//https://www.youtube.com/watch?v=Q8beQ6xW0s0
	private static final long serialVersionUID = 1L;

	static JComboBox<String> portList;
	static SerialPort[] portNames;
	public static SerialPort scannerComPort;

	JLabel lblCRTReady;
	JLabel lblCRLReady;
	JLabel lblScannerReady;
	
	long startTime;
	long readTime;

	VDSD_CRT_Container CRT_Container;
	VDSD_CRL_Container CRL_Container;
	VDSD_History_Container History_Container;
	private JLabel lblScanner;

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
		setSize(400,250);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnCrtCrl = new JMenu("MENU");
		menuBar.add(mnCrtCrl);

		JMenuItem mntmCRT = new JMenuItem("Certificate");
		mntmCRT.setActionCommand("CRT");
		mntmCRT.addActionListener(this);
		mnCrtCrl.add(mntmCRT);

		JMenuItem mntmCRL = new JMenuItem("CRL");
		mntmCRL.setActionCommand("CRL");
		mntmCRL.addActionListener(this);
		mnCrtCrl.add(mntmCRL);
		
		JMenuItem mntmHistory = new JMenuItem("History");
		mntmHistory.setActionCommand("HISTORY");
		mntmHistory.addActionListener(this);
		mnCrtCrl.add(mntmHistory);

		portList = new JComboBox<String>();
		portList.setSize(100, 20);
		portList.setLocation(127, 20);
		for (int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());

		JButton connectButton = new JButton("Connect");
		connectButton.setSize(100, 20);
		connectButton.setLocation(250, 20);
		connectButton.setActionCommand("connect");
		connectButton.addActionListener(this);
		getContentPane().add(connectButton);    
		getContentPane().add(portList);

		JLabel lblNewLabel = new JLabel("Select port:");
		lblNewLabel.setBounds(20, 20, 100, 20);
		getContentPane().add(lblNewLabel);

		JLabel lblCertificateDatabase = new JLabel("Certificate database:");
		lblCertificateDatabase.setBounds(20, 70, 150, 20);
		getContentPane().add(lblCertificateDatabase);

		JLabel lblCrlDatabase = new JLabel("CRL database:");
		lblCrlDatabase.setBounds(20, 100, 150, 20);
		getContentPane().add(lblCrlDatabase);				

		lblScanner = new JLabel("Scanner:");
		lblScanner.setBounds(20, 130, 150, 20);
		getContentPane().add(lblScanner);

		lblCRTReady = new JLabel("NOT Ready");
		lblCRTReady.setBounds(250, 70, 100, 20);
		getContentPane().add(lblCRTReady);

		lblCRLReady = new JLabel("NOT Ready");
		lblCRLReady.setBounds(250, 100, 100, 20);
		getContentPane().add(lblCRLReady);

		lblScannerReady = new JLabel("NOT Ready");
		lblScannerReady.setBounds(250, 130, 100, 20);
		getContentPane().add(lblScannerReady);


		CRT_Container = VDSD_CRT_Container.getIstance();

		VDSD_CRT_Controller controllerCRT = new VDSD_CRT_Controller();		
		controllerCRT.setAction(VDSD_CRT_ACTION.LOAD);
		controllerCRT.setDelegate(this);
		controllerCRT.execute();

		CRL_Container = VDSD_CRL_Container.getIstance();

		VDSD_CRL_Controller controllerCRL = new VDSD_CRL_Controller();		
		controllerCRL.setAction(VDSD_CRL_ACTION.LOAD);
		controllerCRL.setDelegate(this);
		controllerCRL.execute();

	}

	@Override
	public void processFinishCRT(VDSD_CRT_Map output) {
		CRT_Container.setCERTS(output);
		lblCRTReady.setText("READY");
		System.out.println("CRT_MAP LOADED");				
	}

	@Override
	public void processFinishCRL(VDSD_CRL_Map output) {
		CRL_Container.setCRLs(output);
		lblCRLReady.setText("READY");
		System.out.println("CRL_MAP LOADED");

	}

	@Override
	public void processFinishScannerListener(byte[] output) 
	{
		readTime = (System.nanoTime() - startTime);
		
		VDSD_ByteStringDecoder decoder = new  VDSD_ByteStringDecoder(output);
		VDSD_Barcode barcode = decoder.decode();
		
		barcode.readTime = readTime;
        long endTime = System.nanoTime();
        barcode.total_duration = (endTime - startTime);
		
		History_Container = VDSD_History_Container.getIstance();
		
        if (barcode.isValid && barcode.isSupported)
        	History_Container.add(barcode);
		
		VDSD_Barcode_View viewBarcode = new VDSD_Barcode_View(barcode);
		viewBarcode.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();


		switch (actionCommand) {
		case "connect": 		
			scannerComPort = portNames[portList.getSelectedIndex()];
			if (scannerComPort.openPort())
			{
				startTime = System.nanoTime();
				lblScannerReady.setText("CONNECT");
				VDSD_ScannerListener scannerListener = new VDSD_ScannerListener(); 
				scannerListener.delegate = this;
				scannerComPort.addDataListener(scannerListener);
			}
			else			
				lblScannerReady.setText("PORT ERROR");
			break;		
		case "CRT": 
			VDSD_CRT_View viewCRT = new VDSD_CRT_View();
			viewCRT.setVisible(true);	
			break;
		case "CRL": 
			VDSD_CRL_View viewCRL = new VDSD_CRL_View();
			viewCRL.setVisible(true);	
			break;
		case "HISTORY": 
			VDSD_History_View viewHistory = new VDSD_History_View();
			viewHistory.setVisible(true);	
			break;

		}	
	}
}