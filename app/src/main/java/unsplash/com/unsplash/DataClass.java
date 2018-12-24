package unsplash.com.unsplash;

import android.graphics.drawable.Drawable;

public class DataClass {
    boolean isDownload;
    String tvPicName;
    String tvDescriptionName;
    String Image_Url;
    int Picture;

    public DataClass(String tvPicName, String tvDescriptionName, String Image_Url, boolean isDownload) {
        this.tvPicName = tvPicName;
        this.tvDescriptionName = tvDescriptionName;
        this.Image_Url = Image_Url;
        this.isDownload = isDownload;
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

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
