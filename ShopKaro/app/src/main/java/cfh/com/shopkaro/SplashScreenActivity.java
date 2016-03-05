package cfh.com.shopkaro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
               SharedPreferences sp = getBaseContext().getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
                String email = sp.getString("username", null);
                String password = sp.getString("password", null);
                Log.e("Splash SCreen error", email + " " + password);
                if(email!=null && password!=null){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
               }
                else {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
