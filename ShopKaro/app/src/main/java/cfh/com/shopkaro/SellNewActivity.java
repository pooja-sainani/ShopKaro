package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class SellNewActivity extends BaseMenuActivitiy {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", "Sell");

        getLayoutInflater().inflate(R.layout.activity_sell_new, subActivityLayout);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume(){
        super.onResume();
    }


}
