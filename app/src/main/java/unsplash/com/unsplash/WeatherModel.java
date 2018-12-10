package unsplash.com.unsplash;

public class WeatherModel {

    public int id;
    public String main, description, icon;

    public WeatherModel(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }
}
