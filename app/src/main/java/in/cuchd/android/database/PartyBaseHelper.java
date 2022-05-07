package in.cuchd.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PartyBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "partyBase.db";

    public PartyBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + PartyDbSchema.PartyTable.NAME + "(" +
                PartyDbSchema.PartyTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PartyDbSchema.PartyTable.Cols.NAME + " TEXT NOT NULL, " +
                PartyDbSchema.PartyTable.Cols.VOTE_COUNT + " TEXT NOT NULL" +
                ")");

        db.execSQL("INSERT INTO " + PartyDbSchema.PartyTable.NAME +
                "(" + PartyDbSchema.PartyTable.Cols.NAME + ", " +
                PartyDbSchema.PartyTable.Cols.VOTE_COUNT + ") VALUES " +
                "(\"BJP\",0), " +
                "(\"INC\",0), " +
                "(\"BSP\",0), " +
                "(\"CPI-M\",0);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
