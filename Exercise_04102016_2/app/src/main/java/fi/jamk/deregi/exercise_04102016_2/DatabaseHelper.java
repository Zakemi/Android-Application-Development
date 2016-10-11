package fi.jamk.deregi.exercise_04102016_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DoubleD on 2016. 10. 08..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Shopping_list";
    private final String DATABASE_TABLE = "items";
    private final String NAME = "name";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT," + COUNT + " INTEGER," + PRICE + " REAL);");
        ContentValues values = new ContentValues();
        values.put(NAME, "egg");
        values.put(COUNT, 2);
        values.put(PRICE, 1);
        db.insert(DATABASE_TABLE, null, values);
        ContentValues values2 = new ContentValues();
        values2.put(NAME, "bread");
        values2.put(COUNT, 1);
        values2.put(PRICE, 1);
        db.insert(DATABASE_TABLE, null, values2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
