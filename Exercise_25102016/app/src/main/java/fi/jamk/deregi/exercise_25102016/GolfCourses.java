package fi.jamk.deregi.exercise_25102016;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DoubleD on 2016. 11. 01..
 */

public class GolfCourses {

    public ArrayList<GolfCourse> golfCourses;

    public GolfCourses(JSONArray golfJSONArray) {
        this.golfCourses = new ArrayList<>();
        for (int i=0; i<golfJSONArray.length(); i++){
            try {
                JSONObject golfObj = golfJSONArray.getJSONObject(i);
                golfCourses.add(new GolfCourse(
                        golfObj.getString("Tyyppi"),
                        golfObj.getDouble("lat"),
                        golfObj.getDouble("lng"),
                        golfObj.getString("Kentta"),
                        golfObj.getString("Osoite"),
                        golfObj.getString("Puhelin"),
                        golfObj.getString("Sahkoposti"),
                        golfObj.getString("Webbi"),
                        golfObj.getString("Kuva"),
                        golfObj.getString("Kuvaus")
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class GolfCourse{
        public String type;
        public double lat;
        public double lng;
        public String name;
        public String address;
        public String phone;
        public String email;
        public String web;
        public String picture;
        public String description;

        public GolfCourse(String type, double lat, double lng, String name, String address, String phone, String email, String web, String picture, String description) {
            this.type = type;
            this.lat = lat;
            this.lng = lng;
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.email = email;
            this.web = web;
            this.picture = picture;
            this.description = description;
        }
    }

}