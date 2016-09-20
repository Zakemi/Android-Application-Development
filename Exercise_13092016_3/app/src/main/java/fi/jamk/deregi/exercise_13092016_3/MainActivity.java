package fi.jamk.deregi.exercise_13092016_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        final String[] phones = new String[]{"Android", "iPhone", "WindowsMobile", "BlackBerry", "WebOS", "Ubuntu",
                "Android", "iPhone", "WindowsMobile", "BlackBerry", "WebOS", "Ubuntu",
                "Android", "iPhone", "WindowsMobile", "BlackBerry", "WebOS", "Ubuntu"
        };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < phones.length; i++){
            list.add(phones[i]);
        }

        // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.textView, list);
        PhoneArrayAdapter adapter = new PhoneArrayAdapter(this, list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String phone = list.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }

}
