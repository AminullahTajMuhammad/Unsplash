package unsplash.com.unsplash;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.BitSet;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    TextView tvName;
    ImageButton imgBack, btnReload, btnDownload;
    FloatingActionButton btnFloatRandom;

    String url = "https://api.unsplash.com/photos/curated?client_id=52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96";
    String newUrl = "https://api.unsplash.com/photos/curated?client_id=52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96&page=";
    int pageNumber = 1;

    MainAdapter mAdapter;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager = new GridLayoutManager(this,2);

    ArrayList<DataClass> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerUnSplash);
        imgBack = findViewById(R.id.imgBtnBack);
        btnReload = findViewById(R.id.btnReload);
        btnDownload = findViewById(R.id.btnDownload);
        tvName = findViewById(R.id.tvAppName);

        btnFloatRandom = findViewById(R.id.btnRandom);
        btnFloatRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RandomImageScreen.class);
                startActivity(intent);
            }
        });
        JSONGetData();
        setAdapter();


    }

    public void setAdapter() {
        mAdapter = new MainAdapter(data, MainActivity.this, new OnItemClicked() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, DownloadImageActivity.class);
                intent.putExtra("ImageURL", data.get(position).getImage_Url());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // when scroll is at almost reached bottom new pics uploaded using this listner
        mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                JSONGetData();
                pageNumber++;
            }
        });

    }

    public void JSONGetData() {
        String tempUrl  = newUrl+pageNumber;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(tempUrl, new Response.Listener<JSONArray>() {
            private static final String TAG = "TAG";

                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());
                    try {
                        for (int i=0; i<response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            DataClass dataClass = new DataClass(
                                    jsonObject.getJSONObject("user").getString("username"),
                                    jsonObject.getString("description"),
                                    jsonObject.getJSONObject("urls").getString("thumb"),
                                    false,
                                    jsonObject.getJSONObject("urls").getString("full")
                            );
                            data.add(dataClass);
                            mAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                            e.printStackTrace();
                    }
                }

            },
             new Response.ErrorListener() {

                    private static final String TAG = "TAG";

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error"+error.getMessage());
                    }
             });

            AppController.getAppController().addToRequestQueue(jsonArrayRequest);
    }

}