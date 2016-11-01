package fi.jamk.deregi.exercise_25102016;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by DoubleD on 2016. 11. 01..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private GolfCourses golfCourses;

    public MyAdapter(GolfCourses golfCourses){
        this.golfCourses = golfCourses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.golf_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GolfCourses.GolfCourse golfCourse = golfCourses.golfCourses.get(position);
        try {
            Field field = R.drawable.class.getField(golfCourse.picture.replace("kuvat/","").replace(".jpg", ""));
            int id = field.getInt(null);
            holder.imageView.setImageResource(id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        holder.nameTextView.setText(golfCourse.name);
        holder.addressTextView.setText(golfCourse.address);
        holder.emailTextView.setText(golfCourse.email);
        holder.phoneTextView.setText(golfCourse.phone);
    }

    @Override
    public int getItemCount() {
        return golfCourses.golfCourses.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView nameTextView;
        public TextView addressTextView;
        public TextView phoneTextView;
        public TextView emailTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            phoneTextView = (TextView) itemView.findViewById(R.id.phoneTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
        }
    }
}
