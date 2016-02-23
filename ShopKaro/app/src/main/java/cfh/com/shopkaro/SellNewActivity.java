package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SellNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int fragmentId = getIntent().getExtras().getInt("fragment");
        Fragment fragment;
        if(fragmentId == R.id.nav_sell_service){
            fragment = new SellServiceFragment();
        }
        else {
            fragment = new SellProductFragment();
        }
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.sell_content_frame, fragment).commit();
    }

}
