package unsplash.com.unsplash;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.widget.Toast.LENGTH_SHORT;

public class RandomImageScreen extends AppCompatActivity {

    private final static int REQUEST_STORAGE = 225;
    private final static int TEXT_STORAGE = 2;

    String randomImageURL = "https://api.unsplash.com/photos/random?client_id=52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96";
    String urlString;
    PermissionUtil permissionUtil;
    TouchImageView imgRandomImage;
    ImageButton imgBack, btnDownload, btnReload;
    ProgressBar progressBar;
    Activity activity;        // for create toast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionUtil = new PermissionUtil(this);

        ActivityCompat.requestPermissions(RandomImageScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        setContentView(R.layout.activity_random_image_screen);
        imgRandomImage = (TouchImageView) findViewById(R.id.imgRandom);
        progressBar = findViewById(R.id.programRandom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(5.0f);
        setJSON();
        //setToolBar();


        //For Permissions



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
                ActivityCompat.requestPermissions(RandomImageScreen.this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.random_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemReload:
                progressBar.setVisibility(View.VISIBLE);
                setJSON();
                break;
            case R.id.itemDownload:
                if(checkPermission(TEXT_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if(ActivityCompat.shouldShowRequestPermissionRationale(RandomImageScreen.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
                }
                if (checkPermission(TEXT_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(RandomImageScreen.this,"Random Image is Downloading",Toast.LENGTH_SHORT).show();
                    setImageDownload(urlString);
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    public void setJSON() {

        final String TAG = "TAG";
        String tag_json_arry = "json_array_req";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                randomImageURL, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject jsonObject = response.getJSONObject("urls");
                            urlString = jsonObject.getString("regular");

                            //picasso library
                            Picasso.get().load(urlString).into(imgRandomImage, new Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE);
                                    imgRandomImage.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getAppController().addToRequestQueue(jsonObjReq);
    }

    public void setToolBar() {
        btnDownload.setVisibility(View.GONE);
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public void setImageDownload(final String url) {
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

                            MediaScannerConnection.scanFile(RandomImageScreen.this, new String[] { file.getPath() }, new String[] { "image/jpeg" }, null);
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