package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    TextView tvName;
    ImageButton imgBack, btnReload, btnDownload;
    FloatingActionButton btnFloatRandom;

    String url = "https://api.unsplash.com/photos/curated?client_id=52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96";
    String newUrl = "https://api.unsplash.com/photos/curated?client_id=52f6fe575c0944e744299f550208a4cba773d1da029df74d4dbe7b4362808f96&page=";
    boolean isScroll = false;
    int pageNumber = 1;
    int currentItems;
    int totalItems;
    int[] scrolledItems;

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

    public void setToolbar() {
        imgBack.setVisibility(View.GONE);
        btnDownload.setVisibility(View.GONE);
        btnReload.setVisibility(View.GONE);
        tvName.setVisibility(View.VISIBLE);
    }

    public void setAdapter() {
        mAdapter = new MainAdapter(data, MainActivity.this);
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
                                    jsonObject.getJSONObject("urls").getString("thumb")
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

//    public void setData() {
//        try {
//            JSONArray rootArr = new JSONArray(photosStr);
//            for (int i=0; i<rootArr.length(); i++)
//            {
//                JSONObject jsonObject = rootArr.getJSONObject(i);
//                DataClass dataClass = new DataClass(
//                        jsonObject.getString("title"),
//                        jsonObject.getString("title"),
//                        jsonObject.getString("url")
//                );
//                data.add(dataClass);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }




//        data.add(new DataClass("Hello World", "A pic of small town",
//                "https://images.pexels.com/photos/1212487/pexels-photo-1212487.jpeg?auto=compress0&cs=tinysrgb&h=650&w=940"));
//        data.add(new DataClass("Volkswagen", "A pic of bmw car",
//                "https://res.cloudinary.com/prestige-gifting/image/fetch/fl_progressive,q_95,e_sharpen:50,w_480/e_saturation:05/https://www.prestigeflowers.co.uk/images/NF4016-130116.jpg"));
//        data.add(new DataClass("Nissan", "A pic of nissan car",
//                "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg"));
//        data.add(new DataClass("Hello World", "A pic of small town",
//                "https://ichef.bbci.co.uk/news/660/cpsprodpb/1999/production/_92935560_robot976.jpg"));
//        data.add(new DataClass("Volkswagen", "A pic of bmw car",
//                "https://img.huffingtonpost.com/asset/55b72dba1400002f002e1008.jpeg?ops=crop_0_0_3825_2018,scalefit_720_noupscale"));
//        data.add(new DataClass("Nissan", "A pic of nissan car",
//                "https://images.pexels.com/photos/1212487/pexels-photo-1212487.jpeg?auto=compress0&cs=tinysrgb&h=650&w=940"));
//        data.add(new DataClass("Volkswagen", "A pic of bmw car",
//                "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg"));
//        data.add(new DataClass("Nissan", "A pic of nissan car",
//                "https://img.huffingtonpost.com/asset/55b72dba1400002f002e1008.jpeg?ops=crop_0_0_3825_2018,scalefit_720_noupscale"));
//        data.add(new DataClass("Volkswagen", "A pic of bmw car",
//                "https://ichef.bbci.co.uk/news/660/cpsprodpb/1999/production/_92935560_robot976.jpg"));
//        data.add(new DataClass("Nissan", "A pic of nissan car",
//                "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg"));


//    }

//    public void dummyJson()
//    {
}