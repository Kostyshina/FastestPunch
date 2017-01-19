package by.stylesoft.fastestpunch.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import by.stylesoft.fastestpunch.data.FastestPunchContract.*;

/**
 * Created by User on 19.01.2017.
 */

public class FastestPunchDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = FastestPunchDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "FastestPunchDB";
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor of {@link FastestPunchDbHelper}.
     *
     * @param context application context
     */
    public FastestPunchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String COMMUNITY_TABLE_SQL = "CREATE TABLE " + CommunityEntry.TABLE_NAME + "(" +
                CommunityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CommunityEntry.COLUMN_LOGIN + " TEXT NOT NULL, " +
                CommunityEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";
        final String PARAMETERS_TABLE_SQL = "CREATE TABLE "+ ParametersEntry.TABLE_NAME + "(" +
                ParametersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ParametersEntry.COLUMN_PUNCH_TYPE + " INTEGER NOT NULL, " +
                ParametersEntry.COLUMN_HAND + " TEXT NOT NULL, " +
                ParametersEntry.COLUMN_GLOVES + " TEXT NOT NULL, " +
                ParametersEntry.COLUMN_GLOVES_WEIGHT + " INTEGER, " +
                ParametersEntry.COLUMN_MOVES + " TEXT NOT NULL);";
        final String HISTORY_TABLE_SQL = "CREATE TABLE " + HistoryEntry.TABLE_NAME +"(" +
                HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HistoryEntry.COLUMN_PARAMETERS + " TEXT REFERENCES Parameters NOT NULL, " +
                HistoryEntry.COLUMN_PUNCH_SPEED + " REAL, " +
                HistoryEntry.COLUMN_REACTION_SPEED + " REAL, " +
                HistoryEntry.COLUMN_ACCELERATION + " REAL, " +
                HistoryEntry.COLUMN_DATE + " TEXT NOT NULL);";
        db.execSQL(COMMUNITY_TABLE_SQL);
        db.execSQL(PARAMETERS_TABLE_SQL);
        db.execSQL(HISTORY_TABLE_SQL);
    }

    /**
     * Called when data base scheme is updating
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Update from version " + oldVersion + " to version " + newVersion);
        //delete old table and create new
    }
}
