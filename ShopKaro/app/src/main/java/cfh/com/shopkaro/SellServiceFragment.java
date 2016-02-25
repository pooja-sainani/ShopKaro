package cfh.com.shopkaro;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.UUID;

import cfh.com.shopkaro.dummy.Service;

public class SellServiceFragment extends Fragment{

    public SellServiceFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.sell_service_fragment, container, false);
        Spinner staticSpinner = (Spinner) view.findViewById(R.id.static_service_category_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this.getActivity(), R.array.service_categories, android.R.layout.simple_spinner_item);
        staticSpinner.setAdapter(staticAdapter);

        final Button addServiceButton = (Button) view.findViewById(R.id.add_service_button);
        addServiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addServiceButton.setText("blah");
                Service service = new Service();
                //EditText t = (EditText)view.findViewById(R.id.service_name);
                //Log.d("NAME", t.getText().toString());
                /*service.NAME = ((EditText)view.findViewById(R.id.service_name)).getText().toString();
                Log.d("NAME", service.NAME);
                service.CATEGORYID = UUID.fromString("E0C71922-1EAC-451C-9184-E980C1D6A679");
                Log.d("CATEGORYID",service.CATEGORYID.toString());
                service.PRICE = Float.parseFloat(((EditText) view.findViewById(R.id.service_price)).getText().toString());
                Log.d("PRICE",service.PRICE+"");
                service.DETAILS = ((EditText)view.findViewById(R.id.service_details)).getText().toString();
                System.out.println(service.DETAILS);
                service.TAG1 = ((EditText)view.findViewById(R.id.service_tag1)).getText().toString();
                service.TAG2 = ((EditText)view.findViewById(R.id.service_tag2)).getText().toString();
                service.TAG3 = ((EditText)view.findViewById(R.id.service_tag3)).getText().toString();
                service.PLACE = ((EditText)view.findViewById(R.id.service_price)).getText().toString();
                service.CITY = ((EditText)view.findViewById(R.id.service_city)).getText().toString();
                service.STATE = ((EditText)view.findViewById(R.id.service_state)).getText().toString();
                service.PINCODE = Integer.parseInt(((EditText) view.findViewById(R.id.service_pincode)).getText().toString());*/
                new addNewServicePostTask().execute();
            }
        });
        return view;
    }




    private class addNewServicePostTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            postData();
            return null;
        }

        private String postData(){
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Services/AddNewService");
                HttpParams params = new BasicHttpParams();
                params.setParameter("NAME", "N");
                params.setParameter("CATEGORYID", "E1797E9A-D938-4F3C-B70B-BE8105DB6273");
                params.setParameter("PRICE", 10);
                params.setParameter("SELLERID", "A5396330-31CB-43FB-B98C-E6E8CDE97F0C");
                params.setParameter("PLACE","GACHIBOWLI");
                params.setParameter("CITY","HYDERABAD");
                params.setParameter("STATE", "TELANGANA");
                params.setParameter("PINCODE", 680590);
                Log.d("HERE","POST");
          /*      params.setParameter("DETAILS", service.DETAILS);
                params.setParameter("TAG1", service.TAG1);
                params.setParameter("TAG2", service.TAG2);
                params.setParameter("TAG3", service.TAG3);
                params.setParameter("PLACE", service.PLACE);
                params.setParameter("CITY", service.CITY);
                params.setParameter("STATE", service.STATE);
                params.setParameter("PINCODE", service.PINCODE);*/
                httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                httpPost.setParams(params);
                HttpResponse response = httpClient.execute(httpPost);
                return  null;
            }
            catch (Exception e){
                Log.d("ERROR",e.getLocalizedMessage());
                return e.getLocalizedMessage();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  updateProgressBar(values[0]);
        }

    }

}



