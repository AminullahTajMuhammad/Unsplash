package unsplash.com.unsplash;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvPicName.setText(data.get(i).getTvPicName());
        myViewHolder.tvPicDescription.setText(data.get(i).getTvDescriptionName());

        AsyncTaskClass taskClass = new AsyncTaskClass(myViewHolder.imgPicture,context);
        taskClass.execute(data.get(i).Image_Url);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPicName;
        TextView tvPicDescription;
        ImageView imgPicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPicName = itemView.findViewById(R.id.tvItemName);
            tvPicDescription = itemView.findViewById(R.id.tvItemDesc);
            imgPicture = itemView.findViewById(R.id.imgImage);
        }
    }

//    public class ImageDownloaded extends AsyncTask<String, Integer, Bitmap> {
//
//        public ImageDownloaded() {
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setTitle("Downloading Image...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            for (String params : strings) {
//                try {
//                    URL url = new URL(params);
//                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream inputStream = connection.getInputStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    return bitmap;
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            MyViewHolder myViewHolder = null;
//            progressDialog.dismiss();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
//    }
}
