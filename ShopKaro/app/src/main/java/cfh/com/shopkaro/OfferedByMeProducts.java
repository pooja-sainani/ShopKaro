package cfh.com.shopkaro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pooja on 3/5/2016.
 */
public class OfferedByMeProducts extends Fragment {

    public static OfferedByMeProducts newInstance() {
        OfferedByMeProducts fragment = new OfferedByMeProducts();
        return fragment;
    }

    public OfferedByMeProducts() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.offeredbymeproduct, container, false);


        return rootView;
    }

}
