package eu.jrc.vdsd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class VDSD_Utils {
    
	static final String[] hashAlgorithm = new String[] {"SHA224withECDSA","SHA256withECDSA","SHA384withECDSA","SHA384withECDSA","SHA512withECDSA","SHA512withECDSA"};

    
	public static String encodeHexString(byte[] byteArray)
	{
		StringBuffer hexStringBuffer = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++)
		{
			hexStringBuffer.append(byteToHex(byteArray[i]));
		}
		return hexStringBuffer.toString();
	}

	public static String byteToHex(byte num) {
		char[] hexDigits = new char[2];
		hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		hexDigits[1] = Character.forDigit((num & 0xF), 16);
		return new String(hexDigits);
	}

	public static byte hexToByte(String hexString) {
		int firstDigit = toDigit(hexString.charAt(0));
		int secondDigit = toDigit(hexString.charAt(1));
		return (byte) ((firstDigit << 4) + secondDigit);
	}

	private static int toDigit(char hexChar) {
		int digit = Character.digit(hexChar, 16);
		if(digit == -1) {
			throw new IllegalArgumentException(
					"Invalid Hexadecimal Character: "+ hexChar);
		}
		return digit;
	}
	

    static boolean afterToday(String input)
    {
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            Date today = format.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            Date date = format.parse(input);
            if(date.after(today))
                return true;
            else
                return false;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return true;
        }

    }

    public static String StringToHex(String certSerial) {
        BigInteger bigInt = new BigInteger(certSerial);
        return bigInt.toString(16);
    }
    
    static boolean isValidCountry(String country)
    {
        String[] countries = new String[] {"ABW", "AFG", "AGO", "AIA", "ALA", "ALB", "AND", "ARE", "ARG", "ARM", "ASM", "ATA", "ATF", "ATG", "AUS", "AUT", "AZE", "BDI",
                "BEL", "BEN", "BES", "BFA", "BGD", "BGR", "BHR", "BHS", "BIH", "BLM", "BLR", "BLZ", "BMU", "BOL", "BRA", "BRB", "BRN", "BTN",
                "BVT", "BWA", "CAF", "CAN", "CCK", "CHE", "CHL", "CHN", "CIV", "CMR", "COD", "COG", "COK", "COL", "COM", "CPV", "CRI", "CUB",
                "CUW", "CXR", "CYM", "CYP", "CZE", "DEU", "DJI", "DMA", "DNK", "DOM", "DZA", "ECU", "EGY", "ERI", "ESH", "ESP", "EST", "ETH",
                "FIN", "FJI", "FLK", "FRA", "FRO", "FSM", "GAB", "GBR", "GEO", "GGY", "GHA", "GIB", "GIN", "GLP", "GMB", "GNB", "GNQ", "GRC",
                "GRD", "GRL", "GTM", "GUF", "GUM", "GUY", "HKG", "HMD", "HND", "HRV", "HTI", "HUN", "IDN", "IMN", "IND", "IOT", "IRL", "IRN",
                "IRQ", "ISL", "ISR", "ITA", "JAM", "JEY", "JOR", "JPN", "KAZ", "KEN", "KGZ", "KHM", "KIR", "KNA", "KOR", "KWT", "LAO", "LBN",
                "LBR", "LBY", "LCA", "LIE", "LKA", "LSO", "LTU", "LUX", "LVA", "MAC", "MAF", "MAR", "MCO", "MDA", "MDG", "MDV", "MEX", "MHL",
                "MKD", "MLI", "MLT", "MMR", "MNE", "MNG", "MNP", "MOZ", "MRT", "MSR", "MTQ", "MUS", "MWI", "MYS", "MYT", "NAM", "NCL", "NER",
                "NFK", "NGA", "NIC", "NIU", "NLD", "NOR", "NPL", "NRU", "NZL", "OMN", "PAK", "PAN", "PCN", "PER", "PHL", "PLW", "PNG", "POL",
                "PRI", "PRK", "PRT", "PRY", "PSE", "PYF", "QAT", "REU", "ROU", "RUS", "RWA", "SAU", "SDN", "SEN", "SGP", "SGS", "SHN", "SJM",
                "SLB", "SLE", "SLV", "SMR", "SOM", "SPM", "SRB", "SSD", "STP", "SUR", "SVK", "SVN", "SWE", "SWZ", "SXM", "SYC", "SYR", "TCA",
                "TCD", "TGO", "THA", "TJK", "TKL", "TKM", "TLS", "TON", "TTO", "TUN", "TUR", "TUV", "TWN", "TZA", "UGA", "UKR", "UMI", "URY",
                "USA", "UZB", "VAT", "VCT", "VEN", "VGB", "VIR", "VNM", "VUT", "WLF", "WSM", "YEM", "ZAF", "ZMB", "ZWE", "UTO"};
        return Arrays.asList(countries).contains(country);

    }

	public static VDSD_Cert loadCertificate(String certFile) throws CertificateException, IOException, NoSuchProviderException  {
        VDSD_Cert cert = null;
        
        certFile = certFile.startsWith("-----BEGIN CERTIFICATE-----\n") ? certFile : "-----BEGIN CERTIFICATE-----\n" + certFile;
        certFile = certFile.endsWith("-----END CERTIFICATE-----") ? certFile : certFile + "\n-----END CERTIFICATE-----";
        
        CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
        InputStream txtCertStream = new ByteArrayInputStream(certFile.getBytes());
        X509Certificate x509cert  = (X509Certificate)cf.generateCertificate(txtCertStream);
        cert = new VDSD_Cert(x509cert);
        return cert;		
	}

	public static boolean isValidSignaturePublicKey(PublicKey pubKey, String message, String signature) throws UnsupportedEncodingException, GeneralSecurityException 
	{
        for (String algo : hashAlgorithm)
        {
            byte[] realSig = decodeHexString(signature);
            Signature ecdsaVerify = Signature.getInstance(algo, new BouncyCastleProvider());
            ecdsaVerify.initVerify(pubKey);
            ecdsaVerify.update(Hex.decode(message));
            boolean verify = ecdsaVerify.verify(realSig);
            if (verify == true)
                return true;
        }
        return false;
	}
	
	public static final String deriveCurveName(org.bouncycastle.jce.spec.ECParameterSpec ecParameterSpec) throws GeneralSecurityException{
	    for (@SuppressWarnings("rawtypes")
	           Enumeration names = ECNamedCurveTable.getNames(); names.hasMoreElements();){
	        final String name = (String)names.nextElement();

	        final X9ECParameters params = ECNamedCurveTable.getByName(name);

	        if (params.getN().equals(ecParameterSpec.getN())
	            && params.getH().equals(ecParameterSpec.getH())
	            && params.getCurve().equals(ecParameterSpec.getCurve())
	            && params.getG().equals(ecParameterSpec.getG())){
	            return name;
	        }
	    }

	    throw new GeneralSecurityException("Could not find name for curve");
	}
	
	public static final String deriveCurveName(PublicKey publicKey) throws GeneralSecurityException{
	    if(publicKey instanceof java.security.interfaces.ECPublicKey){
	        final java.security.interfaces.ECPublicKey pk = (java.security.interfaces.ECPublicKey) publicKey;
	        final ECParameterSpec params = pk.getParams();
	        return deriveCurveName(EC5Util.convertSpec(params));
	    } else if(publicKey instanceof org.bouncycastle.jce.interfaces.ECPublicKey){
	        final org.bouncycastle.jce.interfaces.ECPublicKey pk = (org.bouncycastle.jce.interfaces.ECPublicKey) publicKey;
	        return deriveCurveName(pk.getParameters());
	    } else throw new IllegalArgumentException("Can only be used with instances of ECPublicKey (either jce or bc implementation)");
	}

	
	static String getAlgoFromCurveName(String curveName)
    {
        if (curveName.contains("224"))
            return "SHA224withECDSA";
        else if (curveName.contains("256"))
            return "SHA256withECDSA";
        else if (curveName.contains("320"))
            return "SHA384withECDSA";
        else if (curveName.contains("384"))
            return "SHA384withECDSA";
        else if (curveName.contains("512"))
            return"SHA512withECDSA";
        else
            return "SHA512withECDSA";
    }
	
    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    static X509CRL loadCRL(String txtCRL) throws CertificateException, CRLException 
    {
    	txtCRL = txtCRL.startsWith("-----BEGIN X509 CRL-----\n") ? txtCRL : "-----BEGIN X509 CRL-----\n" + txtCRL;
    	txtCRL = txtCRL.endsWith("-----END X509 CRL-----") ? txtCRL : txtCRL + "\n-----END X509 CRL-----";
        CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
        InputStream txtCRLStream = new ByteArrayInputStream(txtCRL.getBytes());
        return (X509CRL)cf.generateCRL(txtCRLStream);
    }
    
}
