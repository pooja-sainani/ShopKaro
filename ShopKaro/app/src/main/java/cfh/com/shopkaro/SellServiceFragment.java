package cfh.com.shopkaro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SellServiceFragment extends Fragment {

    private static View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        view = inflater.inflate(R.layout.sell_service_fragment, container, false);
        Spinner staticSpinner = (Spinner) view.findViewById(R.id.static_service_category_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this.getActivity(), R.array.service_categories, android.R.layout.simple_spinner_item);
        staticSpinner.setAdapter(staticAdapter);

        Button addServiceButton = (Button) view.findViewById(R.id.add_service_button);
        addServiceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new addNewServicePostTask().execute();
            }
        });
        return view;
    }




    private class addNewServicePostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return "abc";
        }

        @Override
        protected void onPostExecute(String result) {
            // something with data retrieved from server in doInBackground
        }

    }

}



