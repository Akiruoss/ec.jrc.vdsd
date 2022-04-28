package eu.jrc.vdsd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class VDSD_C40 {
    public static String encode(String input)
    {
        String message = new String();

        return message;
    }

    public static String decodeDate(String input)
    {
        String message = new String();

        return message;
    }

    public static String decodeDecimal(String input)
    {
        String s = "";
        try{
            s = Integer.toString(Integer.parseInt(input, 16));
        }
        catch (Exception e)
        {

        }
        return s;
    }

    public static String decodeAlphanum(String input)
    {
        String message = new String();
        List<String> hexsplit = ngrams(2, input);

        for (int i = 0; i < hexsplit.size(); i = i+2)
        {
            int I1 = Integer.parseInt(hexsplit.get(i), 16);
            int I2 = Integer.parseInt(hexsplit.get(i+1), 16);
            if (I1 != 254)
            {
                int V16 = (I1 * 256) + I2;
                int U1 = (V16 - 1) / 1600;
                int U2 = (V16 - (U1 * 1600) - 1) / 40;
                int U3 = (V16 - (U1 * 1600) - (U2 * 40) - 1);
                if (U3 < 3)
                    message = message + fromC40ToChar(U1) + fromC40ToChar(U2);
                else
                    message = message + fromC40ToChar(U1) + fromC40ToChar(U2) + fromC40ToChar(U3);

            }
            else
            {
                message = message + (char)(I2 - 1);
            }
        }


        return message;
    }

    public static String decodeBinary(String input)
    {
        return new BigInteger(input, 16).toString(2);
    }

    boolean isOdd( int val )
    {
        return (val & 0x01) != 0;
    }

    public static char fromC40ToChar(int n)
    {
        if (n <3)
        {
            return '\0';
        }
        else if (n == 3)
        {
            return ' ';
        }
        else if (n < 14)
        {
            return (char)(n+44);
        }
        else
        {
            return (char)(n+51);
        }
    }


    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        for (int i = 0; i < str.length(); i = i + n) {
            if (i + n < str.length())
                ngrams.add(str.substring(i, i + n));
            else
                ngrams.add(str.substring(i, str.length()));
        }
        return ngrams;
    }

}
