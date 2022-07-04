package eu.jrc.vdsd;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutionException;

public class VDSD_CRT_Controller extends SwingWorker<VDSD_CRT_Map, String>{

	File path = null;
	Object delegate = null;
	VDSD_CRT_ACTION ACTION;

	public enum VDSD_CRT_ACTION
	{
		CREATE, DELETE, LOAD
	}

	public void setDelegate(Object delegate) {
		this.delegate = delegate;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public void setAction(VDSD_CRT_ACTION action) {
		this.ACTION = action;
	}

	public VDSD_CRT_Controller()
	{

	}

	public VDSD_CRT_Controller(String _path)
	{

	}

	public VDSD_CRT_Controller(String _path, VDSD_CRT_Controller _delegate)
	{

	}

	void init(String _path) 
	{

	}

	@Override
	protected VDSD_CRT_Map doInBackground() throws Exception 
	{			


		String appPath = VDSD_CRT_Controller.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		String decodedAppPath = URLDecoder.decode(appPath, "UTF-8");
		File pathFile = new File(decodedAppPath);
		
		if (pathFile.isFile())			
			decodedAppPath = pathFile.getParent();   
				
		String crt_mapFilename = "VDS_CRT_MAP.dat";
		VDSD_CRT_Map CRT_MAP = new VDSD_CRT_Map();

		if (ACTION == VDSD_CRT_ACTION.CREATE)
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
					X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(fileEntry));	

					if (cert != null)
						CRT_MAP.addCRT(cert);
				}	
			}
			try {

				FileOutputStream fileOut = new FileOutputStream(decodedAppPath + File.separator + crt_mapFilename);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(CRT_MAP);
				objectOut.flush();
				System.out.println("CRT_MAP Saved");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return CRT_MAP;
		}
		if (ACTION == VDSD_CRT_ACTION.DELETE)
		{
			if (delegate == null)			
				return null;

			File f = new File(decodedAppPath + File.separator + crt_mapFilename);
			if (f.exists())
			{
				File fileCRT = new File(decodedAppPath + File.separator + crt_mapFilename);
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
		if (ACTION == VDSD_CRT_ACTION.LOAD)
		{
			if (delegate == null)			
				return null;
			File f = new File(decodedAppPath + File.separator + crt_mapFilename);
			if (f.exists())
			{
				FileInputStream file = new FileInputStream(decodedAppPath + File.separator + crt_mapFilename);
				ObjectInputStream in = new ObjectInputStream(file);             
				CRT_MAP = (VDSD_CRT_Map)in.readObject();              
				in.close();
				file.close();
				return CRT_MAP;
			}
			return null;
		}
		return null;
	}
	
    @Override
    protected void done() {
    	
    	try {
			VDSD_CRT_Map CRT_MAP = get();
			if (delegate instanceof VDSD_Main) 
				((VDSD_Main) delegate).processFinishCRT(CRT_MAP);
			if (delegate instanceof VDSD_CRT_View) 
				((VDSD_CRT_View) delegate).processFinishCRT(CRT_MAP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    

}
