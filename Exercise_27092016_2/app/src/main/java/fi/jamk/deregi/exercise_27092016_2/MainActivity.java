package fi.jamk.deregi.exercise_27092016_2;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String mediaPath;
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private LoadSongsTask loadSongsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(songs.get(position));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Cannot play "+ songs.get(position), Toast.LENGTH_LONG).show();
                }

            }
        });
        mediaPath = System.getenv("SECONDARY_STORAGE") + "/Music";

        loadSongsTask = new LoadSongsTask();
        loadSongsTask.execute();
    }

    private class LoadSongsTask extends AsyncTask<Void, String, Void> {

        private List<String> loadedSongs = new ArrayList<String>();

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            File file = new File(mediaPath);
            updateSongListRecursively(file);
            return null;
        }

        private void updateSongListRecursively(File file){
            if(!file.exists()){
                try {
                    throw new FileNotFoundException();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (file.isDirectory()){
                File[] files = file.listFiles();
                for (int i=0; i < file.listFiles().length; i++){
                    File child_file = file.listFiles()[i];
                    updateSongListRecursively(child_file);
                }
            }
            else {  // he?
                String name = file.getAbsolutePath();
                publishProgress(name);
                if (name.endsWith(".mp3")){
                    loadedSongs.add(name);
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ArrayAdapter<String> songList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, loadedSongs);
            listView.setAdapter(songList);
            songs = loadedSongs;
            Toast.makeText(getApplicationContext(), "Songs="+songs.size(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
    }
}
