package in.cuchd.android.evote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import in.cuchd.android.database.PartyBaseHelper;
import in.cuchd.android.database.PartyCursorWrapper;
import in.cuchd.android.database.PartyDbSchema.PartyTable;
import in.cuchd.android.database.VoteResultBaseHelper;
import in.cuchd.android.database.VoteResultCursorWrapper;
import in.cuchd.android.database.VoteResultDbSchema.VoteResultTable;
import in.cuchd.android.database.VoterBaseHelper;
import in.cuchd.android.database.VoterCursorWrapper;
import in.cuchd.android.database.VoterDbSchema.VoterTable;

public class VoterCentre
{
    private static final String TAG = "VoterCentre";

    private static VoterCentre sVoterCentre;

    private Context mContext;
    private SQLiteDatabase mVoterDatabase;
    private SQLiteDatabase mPartyDatabase;
    private SQLiteDatabase mVoteResultDatabase;

    private List<Voter> mVoters;

    public static VoterCentre getVoterCentre(Context context)
    {
        if (sVoterCentre == null) sVoterCentre = new VoterCentre(context);
        return sVoterCentre;
    }

    private VoterCentre(Context context)
    {
        mContext = context.getApplicationContext();

        mPartyDatabase = new PartyBaseHelper(mContext).getWritableDatabase();
        mVoterDatabase = new VoterBaseHelper(mContext).getWritableDatabase();
        mVoteResultDatabase = new VoteResultBaseHelper(mContext).getWritableDatabase();

        mVoters = new ArrayList<>();
    }

    public List<Voter> getVoters()
    {
        List<Voter> voters = new ArrayList<>();
        VoterCursorWrapper cursor = queryVoters(null, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                voters.add(cursor.getVoter());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return voters;
    }

    private PartyCursorWrapper queryParties(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mPartyDatabase.query(PartyTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new PartyCursorWrapper(cursor);
    }

    public Party getParty(String partyId)
    {
        PartyCursorWrapper cursor =
                queryParties(PartyTable.Cols.ID + " = ?", new String[]{partyId});

        try
        {
            if (cursor.getCount() == 0) return null;

            cursor.moveToFirst();
            return cursor.getParty();
        }
        finally
        {
            cursor.close();
        }
    }

    private void updateParty(Party party)
    {
        String id = String.valueOf(party.getId());
        ContentValues values = getContentValues(party);

        mPartyDatabase.update(PartyTable.NAME, values, PartyTable.Cols.ID + " = ?",
                new String[] {id});
    }

    private VoterCursorWrapper queryVoters(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mVoterDatabase.query(VoterTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new VoterCursorWrapper(cursor);
    }

    public Voter getVoter(UUID voterId)
    {
        VoterCursorWrapper cursor = queryVoters(VoterTable.Cols.UUID + " = ?", new String[]{voterId.toString()});

        try
        {
            if (cursor.getCount() == 0) return null;

            cursor.moveToFirst();
            return cursor.getVoter();
        }
        finally
        {
            cursor.close();
        }
    }

    public Voter getVoter(String aadhaar)
    {
        VoterCursorWrapper cursor = queryVoters(VoterTable.Cols.AADHAAR_NUMBER + " = ?",
                new String[]{aadhaar});

        try
        {
            if (cursor.getCount() == 0) return null;

            cursor.moveToFirst();
            return cursor.getVoter();
        }
        finally
        {
            cursor.close();
        }
    }

    public Voter getVoter(String aadhaar, String password)
    {
        VoterCursorWrapper cursor = queryVoters(VoterTable.Cols.AADHAAR_NUMBER + " = ? " +
                "AND " + VoterTable.Cols.PASSWORD + " = ?", new String[]{aadhaar, password});

        try
        {
            if (cursor.getCount() == 0) return null;

            cursor.moveToFirst();
            return cursor.getVoter();
        }
        finally
        {
            cursor.close();
        }
    }

    public void addVoter(Voter voter)
    {
        ContentValues values = getContentValues(voter);
        mVoterDatabase.insert(VoterTable.NAME, null, values);
    }

    public void updateVoter(Voter voter)
    {
        String uuidString = voter.getId().toString();
        ContentValues values = getContentValues(voter);
        mVoterDatabase.update(VoterTable.NAME, values, VoterTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    public void deleteVoter(Voter voter)
    {
        mVoterDatabase.delete(VoterTable.NAME,VoterTable.Cols.UUID + " = ?", new String[]{voter.getId().toString()});
    }

    private static ContentValues getContentValues(Voter voter)
    {
        ContentValues values = new ContentValues();

        values.put(VoterTable.Cols.UUID, voter.getId().toString());
        values.put(VoterTable.Cols.AADHAAR_NUMBER, voter.getAadhaar());
        values.put(VoterTable.Cols.NAME, voter.getName());
        values.put(VoterTable.Cols.DATE_OF_BIRTH, voter.getDateOfBirth().getTime());
        values.put(VoterTable.Cols.PHONE, voter.getPhone());
        values.put(VoterTable.Cols.EMAIL, voter.getEmail());
        values.put(VoterTable.Cols.PASSWORD, voter.getPassword());
        values.put(VoterTable.Cols.PARTY_ID, voter.getPartyId());

        return values;
    }

    private static ContentValues getContentValues(Party party)
    {
        ContentValues values = new ContentValues();

        values.put(PartyTable.Cols.ID, String.valueOf(party.getId()));
        values.put(PartyTable.Cols.NAME, party.getName());
        values.put(PartyTable.Cols.VOTE_COUNT, party.getVoteCount());

        return values;
    }

    public void incrementVote(String partyId)
    {
        Party party = getParty(partyId);

        party.setVoteCount(party.getVoteCount()+1);

        updateParty(party);
    }

    public void decrementVote(String partyId)
    {
        if (partyId.equals("0")) return;
        Party party = getParty(partyId);

        party.setVoteCount(party.getVoteCount()-1);

        updateParty(party);
    }

    public boolean isResultDeclared()
    {
        VoteResultCursorWrapper cursor = new VoteResultCursorWrapper(mVoteResultDatabase.query(VoteResultTable.NAME,
                null, null,
                null, null, null, null));

        try
        {
            if (cursor.getCount() == 0) return false;

            cursor.moveToFirst();
            return cursor.getResult() == 1;
        }
        finally
        {
            cursor.close();
        }
    }

    public Party getWinningParty()
    {
        Cursor cursor = mPartyDatabase.rawQuery(
                "SELECT MAX(" + PartyTable.Cols.VOTE_COUNT + ") FROM " + PartyTable.NAME,
                null);

        int maxVotes;

        try
        {
            if (cursor.getCount() != 1) return null;

            cursor.moveToFirst();
            maxVotes = cursor.getInt(0);
        }
        finally
        {
            cursor.close();
        }

        PartyCursorWrapper partyCursor = new PartyCursorWrapper(mPartyDatabase
                .query(PartyTable.NAME, null,
                        PartyTable.Cols.VOTE_COUNT + " = ? ",
                        new String[]{String.valueOf(maxVotes)},
                        null, null, null));

        try
        {
            if (partyCursor.getCount() != 1) return null;

            partyCursor.moveToFirst();
            return partyCursor.getParty();
        }
        finally
        {
            partyCursor.close();
        }
    }

    public boolean setResultDeclared(boolean isResultDeclared)
    {
        int result = isResultDeclared ? 1 : 0;

        Party party = getWinningParty();
        if (party == null) return false;

        ContentValues values = new ContentValues();
        values.put(VoteResultTable.Cols.IS_RESULT_DECLARED, String.valueOf(result));

        mVoteResultDatabase.update(VoteResultTable.NAME, values, null, null);

        return true;
    }
}
