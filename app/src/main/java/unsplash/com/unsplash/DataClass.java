package unsplash.com.unsplash;

import android.graphics.drawable.Drawable;

public class DataClass {
    String tvPicName;
    String tvDescriptionName;
    int Picture;

    public DataClass(String tvPicName, String tvDescriptionName, int picture) {
        this.tvPicName = tvPicName;
        this.tvDescriptionName = tvDescriptionName;
        Picture = picture;
    }

    public String getTvPicName() {
        return tvPicName;
    }

    public void setTvPicName(String tvPicName) {
        this.tvPicName = tvPicName;
    }

    public String getTvDescriptionName() {
        return tvDescriptionName;
    }

    public void setTvDescriptionName(String tvDescriptionName) {
        this.tvDescriptionName = tvDescriptionName;
    }

    public int getPicture() {
        return Picture;
    }

    public void setPicture(int picture) {
        Picture = picture;
    }
}
