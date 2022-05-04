package eu.jrc.vdsd;

public class VDSD_Barcode {
    public VDSD_Barcode_Header header = new VDSD_Barcode_Header();
    public VDSD_Barcode_Message message = new VDSD_Barcode_Message();
    public VDSD_Barcode_Signature signature = new VDSD_Barcode_Signature();
    public VDSD_Barcode_Certificate certificate = new VDSD_Barcode_Certificate();
    public String rawBarcode = "";
    public String latinBarcode = "";
    public boolean isValid;
    public boolean isRevoked;
    public boolean isSupported = false;
    public boolean isVerified = false;
    public long duration = 0;
    public long dbLookUp = 0;
    public long signatureVerification = 0;
    public int hex_dec; //0 hex 1 dec
    public long total_duration = 0;
    public long readTime = 0;
    public String error = "None";

    String getMessage(int end)
    {
        return rawBarcode.substring(0,end);
    }
}
