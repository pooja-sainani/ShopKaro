package cfh.com.shopkaro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pooja on 3/5/2016.
 */
public class ProductsOfferedByMeFragment extends Fragment {

    public static ProductsOfferedByMeFragment newInstance() {
        ProductsOfferedByMeFragment fragment = new ProductsOfferedByMeFragment();
        return fragment;
    }

    public ProductsOfferedByMeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.offeredbymeproduct, container, false);


        return rootView;
    }

}
