package fi.jamk.deregi.exercise_04102016_2;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener {

    SQLiteDatabase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        db = (new DatabaseHelper(this)).getWritableDatabase();

        setListView();
    }

    public void setListView(){

        listView.setAdapter(setListViewAdapter());

        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor cursor = (SQLiteCursor) listView.getItemAtPosition(position);
                Long itemId = cursor.getLong(cursor.getColumnIndex("_id"));
                String[] args = {String.valueOf(itemId)};
                db.delete("items", "_id=?", args);
                listView.setAdapter(setListViewAdapter());
                toastTotalPrice();
                return true;
            }
        });
    }

    public ListAdapter setListViewAdapter(){
        Cursor cursor = db.rawQuery("SELECT _id, name, count, price FROM items", null);

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_layout, cursor,
                new String[] {"name", "count", "price"},
                new int[] {R.id.name, R.id.count, R.id.price},
                0);
        return adapter;
    }

    public void toastTotalPrice(){
        Cursor cursor = db.rawQuery("SELECT SUM(price) from items", null);
        if(cursor.moveToFirst()){
            Toast.makeText(getApplicationContext(), "Total price: " + String.valueOf(cursor.getDouble(0)), Toast.LENGTH_LONG).show();
        }
    }

    public void addItem(View view){
        AddItemDialog dialog = new AddItemDialog();
        dialog.show(getFragmentManager(), "he");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, Integer count, Double price) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("count", count);
        values.put("price", price);
        db.insert("items", null, values);
        setListView();
        toastTotalPrice();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
