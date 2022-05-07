package in.cuchd.android.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import in.cuchd.android.evote.Voter;
import in.cuchd.android.database.VoterDbSchema.VoterTable;

public class VoterCursorWrapper extends CursorWrapper
{
    public VoterCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Voter getVoter()
    {
        String uuidString = getString(getColumnIndex(VoterTable.Cols.UUID));
        long aadhaar = getLong(getColumnIndex(VoterTable.Cols.AADHAAR_NUMBER));
        String name = getString(getColumnIndex(VoterTable.Cols.NAME));
        long dateOfBirth = getLong(getColumnIndex(VoterTable.Cols.DATE_OF_BIRTH));
        long phone = getLong(getColumnIndex(VoterTable.Cols.PHONE));
        String email = getString(getColumnIndex(VoterTable.Cols.EMAIL));
        String password = getString(getColumnIndex(VoterTable.Cols.PASSWORD));
        int partyId = getInt(getColumnIndex(VoterTable.Cols.PARTY_ID));

        Voter voter = new Voter(UUID.fromString(uuidString));

        voter.setAadhaar(aadhaar);
        voter.setName(name);
        voter.setDateOfBirth(new Date(dateOfBirth));
        voter.setPhone(phone);
        voter.setEmail(email);
        voter.setPassword(password);
        voter.setPartyId(partyId);

        return voter;
    }
}
