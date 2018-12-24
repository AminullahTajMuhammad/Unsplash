package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.squareup.picasso.Callback;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    ArrayList<DataClass> data = new ArrayList<>();
    ProgressDialog progressDialog;
    Context context;
    OnBottomReachedListener onBottomReachedListener;

    public MainAdapter(ArrayList<DataClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_listitem,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        //Downloading images From Internet Url
        //AsyncTaskClass taskClass = new AsyncTaskClass(myViewHolder.imgPicture,context, myViewHolder.progressBar);
        //taskClass.execute(data.get(i).Image_Url);


        final int tempPosition = i;
        // donwload images from internet
        Picasso.get().load(data.get(i).Image_Url).fetch(new Callback() {
            @Override
            public void onSuccess() {
                Picasso.get().load(data.get(tempPosition).Image_Url).into(myViewHolder.imgPicture);
            }

            @Override
            public void onError(Exception e) {

            }
        });



        // added animation before downloaded
        //FiftyShadesOf.with(context).on(myViewHolder.imgPicture).start();

        if (i >= (data.size() - 2) && onBottomReachedListener != null )
        {
            onBottomReachedListener.onBottomReached(i);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPicDescription;
        ImageView imgPicture;
        ProgressBar progressBar, loadProgress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPicDescription = itemView.findViewById(R.id.tvItemDesc);
            imgPicture = itemView.findViewById(R.id.imgImage);
            progressBar = itemView.findViewById(R.id.progressBar);
            loadProgress = itemView.findViewById(R.id.roadProgress);
        }
    }

}
