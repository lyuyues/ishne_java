package ca.uvic.ISHNE;

import java.nio.ByteBuffer;

public class Utility
{
    /// <summary>
    /// Fill header Block with source
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <param name="len"></param>
    /// <returns></returns>
    public static int copy(byte[] src, byte[] dest, int offset)
    {
        if (src == null || dest == null)
        {
            throw new NullPointerException();
        }
        else if (offset < 0 || offset >= dest.length)
        {
            throw new IndexOutOfBoundsException();
        }

        int pos = offset;
        for (int i = 0; i < src.length && pos < dest.length; i++, pos++)
        {
            dest[pos] = src[i];
        }
        return pos;
    }

    /// <summary>
    /// Fill header Block with source
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <returns></returns>
    public static int copy(char[] src, byte[] dest, int offset)
    {
        return copy(new String(src).getBytes(), dest, offset);
    }

    /// <summary>
    /// Fill header Block with source
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <returns></returns>
    public static int copy(short src, byte[] dest, int offset)
    {
        return copy(new short[] {src}, dest, offset);
    }

    /// <summary>
    /// Fill header Block with source
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <returns></returns>
    public static int copy(short[] src, byte[] dest, int offset)
    {
        return copy(convertToByteArray(src), dest, offset);
    }

    /// <summary>
    /// Fill header Block with source
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <returns></returns>
    public static int copy(int src, byte[] dest, int offset)
    {
        return copy(convertToByteArray(new int[]{src}), dest, offset);
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <returns></returns>
    public static int copy(String src, byte[] dest, int offset)
    {
        return copy(src.getBytes(), dest, offset);
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="src"></param>
    /// <param name="dest"></param>
    /// <param name="offset"></param>
    /// <returns></returns>
    public static int copy(byte[][] src, byte[] dest, int offset)
    {
        if (src == null)
        {
            throw new NullPointerException();
        }

        int idx = offset;
        for (int i = 0; i < src.length && idx < dest.length; i++)
        {
            idx = copy(src[i], dest, idx);
        }
        return Math.min(idx, dest.length);
    }

    /// <summary>
    /// Convert short array to byte array, store in an little_endian form (LSB first)
    /// </summary>
    /// <param name="src"></param>
    /// <returns></returns>
    public static byte[] convertToByteArray(short[] src)
{
    if (src == null)
    {
        throw new NullPointerException();
    }

    byte[] dest = new byte[src.length * 2];
    int idx = 0;
    for (int i = 0; i < src.length; i++)
    {
        byte[] bs = getBytes(src[i]);
        dest[idx++] = bs[1];
        dest[idx++] = bs[0];
    }
    return dest;
}


    public static byte[] convertToByteArray(int[] src)
    {
        if (src == null)
        {
            throw new NullPointerException();
        }

        byte[] dest = new byte[src.length * 4];
        int idx = 0;
        for (int i = 0; i < src.length; i++)
        {
            byte[] bs = getBytes(src[i]);
            dest[idx++] = bs[3];
            dest[idx++] = bs[2];
            dest[idx++] = bs[1];
            dest[idx++] = bs[0];
        }



        return dest;
    }

    public static byte[] getBytes(int src) {
        return ByteBuffer.allocate(4).putInt(src).array();
    }

    public static byte[] getBytes(short src) {
        return ByteBuffer.allocate(2).putShort(src).array();
    }
}
