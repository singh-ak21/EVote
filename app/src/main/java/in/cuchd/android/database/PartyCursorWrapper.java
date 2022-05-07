package in.cuchd.android.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import in.cuchd.android.evote.Party;

public class PartyCursorWrapper extends CursorWrapper
{
    public PartyCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Party getParty()
    {
        int id = getInt(getColumnIndex(PartyDbSchema.PartyTable.Cols.ID));
        String name = getString(getColumnIndex(PartyDbSchema.PartyTable.Cols.NAME));
        int voteCount = getInt(getColumnIndex(PartyDbSchema.PartyTable.Cols.VOTE_COUNT));

        Party party = new Party(id);

        party.setName(name);
        party.setVoteCount(voteCount);

        return party;
    }
}
