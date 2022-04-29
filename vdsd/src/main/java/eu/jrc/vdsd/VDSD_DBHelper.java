package eu.jrc.vdsd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class VDSD_DBHelper {

	public static final String DATABASE_NAME = "VisibleDigitalSealReader.db";
    public static final String CERT_TABLE_NAME = "certs";
    public static final String CERT_COL_1 = "ID";
    public static final String CERT_COL_2 = "C";
    public static final String CERT_COL_3 = "CN";
    public static final String CERT_COL_4 = "SERIAL";
    public static final String CERT_COL_5 = "CERTIFICATE";



    public static final String BARCODE_TABLE_NAME = "barcode";
    public static final String BARCODE_COL_1 = "ID";
    public static final String BARCODE_COL_2 = "NAME";
    public static final String BARCODE_COL_3 = "SURNAME";
    public static final String BARCODE_COL_4 = "PASSPORT";
    public static final String BARCODE_COL_5 = "DOC_TYPE";
    public static final String BARCODE_COL_6 = "SCAN_DATE";
    public static final String BARCODE_COL_7 = "CERT_REF_SN";
    public static final String BARCODE_COL_8 = "BARCODE";
    public static final String BARCODE_COL_9 = "HEX_DEC";

    public static final String HISTORY_TABLE_NAME = "history";
    public static final String HISTORY_COL_1 = "ID";
    public static final String HISTORY_COL_2 = "VERSION";
    public static final String HISTORY_COL_3 = "ISSUING_COUNTRY";
    public static final String HISTORY_COL_4 = "CERTIFICATEAUTHANDREF";
    public static final String HISTORY_COL_5 = "DOCUMENTISSUEDATE";
    public static final String HISTORY_COL_6 = "SIGNATURECREATIONDATE";
    public static final String HISTORY_COL_7 = "DOCUMENTFEATURE";
    public static final String HISTORY_COL_8 = "DOCUMENTTYPE";
    public static final String HISTORY_COL_9 = "MRZ";
    public static final String HISTORY_COL_10 = "NOFENTRIES";
    public static final String HISTORY_COL_11 = "DURATIONOFSTAY";
    public static final String HISTORY_COL_12 = "PASSPORTNUMBER";
    public static final String HISTORY_COL_13 = "R";
    public static final String HISTORY_COL_14 = "S";
    public static final String HISTORY_COL_15 = "READTIME";
    public static final String HISTORY_COL_16 = "DBLOOKUP";
    public static final String HISTORY_COL_17 = "SIGNATUREVERIFICATION";
    public static final String HISTORY_COL_18 = "DURATION";
    public static final String HISTORY_COL_19 = "TOTALTIME";
    public static final String HISTORY_COL_20 = "BARCODE";

    public static final String CRL_TABLE_NAME = "crls";
    public static final String CRL_COL_1 = "ID";
    public static final String CRL_COL_2 = "C";
    public static final String CRL_COL_3 = "CN";
    public static final String CRL_COL_4 = "LASTUPDATE";
    public static final String CRL_COL_5 = "CRL";
    static VDSD_DBHelper self;

    public VDSD_DBHelper() {


    }

}
