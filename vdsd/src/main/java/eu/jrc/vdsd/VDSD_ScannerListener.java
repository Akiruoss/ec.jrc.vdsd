package eu.jrc.vdsd;

import com.fazecast.jSerialComm.*;

public class VDSD_ScannerListener  implements SerialPortDataListener{

	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; 
	}

	public void serialEvent(SerialPortEvent event) {
		byte[] buffer = new byte[event.getSerialPort().bytesAvailable()];
        event.getSerialPort().readBytes(buffer, buffer.length);
        String rawbarcode = VDSD_Utils.encodeHexString(buffer);
		
	}

}
