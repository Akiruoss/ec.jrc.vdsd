package eu.jrc.vdsd;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class VDSD_CRT_Container implements Serializable{

	private static VDSD_CRT_Container self;

	private VDSD_CRT_Map CERTS;


	public void setCERTS(VDSD_CRT_Map cERTS) {
		CERTS = cERTS;
	}


	public VDSD_CRT_Map getCERTS() {
		return CERTS;
	}


	private VDSD_CRT_Container(){
		CERTS = new VDSD_CRT_Map();
	}


	public static VDSD_CRT_Container getIstance()
	{
		if (self == null)
			self = new VDSD_CRT_Container();		
		return self;
	}
	

}
