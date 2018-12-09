package unsplash.com.unsplash;

import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.color.black;

public class SplashScreen extends AppCompatActivity {

    ImageView imgSplash;
    ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        mProgressbar = findViewById(R.id.progressSplash);
        Timer runSplash = new Timer();
        TimerTask showSplash = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
        runSplash.schedule(showSplash,3000);
    }
}
