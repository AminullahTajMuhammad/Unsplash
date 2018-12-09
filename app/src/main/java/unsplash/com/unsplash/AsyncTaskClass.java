package unsplash.com.unsplash;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncTaskClass extends AsyncTask<String, Integer, Bitmap> {

    ImageView imgView;
    String Url;

    public AsyncTaskClass(ImageView imgView, String url) {
        this.imgView = imgView;
        Url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return null;
    }

}
