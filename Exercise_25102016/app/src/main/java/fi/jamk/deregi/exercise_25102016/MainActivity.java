package fi.jamk.deregi.exercise_25102016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener{

    public RecyclerView myRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public GolfCourses mGolfCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        mAdapter = new MyAdapter(mGolfCourses, this);
        myRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(GolfCourses.GolfCourse golfCourse) {
        Intent intent = new Intent(this, GolfDetailsActivity.class);
        intent.putExtra("name", golfCourse.name);
        intent.putExtra("picture", golfCourse.picture);
        intent.putExtra("phone", golfCourse.phone);
        intent.putExtra("address", golfCourse.address);
        intent.putExtra("email", golfCourse.email);
        intent.putExtra("description", golfCourse.description);
        intent.putExtra("web", golfCourse.web);
        intent.putExtra("lat", golfCourse.lat);
        intent.putExtra("lng", golfCourse.lng);
        startActivity(intent);
    }
}


