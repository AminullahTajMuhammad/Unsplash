package unsplash.com.unsplash;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    TextView tvName;
    ImageButton imgBack;

    MainAdapter mAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
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
        imgBack.setVisibility(View.INVISIBLE);
        tvName.setVisibility(View.VISIBLE);
    }

    public void setData() {
        data.add(new DataClass("Hello World", "A pic of small town",
                "https://www.pexels.com/photo/yellow-cosmos-flower-close-up-photography-1212487/"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://www.pexels.com/photo/shallow-focus-photography-of-green-fern-1141792/"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://www.pexels.com/photo/landscape-photography-of-road-and-forest-775203/"));
        data.add(new DataClass("Hello World", "A pic of small town",
                "https://www.pexels.com/photo/lights-water-blur-rain-21492/"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://www.pexels.com/photo/red-wooden-lounge-chair-on-brown-boardwalk-near-body-of-water-during-daytime-161029/"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://www.pexels.com/photo/asphalt-cars-countryside-daylight-535986/"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://www.pexels.com/photo/blur-bridge-clouds-dawn-447431/"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://www.pexels.com/photo/close-up-of-tree-254179/"));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                "https://www.pexels.com/photo/abstract-blur-bright-day-close-up-237907/"));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                "https://www.pexels.com/photo/barn-blur-close-up-countryside-277669/"));
    }

    public void setAdapter() {
        mAdapter = new MainAdapter(data, MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}