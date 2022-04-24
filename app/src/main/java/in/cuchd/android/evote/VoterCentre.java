package in.cuchd.android.evote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import in.cuchd.android.database.VoterCursorWrapper;
import in.cuchd.android.database.VoterDbSchema.VoterTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import in.cuchd.android.database.VoterBaseHelper;

public class VoterCentre
{
    private static VoterCentre sVoterCentre;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private List<Voter> mVoters;

    public static VoterCentre getVoterCentre(Context context)
    {
        if (sVoterCentre == null) sVoterCentre = new VoterCentre(context);
        return sVoterCentre;
    }

    private VoterCentre(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new VoterBaseHelper(mContext).getWritableDatabase();

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

    private VoterCursorWrapper queryVoters(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(VoterTable.NAME, null, whereClause, whereArgs, null, null, null);

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
        mDatabase.insert(VoterTable.NAME, null, values);
    }

    public void updateCrime(Voter voter)
    {
        String uuidString = voter.getId().toString();
        ContentValues values = getContentValues(voter);
        mDatabase.update(VoterTable.NAME, values, VoterTable.Cols.UUID + " = ?", new String[] { uuidString });
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

        return values;
    }
}
