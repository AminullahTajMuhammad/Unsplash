package unsplash.com.unsplash;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.zip.Inflater;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;

import static unsplash.com.unsplash.R.menu.random_items;

public class RandomImageScreen extends AppCompatActivity {

    String randomImageURL = "https://api.unsplash.com/photos/random?client_id=" +
            "52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96";
    String urlString;
    TouchImageView imgRandomImage;
    ImageButton imgBack, btnDownload, btnReload;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(RandomImageScreen.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        setContentView(R.layout.activity_random_image_screen);
        imgRandomImage = (TouchImageView) findViewById(R.id.imgRandom);
        progressBar = findViewById(R.id.programRandom);

        setJSON();
        //setToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.random_items,menu);
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
                Picasso.get().load(randomImageURL).into(getTarget(randomImageURL));
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
                            urlString = jsonObject.getString("full");

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

    public void setDownloadImageFun() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.imgRandom);
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/Getsplash/");
        dir.mkdirs();

        File file = new File(dir, "download1.jpg");
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        //target to save
        private static Target getTarget (final String url) {
            Target target = new Target() {

                @Override
                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
                            try {
                                file.createNewFile();
                                FileOutputStream ostream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                                ostream.flush();
                                ostream.close();
                            } catch (IOException e) {
                                Log.e("IOException", e.getLocalizedMessage());
                            }
                        }
                    }).start();

                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            return target;
        }

}





//public void setImageDownload(final String url) {
//        Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // create root folder in sd or phone
//                        File root = Environment.getExternalStorageDirectory();
//
//                        // create Folder name in memory
//                        File appFolder = new File(root + "/GetSplash" );
//
//                        //if folder is not created then create it otherwise nothing
//                        if(!appFolder.exists()){
//                            Boolean result = appFolder.mkdirs();
//                        }
//
//                        // create file name in he folder
//                        File fileName = new File(appFolder , "abcd.jpg");
//
//
//                        try {
//
//                            //Convert bitmap to byte array
//                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                            byte[] bitmapdata = bos.toByteArray();
//
//                            //write the bytes in file
//                            FileOutputStream fos = new FileOutputStream(fileName);
//
//                            Boolean result = fileName.createNewFile();
//                            fos.write(bitmapdata);
//                            fos.flush();
//                            fos.close();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }).start();
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };

//Picasso.get().load(url).into(target);