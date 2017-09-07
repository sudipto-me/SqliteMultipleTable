package com.example.android.multipletable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Android on 9/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //Logcat tag
    private static final String LOG = "DATABASEHELPER";
    //database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "contactsManager";

    //Table Names
    private static final String Table_Todo = "todos";
    private static final String Table_Tags = "tags";
    private static final String Table_todo_tags = "todotags";

    //common column names
    private static final String Key_id = "id";
    private static final String Key_Created_at = "created_at";

    //Notes Table-Column Names
    private static final String Key_Todo = "todo";
    private static final String Key_Status = "status";

    //Tags Table-Column Names
    private static final String Key_Tag_Name = "tag_name";

    //Note Tags Table-Column Names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAGS_ID = "tag_id";

    //Table Create Statements
    //Todo Table Create Statements
    private static final String CREATE_TABLE_TODO = "CREATE TABLE " +
            Table_Todo + "(" + Key_id + " INTEGER PRIMARY KEY, " + Key_Todo +
            "TEXT," + Key_Status + " INTEGER," + Key_Created_at
            + " DATETIME " + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + Table_Tags
            + "(" + Key_id + " INTEGER PRIMARY KEY," + Key_Tag_Name + " TEXT," +
            Key_Created_at + " DATETIME " + ")";


    // todo_tag table create statement
    private static final String CREATE_TABLE_TODO_TAG = "CRETAE TABLE " +
            Table_todo_tags + "(" + Key_id + " INTEGER PRIMARY KEY," + KEY_TODO_ID +
            "INTEGER, " + KEY_TAGS_ID + " INTEGER,"
            + Key_Created_at + " DATEtIME " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_TODO);
        sqLiteDatabase.execSQL(CREATE_TABLE_TAG);
        sqLiteDatabase.execSQL(CREATE_TABLE_TODO_TAG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Todo);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Tags);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_todo_tags);

        onCreate(sqLiteDatabase);

    }

    //create a todo
    public long CreateTodo(Todo todo, long[] tag_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Todo, todo.getNote());
        contentValues.put(Key_Status, todo.getStatus());
        contentValues.put(Key_Created_at, todo.getCreated_at());

        //insert row
        long todo_id = db.insert(Table_Todo, null, contentValues);

        //assigning tags to todo
        for (long tag_id : tag_ids) {
            createTodoTag(todo_id, tag_id);
        }

        return todo_id;
    }

    //fetching a todo
    //get a single todo
    public Todo getTodo(long todo_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + Table_Todo +
                " WHERE " + Key_id + " = " + todo_id;
        Log.d(LOG, SELECT_QUERY);

        Cursor c = db.rawQuery(SELECT_QUERY, null);

        if (c != null)
            c.moveToFirst();

        Todo td = new Todo();
        td.setId(c.getInt(c.getColumnIndex(Key_id)));
        td.setNote((c.getString(c.getColumnIndex(Key_Todo))));
        td.setCreated_at(c.getString(c.getColumnIndex(Key_Created_at)));

        return td;

    }
    

}
