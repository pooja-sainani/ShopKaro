package cfh.com.shopkaro;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by shupa on 3/5/2016.
 */
public class MyOrderService extends Fragment {

    Button ClickMe;
    TextView tv;
    public static MyOrderService newInstance() {
        MyOrderService fragment = new MyOrderService();
        return fragment;
    }

    public MyOrderService() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myorderservices, container, false);


//        ClickMe = (Button) rootView.findViewById(R.id.button);
//      //  tv = (TextView) rootView.findViewById(R.id.textView2);
//
//        ClickMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(tv.getText().toString().contains("Hello")){
//                    tv.setText("Hi");
//                }else tv.setText("Hello");
//            }
//        });
        return rootView;
    }
} // This is the end of our MyFragments Class


