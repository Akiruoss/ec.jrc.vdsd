package eu.jrc.vdsd;

import java.io.Serializable;
import java.util.TreeMap;

import eu.jrc.vdsd.VDSD_History_Controller.VDSD_History_ACTION;

public class VDSD_History_Container extends TreeMap<Integer, VDSD_History_Entry> implements Serializable  {
	
	private static VDSD_History_Container self;	

	private VDSD_History_Container()
	{}
	
	public static VDSD_History_Container getIstance()
	{
		if (self == null) 
		{
			self = new VDSD_History_Container();
			VDSD_History_Controller controller = new VDSD_History_Controller();
			controller.setAction(VDSD_History_ACTION.CREATE);
			controller.execute();
		}		
		return self;
	}
	
	public void add(VDSD_Barcode barcode) 
	{
		VDSD_History_Entry entry = new VDSD_History_Entry(barcode);
		int i = 0;
		if (size() != 0)
			i = lastKey();
		
		put(i+1,entry);
		
		VDSD_History_Controller controller = new VDSD_History_Controller();
		controller.setAction(VDSD_History_ACTION.ADD);
		controller.setHISTORY_ENTRY(entry.toCSVString());
		controller.execute();
		
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
