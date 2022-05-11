package in.cuchd.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static in.cuchd.android.database.VoteResultDbSchema.VoteResultTable;

public class VoteResultBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "voteResult.db";

    public VoteResultBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + VoteResultTable.NAME + "(" +
                VoteResultTable.Cols.IS_RESULT_DECLARED + " INTEGER " +
                ")"
        );

        db.execSQL("INSERT INTO " + VoteResultTable.NAME +
                "(" + VoteResultTable.Cols.IS_RESULT_DECLARED + ") VALUES " +
                "(0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
