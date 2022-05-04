package eu.jrc.vdsd;

import java.io.Serializable;

public class VDSD_CRL_Container implements Serializable{

	private static VDSD_CRL_Container self;

	private VDSD_CRL_Map CRLs;


	public void setCERTS(VDSD_CRL_Map cRLS) {
		CRLs = cRLS;
	}


	public VDSD_CRL_Map getCERTS() {
		return CRLs;
	}


	private VDSD_CRL_Container(){
		CRLs = new VDSD_CRL_Map();
	}


	public static VDSD_CRL_Container getIstance()
	{
		if (self == null)
			self = new VDSD_CRL_Container();		
		return self;
	}
	

}
