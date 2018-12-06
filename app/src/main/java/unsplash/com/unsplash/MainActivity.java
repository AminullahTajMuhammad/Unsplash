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
                R.drawable.mercedes));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                R.drawable.blood_donate));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                R.drawable.donate_blood_1st));
        data.add(new DataClass("Hello World", "A pic of small town",
                R.drawable.mercedes));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                R.drawable.blood_donate));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                R.drawable.nissan));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                R.drawable.volkswagen));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                R.drawable.donate_blood_1st));
        data.add(new DataClass("Volkswagen", "A pic of bmw car",
                R.drawable.volkswagen));
        data.add(new DataClass("Nissan", "A pic of nissan car",
                R.drawable.nissan));
    }

    public void setAdapter() {
        mAdapter = new MainAdapter(data);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}