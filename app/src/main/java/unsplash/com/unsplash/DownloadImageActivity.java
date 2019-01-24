package unsplash.com.unsplash;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

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

    private final static int REQUEST_STORAGE = 225;
    private final static int TEXT_STORAGE = 2;
    PermissionUtil permissionUtil;
    ImageView imgDownloadedImage;
    ProgressBar progressBar;

    private String getURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        permissionUtil = new PermissionUtil(this);
        imgDownloadedImage = findViewById(R.id.imgDownloadedImage);
        progressBar = findViewById(R.id.itemProgress);


        getDataOfImage();
        Picasso.get().load(getURL).into(imgDownloadedImage, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                imgDownloadedImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

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
        switch (item.getItemId()) {
           case R.id.itemDownload:
               if(checkPermission(TEXT_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                   if(ActivityCompat.shouldShowRequestPermissionRationale(DownloadImageActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                       showPermissionExplanation(TEXT_STORAGE);
                   }
                   else if (!permissionUtil.checkPermissionPrefernece("storage")) {
                       requestPermission(TEXT_STORAGE);
                       permissionUtil.updatePermissionPrefrence("storage");

                   } else {
                       Intent intent = new Intent();
                       intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                       Uri uri = Uri.fromParts("package", this.getPackageName(),null);
                       intent.setData(uri);
                       this.startActivity(intent);
                   }
               } else {

                   Toast.makeText(DownloadImageActivity.this,"Image is Downloading",Toast.LENGTH_SHORT).show();
                   setDownloadIamgeToStorage(getURL);

               }
               break;
        }
        return true;
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
                        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "GetSplash");
                        if (!dir.exists())
                        {
                            dir.mkdirs();
                        }

                        File file = new File(dir + File.separator + System.currentTimeMillis() + ".jpg");

                        try {

                            if(!file.exists()) {
                                file.createNewFile();
                            }

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
    // Check Permission that granted or not
    public int checkPermission(int permission) {

        int status = PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case TEXT_STORAGE: {
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            }
        }


        return status;
    }

    // Request New Permission
    public void requestPermission(int permission) {
        switch (permission) {
            case TEXT_STORAGE:
                ActivityCompat.requestPermissions(DownloadImageActivity.this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE);
                break;
        }
    }

    private void showPermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(permission == TEXT_STORAGE) {
            builder.setMessage("This app needs to access your device storage. Please Allow");
            builder.setTitle("Storage Permission Needed");
        }

        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(permission == TEXT_STORAGE) {
                    requestPermission(permission);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
