package ca.uvic.ISHNE;

import java.util.Date;

public class EcgTest
{   public int  userId;
    public Date testTime;

    public EcgTest(int uId, Date tTime)
    {
        userId = uId;
        testTime = tTime;
    }
}