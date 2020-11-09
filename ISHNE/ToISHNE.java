package ca.uvic.ISHNE;

import java.util.ArrayList;
import java.util.Date;

/// Convert the raw data form sensor to ISHNE data format due to mathch the input of data analysis tools.
/// Also, add information into ISHNE format data.
/// The sequential structure of the Standard ISHNE Output File :
/// three blocks of data preceded by a magic number and a checksum calculated over the two blocks of the header.
/// Magic number + CRC + Header( fixed length block + var length block) + ECG data
public class ToISHNE
{
    /// Interface to output standard ISHNE file
    public static void convertToISHNE(ArrayList<String> rawDataPaths, PatientInfo patientInfo, EcgTest ecgTest, String outputPath) throws Exception
    {
        convertToISHNE(rawDataPaths, patientInfo, ecgTest, outputPath, "");
    }


    /// Interface with addition message need to be stored in ISHNE file
    public static void convertToISHNE(ArrayList<String> rawDataPaths, PatientInfo patientInfo, EcgTest ecgTest, String outputPath, String varLengthBlockMessage) throws Exception
    {
        new ISHNEPackage(rawDataPaths, patientInfo, ecgTest, outputPath, varLengthBlockMessage);
    }



    public static void convertToISHNE(byte[][] content, PatientInfo patientInfo, EcgTest ecgTest, String outputPath) throws Exception
    {
        convertToISHNE(content, patientInfo, ecgTest, outputPath, "");
    }

    public static void convertToISHNE(byte[][] content, PatientInfo patientInfo, EcgTest ecgTest, String outputPath, String varLengthBlockMessage) throws Exception
    {
        new ISHNEPackage(content, patientInfo, ecgTest, outputPath, varLengthBlockMessage);
    }


    public static void main(String[] args)
    {
        PatientInfo patientInfo = new PatientInfo(
                123456789,
                "FirstName",
                "LastName",
                 new Date(2020, 1, 2),
                1);

        EcgTest ecgTest = new EcgTest(
                123456780,
                new Date(2011, 3, 16, 6, 45, 13)
        );

        ArrayList<String> files = new ArrayList<String>();
        files.add("../desktop.git/Test/ECG_ISHNE.Tests/rawData/1234.bin");
        String outputPath = "../desktop.git/Test/ECG_ISHNE.Tests/rawData/ISHNE.JAVA";
        String varLengthBlockMessage = "It is var length block content.";

        try {
            convertToISHNE(files, patientInfo, ecgTest, outputPath, varLengthBlockMessage);
        } catch (Exception e){
            System.out.println("Exception" + e.getMessage());
        }
    }

}