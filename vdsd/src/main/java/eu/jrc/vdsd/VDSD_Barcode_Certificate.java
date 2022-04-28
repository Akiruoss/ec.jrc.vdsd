package eu.jrc.vdsd;

import java.security.Principal;
import java.security.cert.X509Certificate;

public class VDSD_Barcode_Certificate {
    X509Certificate X509 = null;
    String issuer = "";
    String subject = "";
    String issue_date = "";
    String expire_date = "";
    String serial_number = "";

    public String getIssuer()
    {
        if (issuer != "")
        {
            return issuer;
        }
        else if (X509 != null)
        {
            Principal principal =  X509.getIssuerDN();
            issuer = principal.getName().toString();
            return issuer;
        }
        else
            return "";
    }

    public String getSubject()
    {
        if (subject != "")
        {
            return subject;
        }
        else if (X509 != null)
        {
            Principal principal = X509.getSubjectDN();
            subject = principal.getName();
            return subject;
        }
        else
            return "";
    }

    public String getIssue_date()
    {
        if (issue_date != "")
        {
            return issue_date;
        }
        else if (X509 != null)
        {
            issue_date = X509.getNotBefore().toString();
            return issue_date;
        }
        else
            return "";
    }

    public String getExpire_date()
    {
        if (expire_date != "")
        {
            return expire_date;
        }
        else if (X509 != null)
        {
            expire_date = X509.getNotAfter().toString();
            return expire_date;
        }
        else
            return "";
    }

    public String getSerial_number()
    {
        if (serial_number != "")
        {
            return serial_number;
        }
        else if (X509 != null)
        {
            serial_number = X509.getSerialNumber().toString();
            return serial_number;
        }
        else
            return "";

    }
}
