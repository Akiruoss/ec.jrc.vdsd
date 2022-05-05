package eu.jrc.vdsd;

import java.io.Serializable;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class VDSD_CRL_Map extends TreeMap<String, TreeMap<String, String>> implements Serializable 
{
	public List<String> getAllC()
	{
		List<String> ca = new ArrayList<>();		
		for (String s : keySet())		
			ca.add(s);				
		return ca;
	}

	public List<String> getAllCN(String C)
	{
		List<String> cna = new ArrayList<>();		
		for (String s : get(C).keySet())		
			cna.add(s);				
		return cna;
	}

	public List<Entry<String, String>> getAllCRL(String C)
	{
		List<Entry<String, String>> cna = new ArrayList<>();	
		for (String s : get(C).keySet())		
			cna.add(Map.entry(s,get(C).get(s)));	
						
		return cna;
	}

	public TreeMap<String, String> getC(String C)
	{
		return get(C);
	}

	void addCRL(X509CRL _crl) 
	{
		VDSD_CRL crl = new VDSD_CRL(_crl);
		addCRL(crl.C, crl.CN, crl.base64);		
	}

	String getCRL(String _C, String _CN) 
	{		
		TreeMap<String, String> C = get(_C); 
		String CRL = C.get(_CN);
		return CRL;			
	}

	void addCRL(String C, String CN, String crl) 
	{

		if (get(C) != null)  			
			get(C).put(CN, crl);		
		else
		{
			TreeMap<String, String> hC = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);						
			hC.put(CN,crl);
			put(C, hC);
		}

	}
}