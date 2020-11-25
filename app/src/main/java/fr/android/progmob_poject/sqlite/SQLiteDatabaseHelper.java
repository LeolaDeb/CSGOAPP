package fr.android.progmob_poject.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.ImageTransformation;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.android.progmob_poject.model.Match;


public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "matches";
    private static final String COL0 = "ID";
    private static final String COL1 = "team_a";
    private static final String COL2 = "team_b";
    private static final String COL3 = "address";
    private static final String COL4 = "coordinates";
    private static final String COL5 = "date_match";
    private static final String COL6 = "score_team_a";
    private static final String COL7 = "score_team_b";
    private static final String COL8 = "picture_path";


    public SQLiteDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 +" TEXT,  " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " INTEGER, " + COL7 + " INTEGER, " + COL8 + " TEXT )" ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Match match, String picturePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL0, match.getId());
        contentValues.put(COL1, match.getTeam_a());
        contentValues.put(COL2, match.getTeam_b());
        contentValues.put(COL3, match.getAddress());
        contentValues.put(COL4, match.getCoordinates());
        contentValues.put(COL5, match.getDate_match().toString());
        contentValues.put(COL6, match.getScore_team_a());
        contentValues.put(COL7, match.getScore_team_b());
        contentValues.put(COL8, picturePath);
        Log.d(TAG, "addData: Adding " + match.getTeam_a() + " VS " + match.getTeam_b() + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public List<Match> getData(){
        List<Match> matches = new ArrayList<Match>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        while (data.moveToNext()){
            Match match = new Match(data.getInt(0), data.getString(1), data.getString(2), data.getString(3),data.getString(4), (LocalDate) LocalDate.parse(data.getString(5)), data.getInt(6),data.getInt(7) );
            match.setPicture_path(data.getString(8));
            matches.add(match);
        }
        return matches;
    }

    /**
     * Returns the match that matches the teams and date passed in
     * @param teamA
     * @param teamB
     * @param date
     * @return
     */
    public Match getMatch(String teamA, String teamB, String date){
        Match match = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + teamA + "' AND " + COL2 +" = '" + teamB + "' AND " + COL5 + " = '" + date + "'";
        Cursor data = db.rawQuery(query, null);
        while (data.moveToNext()){
            match = new Match(data.getInt(0), data.getString(1), data.getString(2), data.getString(3),data.getString(4), (LocalDate) LocalDate.parse(data.getString(5)), data.getInt(6),data.getInt(7) );
            match.setPicture_path(data.getString(8));
        }
        return match;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 +
                " = '" + newName + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}