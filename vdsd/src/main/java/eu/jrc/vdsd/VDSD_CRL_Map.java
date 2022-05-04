package eu.jrc.vdsd;

import java.io.Serializable;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class VDSD_CRL_Map extends TreeMap<String, TreeMap<String, TreeMap<String, String>>> implements Serializable 
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

	public List<Entry<String, String>> getAllCert(String C, String CN)
	{
		List<Entry<String, String>> cna = new ArrayList<>();		
		for (String s : get(C).get(CN).keySet())
		{
			Entry<String, String> ent = Map.entry(s,get(C).get(CN).get(s));
			cna.add(ent);
		}				
		return cna;
	}

	public TreeMap<String, TreeMap<String, String>> getC(String C)
	{
		return get(C);
	}

	public TreeMap<String, String> getCN(String C, String CN)
	{
		return get(C).get(CN);
	}

	void addCRL(X509CRL _crl) 
	{
		VDSD_CRL crl = new VDSD_CRL(_crl);
		addCRL(crl.C, crl.CN, crl.serial, crl.base64);		
	}

	String getCert(String _C, String _CN, String _Serial) 
	{
		TreeMap<String, TreeMap<String, String>> C = get(_C); 
		TreeMap<String, String> CN = C.get(_CN);
		String Serial = CN.get(_Serial);
		return Serial;			
	}

	void addCRL(String C, String CN, String Serial, String crl) 
	{

		if (get(C) != null) 
		{ 			
			if (get(C).get(CN) != null) 
			{						
				get(C).get(CN).putIfAbsent(Serial, crl);
			}
			else
			{					
				TreeMap<String, String> hCN = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				hCN.put(Serial,crl);
				get(C).put(CN,hCN);				
			}
		}
		else
		{
			TreeMap<String, TreeMap<String, String>> hC = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
			TreeMap<String, String> hCN = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
			hCN.put(Serial,crl);
			hC.put(CN,hCN);
			put(C, hC);
		}

	}
}