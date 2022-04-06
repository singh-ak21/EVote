package in.cuchd.android.evote;

import java.util.Date;
import java.util.UUID;

public class Voter
{
    private UUID mId;
    private long mAadhaar;
    private String mName;
    private Date mDateOfBirth;
    private long mPhone;
    private String mEmail;
    private String password;

    public Voter()
    {
        mId = UUID.randomUUID();
    }

    public UUID getId()
    {
        return mId;
    }

    public long getAadhaar()
    {
        return mAadhaar;
    }

    public void setAadhaar(long aadhaar)
    {
        mAadhaar = aadhaar;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public Date getDateOfBirth()
    {
        return mDateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        mDateOfBirth = dateOfBirth;
    }

    public long getPhone()
    {
        return mPhone;
    }

    public void setPhone(long phone)
    {
        mPhone = phone;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public void setEmail(String email)
    {
        mEmail = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
