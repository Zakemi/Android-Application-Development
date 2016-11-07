package fi.jamk.deregi.exercise_25102016;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

public class GolfDetailsActivity extends AppCompatActivity {

    private String name;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golf_infos_activity);

        Intent intent = getIntent();

        ((TextView) findViewById(R.id.infos_address)).setText(intent.getStringExtra("address"));
        ((TextView) findViewById(R.id.infos_phone)).setText(intent.getStringExtra("phone"));
        ((TextView) findViewById(R.id.infos_email)).setText(intent.getStringExtra("email"));
        ((TextView) findViewById(R.id.infos_description)).setText(intent.getStringExtra("description"));
        ((TextView) findViewById(R.id.infos_web)).setText(intent.getStringExtra("web"));
        name = intent.getStringExtra("name");
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        ((TextView) findViewById(R.id.infos_location)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(" + name + ")"));
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        try {
            Field field = R.drawable.class.getField(intent.getStringExtra("picture").replace("kuvat/","").replace(".jpg", ""));
            int id = field.getInt(null);
            ((ImageView) findViewById(R.id.infos_picture)).setImageResource(id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
