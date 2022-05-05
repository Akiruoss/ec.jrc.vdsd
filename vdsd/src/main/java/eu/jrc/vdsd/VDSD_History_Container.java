package eu.jrc.vdsd;

import java.io.Serializable;
import java.util.TreeMap;

public class VDSD_History_Container extends TreeMap<Integer, VDSD_History_Entry> implements Serializable  {
	
	private static VDSD_History_Container self;

	private VDSD_History_Container()
	{}
	
	public static VDSD_History_Container getIstance()
	{
		if (self == null)
			self = new VDSD_History_Container();		
		return self;
	}
	
	public void add(VDSD_Barcode barcode) 
	{
		VDSD_History_Entry entry = new VDSD_History_Entry(barcode);
		int i = lastKey();
		put(i+1,entry);
	}
	
	public void del(int i)
	{
		remove(i);
	}
	
	public VDSD_History_Entry getIndex(int i)
	{
		return get(i);
	}

}
