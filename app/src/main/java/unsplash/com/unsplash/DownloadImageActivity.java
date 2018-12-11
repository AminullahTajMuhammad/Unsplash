package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class DownloadImageActivity extends AppCompatActivity {

    Button btnDownloadImage;
    ImageView imgDownloadedImage;

    private String downloadURL = "https://images.pexels.com/photos/799443/pexels-photo-799443.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        btnDownloadImage = findViewById(R.id.btnDownloadImage);
        imgDownloadedImage = findViewById(R.id.imgDownloadedImage);
    }
}
