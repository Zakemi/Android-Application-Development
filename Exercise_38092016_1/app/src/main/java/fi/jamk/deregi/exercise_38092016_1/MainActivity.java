package fi.jamk.deregi.exercise_38092016_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;
    private DownloadImageTask task;

    private float x1;
    private float x2;

    private String[] images = {
            "http://www.geocities.ws/powercore_04/kenshin2_chibi.jpg",
            "http://wallup.net/wp-content/uploads/2016/01/278003-nature-landscape-sunrise-forest-mist-fall-sky-clouds-trees-Finland.jpg",
            "https://i1.wp.com/www.back2gaming.com/wp-content/uploads/2014/08/8.jpg",
            "http://static2.visitfinland.com/wp-content/uploads/G70424_RGB_4136-400x300.jpg"
    };
    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageIndex = 0;

        showImage();
    }

    private void showImage(){
        task = new DownloadImageTask();
        task.execute(images[imageIndex]);
    };

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try{
                URL imageURL = new URL(params[0]);
                InputStream inputStream = imageURL.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e("Load image", e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            textView.setText("Image" + (imageIndex + 1) + "/" + images.length);
            imageView.setImageBitmap(bitmap);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                if (x1 < x2){
                    if (imageIndex > 0) {
                        imageIndex--;
                    }
                }
                else if (x1 > x2)
                    if (imageIndex < images.length - 1){
                        imageIndex++;
                    }
                showImage();
                break;
        }
        return false;
    }
}
