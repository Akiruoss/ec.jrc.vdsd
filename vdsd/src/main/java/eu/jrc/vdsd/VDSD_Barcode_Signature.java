package eu.jrc.vdsd;

public class VDSD_Barcode_Signature {
	 public String r;
	    public String s;

	    String toDerFormat()
	    {
	        //0x30 b1 0x02 b2 (r) 0x02 b3 (s)
	        String ans1R = isNegative(r);
	        String ans1S = isNegative(s);

	        String b2 = "02" + encodeLength(ans1R.length()/2) + ans1R;
	        String b3 = "02" + encodeLength(ans1S.length()/2) + ans1S;
	        String b =  b2 + b3;
	        String a = encodeLength(b.length()/2);
	        return "30" + a + b;
	    }

	    String isNegative(String signature)
	    {
	        String s = Integer.parseInt(signature.substring(0,2), 16) >= 128 ? "00" + signature : signature;
	        return s;
	    }

	    private static String encodeLength(int l)
	    {
	        String length = "";
	        if (l <= 127)
	            length = Integer.toHexString(l);
	        else
	        {
	            String hexLgt = Integer.toHexString(l);
	            if (hexLgt.length() % 2 !=0)
	            {
	                hexLgt = "0"+hexLgt;
	            }
	            int lgtlgt = hexLgt.length()/2;
	            lgtlgt += 0x80;
	            String hexLgtlgt = Integer.toHexString(lgtlgt);
	            length = hexLgtlgt + hexLgt;

	        }
	        return length;
	    }

	}
