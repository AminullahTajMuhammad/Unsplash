package unsplash.com.unsplash;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    TextView tvName;
    ImageButton imgBack;

    MainAdapter mAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager =
            new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    ArrayList<DataClass> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerUnSplash);
        imgBack = findViewById(R.id.imgBtnBack);
        tvName = findViewById(R.id.tvAppName);

        setToolbar();
        setData();
        setAdapter();
    }

    public void setToolbar() {
        imgBack.setVisibility(View.GONE);
        tvName.setVisibility(View.VISIBLE);
    }

    public void setData() {

//        String photosStr = "[\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 1,\n" +
//                "    \"title\": \"accusamus beatae ad facilis cum similique qui sunt\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/92c952\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/92c952\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 2,\n" +
//                "    \"title\": \"reprehenderit est deserunt velit ipsam\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/771796\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/771796\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 3,\n" +
//                "    \"title\": \"officia porro iure quia iusto qui ipsa ut modi\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/24f355\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/24f355\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 4,\n" +
//                "    \"title\": \"culpa odio esse rerum omnis laboriosam voluptate repudiandae\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/d32776\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/d32776\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 5,\n" +
//                "    \"title\": \"natus nisi omnis corporis facere molestiae rerum in\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/f66b97\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/f66b97\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 6,\n" +
//                "    \"title\": \"accusamus ea aliquid et amet sequi nemo\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/56a8c2\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/56a8c2\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 7,\n" +
//                "    \"title\": \"officia delectus consequatur vero aut veniam explicabo molestias\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/b0f7cc\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/b0f7cc\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 8,\n" +
//                "    \"title\": \"aut porro officiis laborum odit ea laudantium corporis\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/54176f\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/54176f\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 9,\n" +
//                "    \"title\": \"qui eius qui autem sed\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/51aa97\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/51aa97\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 10,\n" +
//                "    \"title\": \"beatae et provident et ut vel\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/810b14\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/810b14\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 11,\n" +
//                "    \"title\": \"nihil at amet non hic quia qui\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/1ee8a4\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/1ee8a4\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 12,\n" +
//                "    \"title\": \"mollitia soluta ut rerum eos aliquam consequatur perspiciatis maiores\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/66b7d2\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/66b7d2\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 13,\n" +
//                "    \"title\": \"repudiandae iusto deleniti rerum\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/197d29\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/197d29\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 14,\n" +
//                "    \"title\": \"est necessitatibus architecto ut laborum\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/61a65\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/61a65\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 15,\n" +
//                "    \"title\": \"harum dicta similique quis dolore earum ex qui\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/f9cee5\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/f9cee5\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 16,\n" +
//                "    \"title\": \"iusto sunt nobis quasi veritatis quas expedita voluptatum deserunt\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/fdf73e\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/fdf73e\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 17,\n" +
//                "    \"title\": \"natus doloribus necessitatibus ipsa\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/9c184f\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/9c184f\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 18,\n" +
//                "    \"title\": \"laboriosam odit nam necessitatibus et illum dolores reiciendis\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/1fe46f\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/1fe46f\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 19,\n" +
//                "    \"title\": \"perferendis nesciunt eveniet et optio a\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/56acb2\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/56acb2\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 20,\n" +
//                "    \"title\": \"assumenda voluptatem laboriosam enim consequatur veniam placeat reiciendis error\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/8985dc\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/8985dc\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 21,\n" +
//                "    \"title\": \"ad et natus qui\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/5e12c6\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/5e12c6\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"albumId\": 1,\n" +
//                "    \"id\": 22,\n" +
//                "    \"title\": \"et ea illo et sit voluptas animi blanditiis porro\",\n" +
//                "    \"url\": \"https://via.placeholder.com/600/45601a\",\n" +
//                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/45601a\"\n" +
//                "  }]";
//
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




        data.add(new DataClass("Hello World", "A pic of small town",
                "https://images.pexels.com/photos/1212487/pexels-photo-1212487.jpeg?auto=compress0&cs=tinysrgb&h=650&w=940"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://res.cloudinary.com/prestige-gifting/image/fetch/fl_progressive,q_95,e_sharpen:50,w_480/e_saturation:05/https://www.prestigeflowers.co.uk/images/NF4016-130116.jpg"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg"));
        data.add(new DataClass("Hello World", "A pic of small town",
                "https://ichef.bbci.co.uk/news/660/cpsprodpb/1999/production/_92935560_robot976.jpg"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://img.huffingtonpost.com/asset/55b72dba1400002f002e1008.jpeg?ops=crop_0_0_3825_2018,scalefit_720_noupscale"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://images.pexels.com/photos/1212487/pexels-photo-1212487.jpeg?auto=compress0&cs=tinysrgb&h=650&w=940"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://img.huffingtonpost.com/asset/55b72dba1400002f002e1008.jpeg?ops=crop_0_0_3825_2018,scalefit_720_noupscale"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://ichef.bbci.co.uk/news/660/cpsprodpb/1999/production/_92935560_robot976.jpg"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg"));


    }

    public void setAdapter() {
        mAdapter = new MainAdapter(data, MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void dummyJson()
    {
        String jsonStr = "{\n" +
                "   \"sys\":\n" +
                "   {\n" +
                "      \"country\":\"GB\",\n" +
                "      \"sunrise\":1381107633,\n" +
                "      \"sunset\":1381149604\n" +
                "   },\n" +
                "   \"weather\":[\n" +
                "      {\n" +
                "         \"id\":711,\n" +
                "         \"main\":\"Smoke\",\n" +
                "         \"description\":\"smoke\",\n" +
                "         \"icon\":\"50n\"\n" +
                "      }\n" +
                "   ],\n" +
                "\t\n" +
                "  \"main\":\n" +
                "   {\n" +
                "      \"temp\":304.15,\n" +
                "      \"pressure\":1009,\n" +
                "   }\n" +
                "}";

        try {
            JSONObject root = new JSONObject(jsonStr);
            JSONObject sysObject = root.getJSONObject("sys");
            String country = sysObject.getString("country");
            long sunrise = sysObject.getLong("sunrise");

            ArrayList<WeatherModel> weatherModelArrayList = new ArrayList<>();

            JSONArray weatherArr = root.getJSONArray("weather");
            for (int i=0; i<weatherArr.length(); i++)
            {
                JSONObject jsonObject = weatherArr.getJSONObject(i);
                WeatherModel model = new WeatherModel(
                        jsonObject.getInt("id"),
                        jsonObject.getString("main"),
                        jsonObject.getString("description"),
                        jsonObject.getString("icon")
                );
                weatherModelArrayList.add(model);
            }

            // You got array list of weather model


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}