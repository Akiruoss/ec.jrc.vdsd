package eu.jrc.vdsd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.SwingWorker;


public class VDSD_History_Controller extends SwingWorker<String, String>
{
	Object delegate = null;
	static final String HISTORY_TITLE = "\"Version\",\"IssuingCountry\",\"CertificateAuthorityAndReference\",\"DocumentIssueDate\",\"SignatureCreationDate\",\"DocumentFeatureDefinitionReference\",\"DocumentTypeCategory\",\"MRZ\",\"nOfEntries\",\"DurationOfStay\",\"PassportNumber\",\"R\",\"S\",\"ReadTime\",\"CertLookUp\",\"SignatureVerification\",\"ProcessingTime\",\"TotalTime\",\"Barcode\""+System.getProperty("line.separator")+System.getProperty("line.separator");
	String HISTORY_ENTRY = null;

	VDSD_History_ACTION ACTION;

	public enum VDSD_History_ACTION
	{
		CREATE, DELETE, LOAD, ADD
	}
	public void setHISTORY_ENTRY(String entry)
	{
		HISTORY_ENTRY = entry;
	}
	public void setAction(VDSD_History_ACTION action) {
		this.ACTION = action;
	}

	public VDSD_History_Controller()
	{

	}

	@Override
	protected String doInBackground() throws Exception 
	{
		String appPath = VDSD_Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		String historyFilename = "VDS_History_CSV.csv";
		File historySaveFile = new File(appPath + File.separator + historyFilename);
		if (ACTION == VDSD_History_ACTION.CREATE)
		{
			if (!historySaveFile.exists() || historySaveFile.isDirectory())
			{
				historySaveFile.createNewFile();
				
				
				FileWriter fw = null;
				BufferedWriter bw = null;
				PrintWriter pw = null;

				try 
				{
					fw = new FileWriter(historySaveFile, true);
					bw = new BufferedWriter(fw);
					pw = new PrintWriter(bw);
					pw.println(HISTORY_TITLE);
					pw.flush();

				} 
				finally 
				{
					try {
						pw.close();
						bw.close();
						fw.close();
					} 
					catch (IOException io){	}

				}
				
			}
		}
		if (ACTION == VDSD_History_ACTION.ADD)
		{
			if (HISTORY_ENTRY != null || historySaveFile.exists() || historySaveFile.isFile())
			{
				FileWriter fw = null;
				BufferedWriter bw = null;
				PrintWriter pw = null;

				try 
				{
					fw = new FileWriter(historySaveFile, true);
					bw = new BufferedWriter(fw);
					pw = new PrintWriter(bw);
					pw.println(HISTORY_ENTRY);
					pw.flush();

				} 
				finally 
				{
					try {
						pw.close();
						bw.close();
						fw.close();
					} 
					catch (IOException io){	}

				}
			}
		}

		return null;
	}

}
