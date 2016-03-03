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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cfh.com.shopkaro.dummy.Service;

public class SellServiceFragment extends Fragment{

    private View view;

    public SellServiceFragment(){

    }


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

        final Button addServiceButton = (Button) view.findViewById(R.id.add_service_button);
        addServiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Service service = new Service();
                String temp = ((EditText)view.findViewById(R.id.service_name)).getText().toString();
                if(temp != null)    service.NAME = temp;
                service.CATEGORYID = UUID.fromString("E0C71922-1EAC-451C-9184-E980C1D6A679");
                service.SELLERID = UUID.fromString("A5396330-31CB-43FB-B98C-E6E8CDE97F0C");
                temp = ((EditText)view.findViewById(R.id.service_price)).getText().toString();
                Float tempf =  0.0f;
                if(!temp.isEmpty())    tempf = Float.parseFloat(temp);
                if(tempf != 0.0f)  service.PRICE = tempf;
                temp = ((EditText)view.findViewById(R.id.service_details)).getText().toString();
                if(temp != null)    service.DETAILS = temp;
                temp = ((EditText)view.findViewById(R.id.service_tag1)).getText().toString();
                if(temp != null)   service.TAG1 = temp;
                temp = ((EditText)view.findViewById(R.id.service_tag2)).getText().toString();
                if(temp != null)   service.TAG2 = temp;
                temp = ((EditText)view.findViewById(R.id.service_tag3)).getText().toString();
                if(temp != null)   service.TAG3 = temp;
                temp = ((EditText)view.findViewById(R.id.service_place)).getText().toString();
                if(temp != null)   service.PLACE = temp;
                temp = ((EditText)view.findViewById(R.id.service_city)).getText().toString();
                if(temp != null)   service.CITY = temp;
                temp = ((EditText)view.findViewById(R.id.service_state)).getText().toString();
                if(temp != null)   service.STATE = temp;
                Integer tempi = 0;
                temp = ((EditText) view.findViewById(R.id.service_pincode)).getText().toString();
                tempi = Integer.parseInt(temp);
                if(tempi != 0)   service.PINCODE = tempi;

                new addNewServicePostTask(service).execute();
            }
        });
        return view;
    }




    private class addNewServicePostTask extends AsyncTask<String, Integer, String>{

        private final Service serv;

        public addNewServicePostTask(Service s){
            this.serv = s;
        }
        @Override
        protected String doInBackground(String... params) {
            Log.e("Log1", "here1");
            postData(serv);
            Log.e("Log2", "here2");
            return null;
        }

        private String postData(Service serv){
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Services/AddNewService");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("NAME", serv.NAME));
                nameValuePairs.add(new BasicNameValuePair("CATEGORYID", serv.CATEGORYID.toString()));
                nameValuePairs.add(new BasicNameValuePair("PRICE", ""+serv.PRICE));
                nameValuePairs.add(new BasicNameValuePair("DETAILS", serv.DETAILS));
                nameValuePairs.add(new BasicNameValuePair("TAG1", serv.TAG1));
                nameValuePairs.add(new BasicNameValuePair("TAG2", serv.TAG2));
                nameValuePairs.add(new BasicNameValuePair("TAG3", serv.TAG3));
                nameValuePairs.add(new BasicNameValuePair("SELLERID", serv.SELLERID.toString()));
                nameValuePairs.add(new BasicNameValuePair("PLACE",serv.PLACE));
                nameValuePairs.add(new BasicNameValuePair("CITY",serv.CITY));
                nameValuePairs.add(new BasicNameValuePair("STATE", serv.STATE));
                nameValuePairs.add(new BasicNameValuePair("PINCODE", ""+serv.PINCODE));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.e("Log3", "here3");
                // Execute HTTP Post Request
                HttpResponse response = httpClient.execute(httpPost);
                Log.e("Log4", "here4");

                return  "OK";
            }
            catch (Exception e){
                Log.d("ERROR",e.getLocalizedMessage());
                return e.getLocalizedMessage();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dismissProgressBar();
            getActivity().finish();
        }


    }

}



