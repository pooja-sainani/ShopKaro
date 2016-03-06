package cfh.com.shopkaro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shupa on 3/5/2016.
 */
public class MyOrderProduct extends Fragment {


    public static MyOrderProduct newInstance() {
        MyOrderProduct fragment = new MyOrderProduct();
        return fragment;
    }

    public MyOrderProduct() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myorderproduct, container, false);



        return rootView;
    }
} // This is the end of our MyFragments Class


