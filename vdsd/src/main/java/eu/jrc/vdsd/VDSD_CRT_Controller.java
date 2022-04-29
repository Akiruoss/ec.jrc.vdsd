package eu.jrc.vdsd;

import javax.swing.SwingWorker;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class VDSD_CRT_Controller extends SwingWorker<String, String>{

	File path = null;
	VDSD_CRT_View delegate = null;

	public void setDelegate(VDSD_CRT_View delegate) {
		this.delegate = delegate;
	}

	public void setPath(File path) {
		this.path = path;
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
	protected String doInBackground() throws Exception 
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
				System.out.println(cert.toString());
			}			
		}
		return null;
	}

}
