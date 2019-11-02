package android.bignerdranch.com.database;


import android.bignerdranch.com.Checkin;
import android.bignerdranch.com.database.CheckinDbSchema.CheckinTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CheckinCursorWrapper extends CursorWrapper {
    public CheckinCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Checkin getCrime() {
        String uuidString = getString(getColumnIndex(CheckinTable.Cols.UUID));
        String title = getString(getColumnIndex(CheckinTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CheckinTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CheckinTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CheckinTable.Cols.SUSPECT));

        Checkin crime = new Checkin(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;
    }

}
