package unsplash.com.unsplash;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.ConditionVariable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
public class AsyncTaskClass extends AsyncTask<String, Integer, Bitmap> {
    ImageView imgView;
    Context context;
    public AsyncTaskClass(ImageView imgView, Context context) {
        this.imgView = imgView;
        this.context = context;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        for (String params : strings) {
            try {
                URL url = new URL(params);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}