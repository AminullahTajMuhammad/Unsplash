package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

    ImageView imgDownloadedImage;

    private String getURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        imgDownloadedImage = findViewById(R.id.imgDownloadedImage);

        getDataOfImage();

        Picasso.get().load(getURL).into(imgDownloadedImage);


    }
    public void getDataOfImage() {
        Intent intent = getIntent();
        getURL = intent.getStringExtra("ImageURL");
    }
}
