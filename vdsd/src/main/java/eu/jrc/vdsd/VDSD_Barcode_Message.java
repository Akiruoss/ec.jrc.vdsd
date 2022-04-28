package eu.jrc.vdsd;

public class VDSD_Barcode_Message {
    public String nOfEntries;
    DurationOfStay DurationOfStay;
    public String PassportNumber ;
    String VisaType;
    String Other;
    VDSD_MRZ MRZ;


}

class DurationOfStay
{
    String msg = "";
    String DD = "";
    String MM = "";
    String YY = "";
    boolean fromMRZ;
    DurationOfStay(String _d)
    {
        msg = _d;
        if (msg.length() == 6)
        {
            DD = Long.toString(Long.parseLong(_d.substring(0,2), 16));
            MM = Long.toString(Long.parseLong(_d.substring(2,4), 16));
            YY = Long.toString(Long.parseLong(_d.substring(4,6), 16));
        }
    }

    String getDate()
    {
        if ((msg.compareToIgnoreCase("ffffff") == 0) || (msg.compareToIgnoreCase("000000") == 0))
            return msg;
        else
        {
            String stay = "";
            if (DD.compareToIgnoreCase("0") != 0)
                stay =  DD + " Day(s) ";
            if (MM.compareToIgnoreCase("0") != 0)
                stay += MM + " Month(s) ";
            if (YY.compareToIgnoreCase("0") != 0)
                stay += YY + " Year(s)";
            return stay;
            //return DD + " Day(s) " + MM + "Month(s) " + YY + "Year(s)";
        }

    }

    int length()
    {
        return msg.length();
    }
}