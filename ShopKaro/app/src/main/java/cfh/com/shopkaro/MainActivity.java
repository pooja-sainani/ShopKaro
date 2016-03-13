package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

//import android.support.v4.app.Fragment;

public class MainActivity extends BaseMenuActivitiy {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", "Main");
        getLayoutInflater().inflate(R.layout.activity_main, subActivityLayout);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int fragmentId = bundle.getInt("fragment");

            Fragment fragment = null;
            Bundle args = new Bundle();

            if(fragmentId == R.id.nav_products) {
                fragment = new CategoriesFragment();
                args.putBoolean("CategoryName", false);
            }
            else  if(fragmentId == R.id.nav_services){
                fragment = new CategoriesFragment();
                args.putBoolean("CategoryName", true);
            }
            else if (fragmentId == R.id.nav_cart){
                fragment = new MyCartFragment();
            }

            if(fragmentId == 123)   return;
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
}

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        Log.d("Stack count", ""+count);
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

   /* public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Activity", "Main onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Activity", "Main onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cfh.com.shopkaro/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cfh.com.shopkaro/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
