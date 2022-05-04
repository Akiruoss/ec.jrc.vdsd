package eu.jrc.vdsd;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CRLHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class VDSD_CRL implements Serializable{
    public String C = "";
    public String CN = "";
    public String serial = "";
    public String base64 = null;
    public X509CRL crl = null;

    public String issuer = "";
    public String lastUpdate = "";
    public String nextUpdate = "";

    public VDSD_CRL(X509CRL _crl)
    {
        crl = _crl;
        try {
            X500Name x500name = new JcaX509CRLHolder(crl).getIssuer();
            RDN cn = x500name.getRDNs(BCStyle.CN)[0];
            RDN c = x500name.getRDNs(BCStyle.C)[0];

            C = IETFUtils.valueToString(c.getFirst().getValue());
            CN = IETFUtils.valueToString(cn.getFirst().getValue());
            serial = String.valueOf(crl.getVersion());
            base64 = Base64.getEncoder().encodeToString(crl.getEncoded());

            issuer = crl.getIssuerDN().getName();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            lastUpdate = df.format(crl.getThisUpdate());
            nextUpdate = df.format(crl.getNextUpdate());
        } catch (CRLException e) {
            e.printStackTrace();

        }
    }

    public VDSD_CRL(String _c, String _cn, String _serial, String _base64)
    {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
            InputStream txtCRLStream = new ByteArrayInputStream(_base64.getBytes());
            crl = (X509CRL) cf.generateCRL(txtCRLStream);

            X500Name x500name = new JcaX509CRLHolder(crl).getIssuer();

            RDN cn = x500name.getRDNs(BCStyle.CN)[0];
            RDN c = x500name.getRDNs(BCStyle.C)[0];

            C = IETFUtils.valueToString(c.getFirst().getValue());
            CN = IETFUtils.valueToString(cn.getFirst().getValue());
            serial = String.valueOf(crl.getVersion());
            base64 = _base64;

            issuer = crl.getIssuerDN().getName();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            lastUpdate = df.format(crl.getThisUpdate());
            nextUpdate = df.format(crl.getNextUpdate());


        } catch (CRLException e) {
            e.printStackTrace();

        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }
}