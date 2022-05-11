package in.cuchd.android.database;

import android.database.Cursor;
import android.database.CursorWrapper;

public class VoteResultCursorWrapper extends CursorWrapper
{
    public VoteResultCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public int getResult()
    {
        int result = getInt(getColumnIndex(VoteResultDbSchema.VoteResultTable.Cols.IS_RESULT_DECLARED));
        return result;
    }
}
