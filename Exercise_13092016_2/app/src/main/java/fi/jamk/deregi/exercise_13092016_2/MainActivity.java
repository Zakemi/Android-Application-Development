package fi.jamk.deregi.exercise_13092016_2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMap(View view){
        EditText editText1 = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String number1 = editText1.getText().toString();
        String number2 = editText2.getText().toString();
        double lat = Double.parseDouble(number1);
        double lng = Double.parseDouble(number2);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo: "+lat+","+lng));
        startActivity(intent);
    }
}
