package eu.jrc.vdsd;

public class VDSD_History_Entry 
{
    public static String VERSION;
    public static String ISSUING_COUNTRY;
    public static String CERTIFICATEAUTHANDREF;
    public static String DOCUMENTISSUEDATE;
    public static String SIGNATURECREATIONDATE;
    public static String DOCUMENTFEATURE;
    public static String DOCUMENTTYPE;
    public static String MRZ;
    public static String NOFENTRIES;
    public static String DURATIONOFSTAY;
    public static String PASSPORTNUMBER;
    public static String R;
    public static String S;
    public static long READTIME;
    public static long DBLOOKUP;
    public static long SIGNATUREVERIFICATION;
    public static long DURATION;
    public static long TOTALTIME;
    public static String BARCODE;
    
    VDSD_History_Entry(VDSD_Barcode barcode)
    {
    	VERSION = barcode.header.Version;
    	ISSUING_COUNTRY = barcode.header.IssuingCountry;
    	CERTIFICATEAUTHANDREF = barcode.header.CertificateAuthorityAndReference;
    	DOCUMENTISSUEDATE = barcode.header.DocumentIssueDate;
    	SIGNATURECREATIONDATE = barcode.header.SignatureCreationDate;
    	DOCUMENTFEATURE = barcode.header.DocumentFeatureDefinitionReference;
    	DOCUMENTTYPE = barcode.header.DocumentTypeCategory;
    	MRZ = barcode.message.MRZ.getMRZ();
    	NOFENTRIES = barcode.message.nOfEntries;
    	DURATIONOFSTAY = barcode.message.DurationOfStay.getDate();
    	PASSPORTNUMBER = barcode.message.PassportNumber;
    	R = barcode.signature.r;
    	S = barcode.signature.s;
    	READTIME = barcode.readTime;
    	DBLOOKUP = barcode.dbLookUp;
    	SIGNATUREVERIFICATION = barcode.signatureVerification;
    	DURATION = barcode.duration;
    	TOTALTIME = barcode.total_duration;
    	BARCODE = barcode.rawBarcode;
    }
    
    String toCSVString()
    {
    	String CSV = "\""+VERSION + "\"," +"\""+ISSUING_COUNTRY + "\","+"\""+CERTIFICATEAUTHANDREF + "\","+"\""+DOCUMENTISSUEDATE + "\","+"\""+SIGNATURECREATIONDATE + "\","+"\""+DOCUMENTFEATURE + "\","+"\""+DOCUMENTTYPE + "\","
                +"\""+MRZ + "\"," + "\""+NOFENTRIES + "\"," +"\""+DURATIONOFSTAY + "\"," +"\""+PASSPORTNUMBER + "\"," +"\""+R + "\"," + "\""+S + "\","
                +"\""+READTIME/1000000 + "\","+ "\""+DBLOOKUP/1000000 + "\","+ "\""+SIGNATUREVERIFICATION/1000000 + "\","+ "\""+DURATION/1000000 + "\","+ "\""+TOTALTIME/1000000 + "\"," + "\""+BARCODE+ "\"";
    	
    	return CSV;
    }
}
