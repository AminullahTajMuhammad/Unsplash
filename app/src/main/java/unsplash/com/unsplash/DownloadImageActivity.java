package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.util.zip.Inflater;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class DownloadImageActivity extends AppCompatActivity {

    ImageView imgDownloadedImage;
    ProgressBar progressBar;

    private String getURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        imgDownloadedImage = findViewById(R.id.imgDownloadedImage);
        progressBar = findViewById(R.id.itemProgress);

        setDownloadIamgeToStorage(getURL);

        Picasso.get().load(getURL).into(imgDownloadedImage, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                Picasso.get().load(getURL).into(imgDownloadedImage);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        getDataOfImage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.random_items,menu);

        MenuItem randomItem = menu.findItem(R.id.itemReload);
        randomItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void getDataOfImage() {
        Intent intent = getIntent();
        getURL = intent.getStringExtra("ImageURL");
    }

    public void setDownloadIamgeToStorage(final String url) {
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //File file = new File(Environment.getExternalStorageDirectory().getPath()
                        //                + "/saved.jpg");

                        File dir = new File(Environment.getDataDirectory() + File.separator + "GetSplash");
                        if (!dir.exists())
                        {
                            dir.mkdirs();
                        }

                        File file = new File(dir + File.separator + System.currentTimeMillis() + ".jpg");

                        try {
                            //file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,ostream);
                            ostream.close();

                            MediaScannerConnection.scanFile(DownloadImageActivity.this, new String[] { file.getPath() }, new String[] { "image/jpeg" }, null);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.get().load(url).into(target);
    }
}
