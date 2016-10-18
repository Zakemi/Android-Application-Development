package fi.jamk.deregi.exercise_11102016;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<String, Float> markerColors;
    private HashMap<Marker, JSONObject> markerInfoWindowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        JSONObject obj = null;
        try{
            InputStream inputStream = getAssets().open("golf_courses.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            obj = new JSONObject(json);
        } catch (JSONException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            JSONArray courses = obj.getJSONArray("kentat");
            for (int i=0; i<courses.length(); i++){
                JSONObject course = courses.getJSONObject(i);
                String type = course.getString("Tyyppi");
                Double lat = course.getDouble("lat");
                Double lng = course.getDouble("lng");

                LatLng courseLatLng = new LatLng(lat, lng);
                Marker courseMarker = mMap.addMarker(new MarkerOptions()
                        .position(courseLatLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(getMarkerColor(type)))
                );
                if (markerInfoWindowData == null){
                    markerInfoWindowData = new HashMap<Marker, JSONObject>();
                }
                markerInfoWindowData.put(courseMarker, course);

            }
        } catch (JSONException e){
            e.printStackTrace();
        }


        // Add a marker in Sydney and move the camera
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.infowindow, null);
                JSONObject courseData = markerInfoWindowData.get(marker);
                TextView name = (TextView) view.findViewById(R.id.iw_name);
                TextView address = (TextView) view.findViewById(R.id.iw_address);
                TextView phone = (TextView) view.findViewById(R.id.iw_phone);
                TextView email = (TextView) view.findViewById(R.id.iw_email);
                TextView web = (TextView) view.findViewById(R.id.iw_web);
                try{
                    name.setText(courseData.getString("Kentta") + " (" + courseData.getString("Tyyppi") + ")");
                    address.setText(courseData.getString("Osoite"));
                    phone.setText(courseData.getString("Puhelin"));
                    email.setText(courseData.getString("Sahkoposti"));
                    web.setText(courseData.getString("Webbi"));
                } catch (JSONException e){
                    e.printStackTrace();
                }
                return view;
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(65.3320614, 27.1558269), 5));
    }

    private float getMarkerColor(String type){
        if (markerColors == null){
            markerColors = new HashMap<String, Float>();
            markerColors.put(type, 0.0f);
        }
        else if (markerColors.get(type) == null){
            markerColors.put(type, markerColors.size()*30.0f);
        }
        return markerColors.get(type);
    }
}
