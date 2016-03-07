package cfh.com.shopkaro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pooja on 3/5/2016.
 */
public class ServicesOfferedByMeFragment extends Fragment {

    public static ServicesOfferedByMeFragment newInstance() {
        ServicesOfferedByMeFragment fragment = new ServicesOfferedByMeFragment();
        return fragment;
    }

    public ServicesOfferedByMeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.offeredbymeservice, container, false);


        return rootView;
    }

}
