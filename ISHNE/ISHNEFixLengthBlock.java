package ca.uvic.ISHNE;

import java.util.Date;

/// The fixed-length (512 bytes) header block.
public class ISHNEFixLengthBlock
{
    /// Fixed-length (512 bytes) of header block.
    public static final int FIX_BLOCK_LEN = 512;
    private static short RESOLUTION_DEFALUT = 4103;

    /// Default sample rate : 250
    private static short SAMPLE_RATE = 250;

    private static short SEX_UNKNOWN = 0;
    private static short SEX_MALE = 1;
    private static short SEX_FEMALE = 2;

    private static short RACE_UNKNOWN = 0;
    private static short RACE_CAUCASIAN = 1;
    private static short RACE_BLACK = 2;
    private static short RACE_ORIENTAL = 3;

    private static short LEAD_I = 5;
    private static short LEAD_II = 6;


    public int varLengthBlockSize;
    public int sampleSizeECG;
    public int offsetVarLengthBlock;
    public int offsetECGBlock;
    private short fileVersion;

    private char[] firstName;
    private String firstNameStr;

    private char[] lastName;
    private String lastNameStr;

    private char[] id;
    private int idInt;

    private short sex;
    private int sexInt;

    private short race;
    // Date of Birth (European: day, month, year)
    private short[] birthDate;
    private Date birthDateDate;
    // (European: day, month, year)
    private short[] recordDate;
    private Date recordDateDate;
    // (European: day, month, year)
    private short[] fileDate;
    private Date fileDateDate;
    // Start time (European: hour (0-231, min, sec)
    private short[] startTime;
    private Date startTimeDate;

    private short nLeads;
    private short[] leadSpec;
    private short[] leadQual;
    private short[] resolution;
    private short pacemaker;
    private char[] recorder;
    private short samplingRate;
    private char[] proprietary;
    private char[] copyright;
    private char[] reserved;

    public int length;


    private void setPatientInfo(PatientInfo pInfo) {
        firstNameStr = pInfo.firstName;
        MyExtensions.set(firstName,firstNameStr);

        lastNameStr = pInfo.lastName;
        MyExtensions.set(lastName,lastNameStr);

        idInt = pInfo.patientId;
        MyExtensions.set(id, idInt);

        sexInt = pInfo.sex;
        sex = MyExtensions.setSex(sexInt);

        birthDateDate = pInfo.birthdate;
        MyExtensions.setDate(birthDate, birthDateDate);

    }

    private void setEcgTest(EcgTest eTest) {
        recordDateDate = eTest.testTime;
        MyExtensions.setDate(recordDate, recordDateDate);

        fileDateDate = eTest.testTime;
        MyExtensions.setDate(fileDate, fileDateDate);

        startTimeDate = eTest.testTime;
        MyExtensions.setTime(startTime, startTimeDate);
    }

    /// Constructor
    public ISHNEFixLengthBlock(PatientInfo pInfo, EcgTest eTest)
    {
        varLengthBlockSize = 0;
        sampleSizeECG = 0;
        offsetVarLengthBlock = 522;
        fileVersion = 1;

        firstName = new char[40];
        firstName[0] = '\0';

        lastName = new char[40];
        lastName[0] = '\0';

        id = new char[20];
        id[0] = '\0';

        sex = SEX_UNKNOWN;
        race = RACE_UNKNOWN;

        birthDate = new short[3];

        recordDate = new short[3];

        fileDate = new short[3];

        startTime = new short[3];

        nLeads = 2;

        leadSpec = new short[12];
        MyExtensions.setDefault(leadSpec);
        leadSpec[0] = LEAD_I;
        leadSpec[1] = LEAD_II;

        leadQual = new short[12];
        MyExtensions.setDefault(leadQual);
        leadQual[0] = 0;
        leadQual[1] = 0;

        resolution = new short[12];
        MyExtensions.setDefault(resolution);
        resolution[0] = RESOLUTION_DEFALUT;
        resolution[1] = RESOLUTION_DEFALUT;

        pacemaker = 0;

        recorder = new char[40];
        recorder[0] = '\0';

        samplingRate = SAMPLE_RATE;

        proprietary = new char[80];
        proprietary[0] = '\0';

        copyright = new char[80];
        copyright[0] = '\0';

        reserved = new char[88];
        reserved[0] = '\0';

        setPatientInfo(pInfo);
        setEcgTest(eTest);
    }


    public byte[] serialize()
    {
        byte[] fixLengthBlcok = new byte[ISHNEFixLengthBlock.FIX_BLOCK_LEN];

        int desIdx = 0;
        desIdx = Utility.copy(varLengthBlockSize, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(sampleSizeECG, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(offsetVarLengthBlock, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(offsetECGBlock, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(fileVersion, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(firstName, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(lastName, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(id, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(sex, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(race, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(birthDate, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(recordDate, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(fileDate, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(startTime, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(nLeads, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(leadSpec, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(leadQual, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(resolution, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(pacemaker, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(recorder, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(samplingRate, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(proprietary, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(copyright, fixLengthBlcok, desIdx);
        desIdx = Utility.copy(reserved, fixLengthBlcok, desIdx);
        length = desIdx;

        return fixLengthBlcok;
    }
}