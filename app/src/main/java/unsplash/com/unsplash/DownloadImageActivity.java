package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

public class DownloadImageActivity extends AppCompatActivity {

    Button btnDownloadImage;
    ImageView imgDownloadedImage;
    ProgressDialog progressDialog;
    private String downloadURL = "https://images.unsplash.com/photo-1509782642997-4befdc4b21c9?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        btnDownloadImage = findViewById(R.id.btnDownloadImage);
        imgDownloadedImage = findViewById(R.id.imgDownloadedImage);

        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(DownloadImageActivity.this).
                        load(downloadURL).
                        into(imgDownloadedImage);
            }
        });

    }
}
