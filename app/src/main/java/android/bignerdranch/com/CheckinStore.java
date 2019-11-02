package android.bignerdranch.com;

import android.bignerdranch.com.database.CheckinBaseHelper;
import android.bignerdranch.com.database.CheckinCursorWrapper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.bignerdranch.com.database.CheckinDbSchema.CheckinTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckinStore {
    private static CheckinStore sCheckinStore;
    //private List<Checkin> mCrimes; // deleted
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CheckinStore get(Context context){
        if (sCheckinStore == null) {
            sCheckinStore = new CheckinStore(context);
        }
        return sCheckinStore;
    }
    private CheckinStore(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CheckinBaseHelper(mContext)
                .getWritableDatabase();
        //mCrimes = new ArrayList<>();  //deleted

    }

    public void addCrime(Checkin c) {
        //mCrimes.add(c); //deleted
        ContentValues values = getContentValues(c);
        mDatabase.insert(CheckinTable.NAME, null, values);

    }
    public List<Checkin> getCrimes() {
        //return mCrimes;//deleted
        List<Checkin> crimes = new ArrayList<>();
        CheckinCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;

    }
    public Checkin getCrime(UUID id){
       /* for (Checkin crime : mCrimes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }*/
        CheckinCursorWrapper cursor = queryCrimes(
                CheckinTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

    }

    public File getPhotoFile(Checkin crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public void updateCrime(Checkin crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CheckinTable.NAME, values,
                CheckinTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private CheckinCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CheckinTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CheckinCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Checkin crime) {
        ContentValues values = new ContentValues();
        values.put(CheckinTable.Cols.UUID, crime.getId().toString());
        values.put(CheckinTable.Cols.TITLE, crime.getTitle());
        values.put(CheckinTable.Cols.DATE, crime.getDate().getTime());
        values.put(CheckinTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CheckinTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }

}
