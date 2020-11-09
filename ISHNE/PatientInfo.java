package ca.uvic.ISHNE;

import java.util.Date;

public class PatientInfo
{

    public int patientId; // userid  int(10) unsigned  (patient_info)
    public String lastName; // lastname  varchar(20)  (patient_info)
    public String firstName; // firstname varchar(20)  (patient_info)
    public Date birthdate;  //birthday date ( 0000-00-00)  (patient_info)
    public int sex;  // sex int(1), male = 1 female = 0   (patient_info)
    public int race = 0;  // no race



    public PatientInfo(int pId,
                       String pFirstname,
                       String pLastName,
                       Date birth,
                       int pSex,
                       int pRace)
    {
        patientId = pId;
        lastName = pLastName;
        firstName = pFirstname;
        birthdate = birth;
        sex = pSex;
        race = pRace;
    }

    public PatientInfo(int pId,
                       String pFirstname,
                       String pLastName,
                       Date birth,
                       int pSex)
    {
        patientId = pId;
        lastName = pLastName;
        firstName = pFirstname;
        birthdate = birth;
        sex = pSex;
    }
}
