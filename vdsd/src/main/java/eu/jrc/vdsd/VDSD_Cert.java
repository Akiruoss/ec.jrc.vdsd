package eu.jrc.vdsd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

public class VDSD_Cert {
	public String issuer = "";
	public String subject = "";
	public String issueDate = "";
	public String expireDate = "";

	public String C = "";
	public String CN = "";
	public String issuerC = "";
	public String issuerCN = "";
	public String serial = "";
	public String notBefore = "";
	public String notAfter = "";
	public String fingerprint = "";
	public X509Certificate x509Cert = null;

	public VDSD_Cert(String _c, String _cn, String _serial, X509Certificate _x509Cert)
	{
		try {
			C = _c;
			CN = _cn;
			x509Cert = _x509Cert;
			X500Name x500name = new JcaX509CertificateHolder(x509Cert).getIssuer();
			RDN iC = x500name.getRDNs(BCStyle.C)[0];
			RDN iCN = x500name.getRDNs(BCStyle.CN)[0];
			issuerC = IETFUtils.valueToString(iC.getFirst().getValue());
			issuerCN = IETFUtils.valueToString(iCN.getFirst().getValue());

			issuer = x509Cert.getIssuerDN().getName();
			subject = x509Cert.getSubjectDN().getName();
			issueDate = x509Cert.getNotBefore().toString();
			expireDate = x509Cert.getNotAfter().toString();
			serial = x509Cert.getSerialNumber().toString();
			serial = VDSD_Utils.StringToHex(serial);
			if (serial.length() > 2)
			{
				if ((serial.length() % 2) != 0)
					serial = "0" +serial;
				serial = serial.replaceAll(".{2}", "$0:");
				serial = serial.substring(0, serial.length() - 1);

			}

			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			notAfter = df.format(x509Cert.getNotAfter());
			notBefore = df.format(x509Cert.getNotBefore());

			fingerprint = getThumbprint(x509Cert);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
		if (fingerprint.length() > 2)
		{
			if ((fingerprint.length() % 2) != 0)
				fingerprint = "0" +fingerprint;
			fingerprint = fingerprint.replaceAll(".{2}", "$0:");
			fingerprint = fingerprint.substring(0, fingerprint.length() - 1);

		}
	}

	public  VDSD_Cert(X509Certificate _x509Cert)
	{
		try {

			x509Cert =_x509Cert;

			X500Name x500nameS = new JcaX509CertificateHolder(x509Cert).getSubject();
			RDN sC = x500nameS.getRDNs(BCStyle.C)[0];
			RDN sCN = x500nameS.getRDNs(BCStyle.CN)[0];
			C = IETFUtils.valueToString(sC.getFirst().getValue());
			CN = IETFUtils.valueToString(sCN.getFirst().getValue());

			X500Name x500name = new JcaX509CertificateHolder(x509Cert).getIssuer();
			RDN iC = x500name.getRDNs(BCStyle.C)[0];
			RDN iCN = x500name.getRDNs(BCStyle.CN)[0];
			issuerC = IETFUtils.valueToString(iC.getFirst().getValue());
			issuerCN = IETFUtils.valueToString(iCN.getFirst().getValue());

			issuer = x509Cert.getIssuerDN().getName();
			subject = x509Cert.getSubjectDN().getName();
			issueDate = x509Cert.getNotBefore().toString();
			expireDate = x509Cert.getNotAfter().toString();
			serial = x509Cert.getSerialNumber().toString();
			serial = VDSD_Utils.StringToHex(serial);
			if (serial.length() > 2)
			{
				if ((serial.length() % 2) != 0)
					serial = "0" +serial;
				serial = serial.replaceAll(".{2}", "$0:");
				serial = serial.substring(0, serial.length() - 1);

			}

			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			notAfter = df.format(x509Cert.getNotAfter());
			notBefore = df.format(x509Cert.getNotBefore());

			fingerprint = getThumbprint(x509Cert);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
		if (fingerprint.length() > 2)
		{
			if ((fingerprint.length() % 2) != 0)
				fingerprint = "0" +fingerprint;
			fingerprint = fingerprint.replaceAll(".{2}", "$0:");
			fingerprint = fingerprint.substring(0, fingerprint.length() - 1);

		}
	}

	private static String getThumbprint(X509Certificate cert)
			throws NoSuchAlgorithmException, CertificateEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] der = cert.getEncoded();
		md.update(der);
		byte[] digest = md.digest();
		String digestHex = DatatypeConverter.printHexBinary(digest);
		return digestHex.toLowerCase();
	}

}
