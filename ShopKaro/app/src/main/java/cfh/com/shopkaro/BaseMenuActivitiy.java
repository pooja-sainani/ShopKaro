package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import cfh.com.shopkaro.dummy.CartContent;
import cfh.com.shopkaro.dummy.CategoriesContent;
import cfh.com.shopkaro.dummy.ProductContent;

public class BaseMenuActivitiy extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener,  ProductPageFragment.OnFragmentInteractionListener, ProductServiceFragment.OnListFragmentInteractionListener,CategoriesFragment.OnListFragmentInteractionListener,MyCartFragment.OnListFragmentInteractionListener{

    @Override
    public void onListFragmentInteraction(ProductContent.DummyItem item) {
        Fragment fragment = new ProductPageFragment();
        Bundle args = new Bundle();
        args.putString("ProductId", item.id);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }



    @Override
    public void onListFragmentInteraction(CartContent.CartItem item) {
    }

    @Override
    public void onListFragmentInteraction(CategoriesContent.DummyItem item) {
        Fragment fragment = new ProductServiceFragment();
        Bundle args = new Bundle();
        args.putString("CategoryName", item.id);
        args.putBoolean("ISSERVICE",item.isService);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }


    protected FrameLayout subActivityLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", "Base Menu");
        setContentView(R.layout.app_bar_main);
        subActivityLayout = (FrameLayout)findViewById(R.id.base_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Activity", "Base onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Activity", "Base onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.menu_search)
            return  true;

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("Activity", "Base onNavigationItemSelected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_products) {
            Fragment fragment = new CategoriesFragment();
            Bundle args = new Bundle();
            args.putBoolean("CategoryName",false);

            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_services) {
            Fragment fragment = new CategoriesFragment();
            Bundle args = new Bundle();
            args.putBoolean("CategoryName", true);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if(id == R.id.nav_sell_service || id == R.id.nav_sell_product){
            Log.d("Activity", "Base Sell product or service");
            Intent intent = new Intent(this, SellNewActivity.class);
            intent.putExtra("fragment", id);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else if(id==R.id.nav_cart){
            Fragment fragment = new MyCartFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else if(id==R.id.nav_offered_by_me) {
            Intent intent = new Intent(this, OfferedByMeActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_my_orders){
            Intent intent = new Intent(this, MyOrderActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_account){

        }else if(id==R.id.nav_password){

        }else if(id==R.id.nav_logout){

        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
