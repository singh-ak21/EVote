package in.cuchd.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.cuchd.android.database.VoterDbSchema.VoterTable;
import in.cuchd.android.database.PartyDbSchema.PartyTable;

public class VoterBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "voterBase.db";

    public VoterBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + VoterTable.NAME + "(" +
                VoterTable.Cols.UUID + " TEXT PRIMARY KEY, " +
                VoterTable.Cols.AADHAAR_NUMBER + " INTEGER NOT NULL UNIQUE, " +
                VoterTable.Cols.NAME + " TEXT NOT NULL, " +
                VoterTable.Cols.DATE_OF_BIRTH + " INTEGER NOT NULL, " +
                VoterTable.Cols.PHONE + " INTEGER NOT NULL, " +
                VoterTable.Cols.EMAIL + " TEXT NOT NULL, " +
                VoterTable.Cols.PASSWORD + " TEXT NOT NULL," +
                VoterTable.Cols.PARTY_ID + " INTEGER, " +
                "FOREIGN KEY (" + VoterTable.Cols.PARTY_ID + ") REFERENCES " +
                PartyTable.NAME + "(" + PartyTable.Cols.ID + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
