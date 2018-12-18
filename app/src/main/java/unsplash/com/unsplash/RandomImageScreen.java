package unsplash.com.unsplash;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Method;
import java.util.zip.Inflater;

import static unsplash.com.unsplash.R.menu.random_items;

public class RandomImageScreen extends AppCompatActivity {

    String randomImageURL = "https://api.unsplash.com/photos/random?client_id=" +
            "52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96";
    String urlString;
    ImageView imgRandomImage;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_image_screen);
        imgRandomImage = findViewById(R.id.imgRandom);
        progressBar = findViewById(R.id.programRandom);

        setJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.random_items,menu);
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
}
