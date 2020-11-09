package ca.uvic.ISHNE;
/// <summary>
/// The variable-length block will consist simply of a stream of ASCII(extended
/// set of 256 characters) characters that any user or manufacturer will use according to his needs.
/// </summary>
public class ISHNEVarLengthBlock
{

    /// a stream of ASCII(extendedset of 256 characters) characters that any user or manufacturer will use according to his needs.
    private char[] reserved;
    private String content;
    public int length;


    public ISHNEVarLengthBlock(String varLenBlockMessage) {
       setContent(varLenBlockMessage);
       setLength();
    }


    public void setContent(String varLenBlockMessage) {
        this.content = varLenBlockMessage;
        if (content == null) {
            reserved = null;
        } else {
            reserved = varLenBlockMessage.toCharArray();
        }
        return;
    }


    public void setLength() {
        if (reserved == null || reserved.length == 0) {
            length = 0;
        } else {
            length = reserved.length;
        }
        return;
    }


    public byte[] serialize()
    {
        byte[] varLengthBlock = new byte[length];
        Utility.copy(reserved, varLengthBlock, 0);
        return varLengthBlock;
    }
}
