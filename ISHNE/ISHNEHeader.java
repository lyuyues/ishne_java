package ca.uvic.ISHNE;


import java.util.ArrayList;

// The main goal of the header is to provide all necessary information on the associated ECG file.
// ISHNE Header( fixed length of 512 bytes block + variable length block )
public class ISHNEHeader
{
    // 512 bytes
    public ISHNEFixLengthBlock fixLengthBlock;
    // var bytes
    public ISHNEVarLengthBlock varLengthBlock;
    public int length;


    /// Constructor
    public ISHNEHeader(PatientInfo pInfo, EcgTest eTest, String varLenBlockMessage)
    {
        this.fixLengthBlock = new ISHNEFixLengthBlock(pInfo, eTest);
        this.varLengthBlock = new ISHNEVarLengthBlock(varLenBlockMessage);

        this.length = ISHNEFixLengthBlock.FIX_BLOCK_LEN + varLengthBlock.length;
        fixLengthBlock.varLengthBlockSize = varLengthBlock.length;

    }


    /// Serialize all elements in ISHNE header into a single byte[]
    public byte[] serialize()
    {
        byte[] headerBlcok = new byte[ISHNEFixLengthBlock.FIX_BLOCK_LEN + varLengthBlock.length];

        int desIdx = 0;

        desIdx = Utility.copy(fixLengthBlock.serialize(), headerBlcok, desIdx);
        if (varLengthBlock.length > 0)
        {
            desIdx = Utility.copy(varLengthBlock.serialize(), headerBlcok, desIdx);
        }
        if (desIdx != length) {
            System.out.println("ISHNE header is not filled correctly");
        }
        return headerBlcok;
    }
}
