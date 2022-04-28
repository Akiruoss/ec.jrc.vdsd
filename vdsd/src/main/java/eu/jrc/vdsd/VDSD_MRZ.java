package eu.jrc.vdsd;

public class VDSD_MRZ {
    String MRZ_1r = "";
    String MRZ_2r = "";
    String Name = "";
    String Surname = "";
    String Type = "";;
    String Issuing = "";;
    String Pass_Visa_No = "";
    String Check_Digit = "";
    String Holder_Nationality = "";
    String Holder_Birthday = "";
    String Check_Digit_Birthday = "";
    String Check_Sex = "";
    String Date_Of_Visa_Expiry = "";
    String Check_Of_Expiry = "";
    String Optional_Data = "";
    boolean valid;

    String getMRZ()
    {
        return MRZ_1r.concat(MRZ_2r);
    }

    public VDSD_MRZ(String MRZ, int expectedLength)
    {
        String[] parts = {MRZ.substring(0, expectedLength), MRZ.substring(expectedLength)};
        MRZ_1r = parts[0];
        MRZ_2r = parts[1];

        int start = 0;
        int end = 2;
        Type = MRZ_1r.substring(start, end);

        start = 2;
        end = 5;
        Issuing = MRZ_1r.substring(start, end).replaceAll("<", "");;

        start = end;
        end = MRZ_1r.substring(start).indexOf("<<") + start;
        Surname = MRZ_1r.substring(start, end).replaceAll("<", " ");

        start = end + 2;
        end = MRZ_1r.length();
        Name = MRZ_1r.substring(start, end).replaceAll("<<", "").replaceAll("<", " ");

        start = 0;
        end = 9;
        Pass_Visa_No = MRZ_2r.substring(start, end).replace('<', ' ');

        start = end;
        end = start + 1;
        Check_Digit = MRZ_2r.substring(start, end);

        start = end;
        end = start + 3;
        Holder_Nationality = MRZ_2r.substring(start, end);

        start = end;
        end = start + 6;
        Holder_Birthday = MRZ_2r.substring(start, end);

        start = end;
        end = start + 1;
        Check_Digit_Birthday = MRZ_2r.substring(start, end);

        start = end;
        end = start + 1;
        Check_Sex = MRZ_2r.substring(start, end);

        start = end;
        end = start + 6;
        Date_Of_Visa_Expiry = MRZ_2r.substring(start, end);

        start = end;
        end = start + 1;
        Check_Of_Expiry = MRZ_2r.substring(start, end);

        start = end;
        end = MRZ_2r.length();
        Optional_Data = MRZ_2r.substring(start, end).replaceAll("<", "");

        valid = true;

    }

    String getDateOfVisaExpiry()
    {
        if (Date_Of_Visa_Expiry.length() == 6)
            return Date_Of_Visa_Expiry.substring(4,6) + "/" + Date_Of_Visa_Expiry.substring(2,4) + "/20" + Date_Of_Visa_Expiry.substring(0,2);
        return Date_Of_Visa_Expiry;
    }

    boolean isValid(){
        return valid;
    }
}
