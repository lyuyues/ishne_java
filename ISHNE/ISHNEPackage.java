package ca.uvic.ISHNE;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.File;

public class ISHNEPackage
{

    /// MagicNumber 8 bytes + CheckSum 2 bytes
    private static int MAGICNUM_CRC_LEN = 10;
    private static String MAGIC_NUMBER = "ISHNE1.0";
    /// 8 bytes
    public String magicNumber;
    /// 2 bytes
    public byte[] checkSum;
    /// 512 + var bytes
    public ISHNEHeader header;
    /// ECG test data
    public byte[][] data;

    /// Constructor
    public ISHNEPackage(ArrayList<String> rawPaths, PatientInfo pInfo, EcgTest eTest, String outPath, String varLenBlockMessage) throws Exception
    {
        magicNumber = MAGIC_NUMBER;
        header = new ISHNEHeader(pInfo, eTest, varLenBlockMessage);

        readDataFromFiles(rawPaths);
        checkSum = calculateCheckSum();
        writeToFile(outPath);

    }

    public ISHNEPackage(byte[][] content, PatientInfo pInfo, EcgTest eTest, String outPath, String varLenBlockMessage) throws Exception
    {
        magicNumber = MAGIC_NUMBER;
        header = new ISHNEHeader(pInfo, eTest, varLenBlockMessage);

        readDataFrombytes(content);
        checkSum = calculateCheckSum();
        writeToFile(outPath);
    }


    /**
     *
     * @param content
     */
    public void readDataFrombytes (byte[][] content) {
        int length = 0;
        data = new byte[content.length][];

        for (int i = 0; i < content.length; i++) {
            byte[] rawData = content[i];
            int samples = (int) (rawData.length / 5);
            length += samples;
            if (samples != 0) {
                data[i] = new byte[samples * 4];
            }

            int outputIdx = 0;
            for (int j = 0; j < 5 * samples; j++) {
                if (j % 5 == 0)
                    continue;
                else if (j % 5 == 1 || j % 5 == 3) {
                    data[i][outputIdx + 1] = rawData[j];
                } else {
                    data[i][outputIdx] = rawData[j];
                    outputIdx += 2;
                }
            }
        }
        header.fixLengthBlock.sampleSizeECG = length;
        header.fixLengthBlock.offsetECGBlock = MAGICNUM_CRC_LEN + header.length;
    }


    /**
     * Read Files into continues byte array, if anyone of the files fails then the all process will fail
     * @param FilePaths
     */
    public void readDataFromFiles(ArrayList<String> FilePaths) throws Exception
    {
        data = new byte[FilePaths.size()][];
        int length = 0;

        for (int i = 0; i < FilePaths.size(); i++)
        {
            byte[] rawData;
            FileInputStream fs;
            File file;
            try
            {
                //rawData = Files.readAllBytes(path);
                file = new File(FilePaths.get(i));
                fs = new FileInputStream(file);
                rawData = new byte[(int) file.length()];
                fs.read(rawData);
                fs.close();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage() + "\n Cannot open file.");
                throw e;
            }

            int samples = (int)(rawData.length / 5);
            length += samples;
            if (samples != 0)
            {
                data[i] = new byte[samples * 4];
            }

            int outputIdx = 0;
            for (int j = 0; j < 5 * samples; j++)
            {
                if (j % 5 == 0)
                    continue;
                else if (j % 5 == 1 || j % 5 == 3)
                {
                    data[i][outputIdx + 1] = rawData[j];
                }
                else
                {
                    data[i][outputIdx] = rawData[j];
                    outputIdx += 2;
                }
            }
        }

        header.fixLengthBlock.sampleSizeECG = length;
        header.fixLengthBlock.offsetECGBlock = MAGICNUM_CRC_LEN + header.length;
    }




    public byte[] serialize()
    {
        int len = (int)(header.fixLengthBlock.sampleSizeECG * 4);
        byte[] PackageBlcok = new byte[MAGICNUM_CRC_LEN + (int) header.length + len];
        int desIdx = 0;

        desIdx = Utility.copy(magicNumber, PackageBlcok, desIdx);
        desIdx = Utility.copy(checkSum,PackageBlcok, desIdx);
        desIdx = Utility.copy(header.serialize(), PackageBlcok, desIdx);
        for (byte[] e: data)
        {
            if (e == null)
                continue;
            desIdx = Utility.copy(e, PackageBlcok, desIdx);
        }

        return PackageBlcok;
    }


    public void writeToFile(String outputPath)
    {
        FileOutputStream fs;
        try
        {
            fs = new FileOutputStream(outputPath);
            byte[] pa = serialize();
            fs.write(pa, 0, pa.length);
            fs.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\n Cannot write file.");
            return;
        }
    }


    private byte[] calculateCheckSum()
    {
        int CRCHI = 0xff; // High Byte(most significant) of the 16 - bit CRC
        int CRCLO = 0xff; // Low Byte (least significant) of the 16-bit CRC

        byte[] headerBlock = header.serialize();
        for (int i = 0; i < headerBlock.length; i++)
        {
            int A = headerBlock[i];
            if (A < 0) {
                A += 0x100;
            }
            A = ((A ^ CRCHI) & 0xff);
            CRCHI = A;
            A = (A >> 4);                       // SHIFT A RIGHT FOUR TIMES {ZERO FILL}
            A = ((A ^ CRCHI) & 0xff);           // {I J K L M N O P}
            CRCHI = CRCLO;                      // SWAP CRCHI, CRCLO
            CRCLO = A;
            A = (((A >> 4) | (A << 4)) & 0xff); // ROTATE A LEFT 4 TIMES { M N O P I J K L}
            int B = A;                          // TEMP SAVE
            A = (((A >> 7) | (A << 1)) & 0xff); // ROTATE A LEFT ONCE {N O P I J K L M}
            A = (A & 0x1f);                     // {0 0 0 I J K L M}
            CRCHI = (A ^ CRCHI);
            A = (B & 0xf0);                     // {M N O P 0 0 0 0}
            CRCHI = (A ^ CRCHI);                // CRCHI complete
            B = ((B >> 7) | (B << 1)) & 0xff;  // ROTATE B LEFT ONCE {N O P 0 0 0 0 M}
            B = (B & 0xe0);                     // {N O P 0 0 0 0 0}
            CRCLO = (B ^ CRCLO);                // CRCLO complete
        }

        byte[] checkSum = new byte[2];
        checkSum[0] = (byte)CRCLO;
        checkSum[1] = (byte)CRCHI;
        return checkSum;
    }
}