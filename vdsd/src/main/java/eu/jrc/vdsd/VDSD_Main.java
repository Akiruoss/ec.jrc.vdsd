package eu.jrc.vdsd;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.fazecast.jSerialComm.SerialPort;

public class VDSD_Main extends JFrame implements VDSD_ScannerListener_ReadComplete, ActionListener
{
//https://www.youtube.com/watch?v=Q8beQ6xW0s0
	private static final long serialVersionUID = 1L;
	
	static JComboBox<String> portList;
	static SerialPort[] portNames;
	public static SerialPort scannerComPort;

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
		
        setTitle("Sensor Graph GUI");
        setSize(600,400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel topPanel = new JPanel();
        portList = new JComboBox<String>();
        for (int i = 0; i < portNames.length; i++)
            portList.addItem(portNames[i].getSystemPortName());
        
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        topPanel.add(connectButton);    
        topPanel.add(portList);
        
        add(topPanel,BorderLayout.NORTH);
        
	}


	public void processFinish(byte[] output) {
		String rawbarcode = VDSD_Utils.encodeHexString(output);
		
	}


	public void actionPerformed(ActionEvent e) {
		
		scannerComPort = portNames[portList.getSelectedIndex()];
		scannerComPort.openPort();
		VDSD_ScannerListener scannerListener = new VDSD_ScannerListener(); 
		scannerListener.delegate = this;
		scannerComPort.addDataListener(scannerListener);
		
	}
}