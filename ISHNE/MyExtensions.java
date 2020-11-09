package ca.uvic.ISHNE;

import java.util.Date;
import  java.sql.Timestamp;



public class MyExtensions
{

    /// Convert string to char array
    public static void set(char[] ch, String value)
    {
        if (value == null || value.length() == 0)
        {
            throw new NullPointerException("The variable is null or length 0");
        }
        else if (value.length() > ch.length)
        {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < value.length(); i++)
        {
            ch[i] = value.charAt(i);
        }
    }


    /// Convert integer to char array
    public static void set(char[] ch, int val)
    {
        Integer value = val;
        if (value < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        String ID = value.toString();
        MyExtensions.set(ch, ID);
    }



    /// Convert string to short array
    public static void setResolutions(short[] ch, String value)
    {
        String[] resolutions = value.split("");
        for (int i = 0; i < resolutions.length; i++)
        {
            ch[i] = Short.parseShort(resolutions[i]);
        }
    }


    /// Set source array default with '-9'
    public static void setDefault(short[] src)
    {
        if (src == null || src.length == 0)
        {
            throw new NullPointerException("The variable is null or length 0");
        }
        //byte[] value = new byte[2];
        for (int i = 0; i < src.length; i++)
        {
            src[i] = -9;

        }
    }


    /// Convert string date to short array
    public static void setDate(short[] ch, Date value)
    {
        if (value == null)
        {
            throw new NullPointerException();
        }


        ch[0] = (short)value.getDate();
        ch[1] = (short)value.getMonth();
        ch[2] = (short)value.getYear();
    }


    /// Convert DateTime to short array
    public static void setTime(short[] ch, Date value)
    {
        if (value == null)
        {
            throw new NullPointerException();
        }

        ch[0] = (short)value.getHours();
        ch[1] = (short)value.getMinutes();
        ch[2] = (short)value.getSeconds();
    }


    /// Coverte int to short array
    // database : male: 1; female :0
    // ISHNE: unknown: 0; male : 1; female: 2
    public static short setSex(int value)
    {
        short ans;
        if (value == 1)
        {
            ans = 1;
        }
        else if (value == 0)
        {
            ans = 2;
        }
        else {
            ans = 0;
        }

        return ans;
    }
}
