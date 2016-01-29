package verkstad.org.in.valentineapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;

public class SplashScreen extends AppCompatActivity {
private static int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AccessToken.getCurrentAccessToken()!=null){
                    Intent intent=new Intent(SplashScreen.this,Home.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        },SPLASH_TIME_OUT);
    }


}
