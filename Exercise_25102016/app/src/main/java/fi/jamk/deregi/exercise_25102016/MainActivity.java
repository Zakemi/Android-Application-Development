package fi.jamk.deregi.exercise_25102016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public RecyclerView myRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public GolfCourses mGolfCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONArray golfArray = null;
        try{
            InputStream inputStream = getAssets().open("golf_courses.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String golfStr = new String(buffer, "UTF-8");
            JSONObject golfObj = new JSONObject(golfStr);
            golfArray = golfObj.getJSONArray("kentat");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mGolfCourses = new GolfCourses(golfArray);

        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(mGolfCourses);
        myRecyclerView.setAdapter(mAdapter);
    }
}


