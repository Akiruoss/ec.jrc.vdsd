package eu.jrc.vdsd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import eu.jrc.vdsd.VDSD_CRT_Controller.VDSD_CRT_ACTION;

public class VDSD_CRL_Controller  extends SwingWorker<VDSD_CRL_Map, String> {
	File path = null;
	Object delegate = null;
	VDSD_CRL_ACTION ACTION;

	public enum VDSD_CRL_ACTION
	{
		CREATE, DELETE, LOAD
	}

	public void setDelegate(Object delegate) {
		this.delegate = delegate;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public void setAction(VDSD_CRL_ACTION action) {
		this.ACTION = action;
	}

	public VDSD_CRL_Controller()
	{

	}

	public VDSD_CRL_Controller(String _path)
	{

	}

	public VDSD_CRL_Controller(String _path, VDSD_CRT_Controller _delegate)
	{

	}

	void init(String _path) 
	{

	}

	@Override
	protected VDSD_CRL_Map doInBackground() throws Exception 
	{			


		String appPath = VDSD_Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		String crl_mapFilename = "VDS_CRL_MAP.dat";
		VDSD_CRL_Map CRL_MAP = new VDSD_CRL_Map();

		if (ACTION == VDSD_CRL_ACTION.CREATE)
		{
			if (delegate == null || !path.exists() || !path.isDirectory())
			{
				return null;
			}
			for (final File fileEntry : path.listFiles()) 
			{
				if (!fileEntry.isDirectory()) 
				{	            
					System.out.println(fileEntry.getName());

					CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());				
					X509CRL crl = (X509CRL) cf.generateCRL(new FileInputStream(fileEntry));	

					if (crl != null)
						CRL_MAP.addCRL(crl);
				}	
			}
			try {

				FileOutputStream fileOut = new FileOutputStream(appPath + File.separator + crl_mapFilename);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(CRL_MAP);
				objectOut.flush();
				System.out.println("CRL_MAP Saved");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return CRL_MAP;
		}
		if (ACTION == VDSD_CRL_ACTION.DELETE)
		{
			if (delegate == null)			
				return null;

			File f = new File(appPath + File.separator + crl_mapFilename);
			if (f.exists())
			{
				File fileCRT = new File(appPath + File.separator + crl_mapFilename);
				fileCRT.delete();
				if (fileCRT.delete()) { 
					System.out.println("Deleted the file: " + fileCRT.getName());
				} else {
					System.out.println("Failed to delete the file.");
				} 
				return null;
			} 
			return null;
		}
		if (ACTION == VDSD_CRL_ACTION.LOAD)
		{
			if (delegate == null)			
				return null;
			File f = new File(appPath + File.separator + crl_mapFilename);
			if (f.exists())
			{
				FileInputStream file = new FileInputStream(appPath + File.separator + crl_mapFilename);
				ObjectInputStream in = new ObjectInputStream(file);             
				CRL_MAP = (VDSD_CRL_Map)in.readObject();              
				in.close();
				file.close();
				return CRL_MAP;
			}
			return null;
		}
		return null;
	}
	
    @Override
    protected void done() {
    	
    	try {
			VDSD_CRL_Map CRL_MAP = get();
			if (delegate instanceof VDSD_Main) 
				((VDSD_Main) delegate).processFinishCRL(CRL_MAP);
			if (delegate instanceof VDSD_CRT_View) 
				((VDSD_CRL_View) delegate).processFinishCRL(CRL_MAP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
