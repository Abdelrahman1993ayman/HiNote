package com.example.abdelrahmanayman.simplenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DBConnection extends SQLiteOpenHelper {

    public static final String DBname = "NoteData.db" ;
    public static final int version = 1 ;

    public DBConnection(Context context) {
        super(context, DBname, null,  version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS Note (id INTEGER primary key , NoteText TEXT , Date Datetime )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if EXISTS Note");

    }

    public void InsertNewNote(String NoteText , String Date )
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("NoteText" , NoteText);
        contentValues.put("Date" , Date);
        sqLiteDatabase.insert("Note" , null , contentValues);
    }

    public ArrayList<NoteItem> getAllNotes(){
        ArrayList<NoteItem> array_list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from Note" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false)
        {
            array_list.add(new NoteItem(cursor.getString(cursor.getColumnIndex("NoteText")) , cursor.getString(cursor.getColumnIndex("Date")) , cursor.getString(cursor.getColumnIndex("id")) ));
            cursor.moveToNext();
        }
        return array_list ;
    }

    public void deleteNote(Integer id ){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from Note where id = " +Integer.toString(id));
    }

    public void updateNote(Integer id  , String Notetext){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        sqLiteDatabase.execSQL("update Note set NoteText ='" + Notetext +"' where id =" + Integer.toString(id));
    }



}
