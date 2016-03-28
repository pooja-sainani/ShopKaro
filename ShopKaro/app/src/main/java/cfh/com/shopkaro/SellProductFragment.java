package cfh.com.shopkaro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import cfh.com.shopkaro.dummy.Product;
import cfh.com.shopkaro.dummy.Service;

public class SellProductFragment extends Fragment{

    private View view;

    private String photoID;

    ProgressBar bar;

    private Button addServiceButton;
    private Button takePictureButton;
    private Button uploadPictureButton;

    public SellProductFragment(){

    }

    private List<String > productCategoryIds = Arrays.asList("51989DDB-9888-41E1-8410-028A207AA0BA",
            "1E39BAC2-2E8D-4C79-A9F7-0E15D989D7F8", "4DCC7972-1D1E-4FE1-8D02-204D0B1A899D", "411204AA-1277-4997-8E65-2F763099E05C",
            "2284C49E-B628-4138-BF96-3D1B4D0B3806", "A0211A31-753F-471C-BF62-85BE714C2087", "C8A269B2-84BB-4964-A623-A311551E8EF9",
            "497A31E4-A6AF-4B9F-8D1E-CAB21EA6E842", "95C6D4CC-21D8-4E1F-8EE7-E1138C1DD07A");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        view = inflater.inflate(R.layout.sell_product_fragment, container, false);
        Spinner staticSpinner = (Spinner) view.findViewById(R.id.static_product_category_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this.getActivity(), R.array.product_categories, android.R.layout.simple_spinner_item);
        staticSpinner.setAdapter(staticAdapter);

        bar = (ProgressBar) view.findViewById(R.id.progressBar);

        addServiceButton = (Button) view.findViewById(R.id.add_product_button);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                String temp = ((EditText) view.findViewById(R.id.product_name)).getText().toString();
                if (temp != null) product.NAME = temp;
                Spinner spinner = (Spinner) view.findViewById(R.id.static_product_category_spinner);
                int selectedCategoryPosition = spinner.getSelectedItemPosition();
                product.CATEGORYID = productCategoryIds.get(selectedCategoryPosition);
                temp = ((EditText) view.findViewById(R.id.product_price)).getText().toString();
                Float tempf = 0.0f;
                if (!temp.isEmpty()) tempf = Float.parseFloat(temp);
                if (tempf != 0.0f) product.PRICE = tempf;
                temp = ((EditText) view.findViewById(R.id.product_details)).getText().toString();
                if (temp != null) product.DETAILS = temp;
                temp = ((EditText) view.findViewById(R.id.product_tag1)).getText().toString();
                if (temp != null) product.TAG1 = temp;
                temp = ((EditText) view.findViewById(R.id.product_tag2)).getText().toString();
                if (temp != null) product.TAG2 = temp;
                temp = ((EditText) view.findViewById(R.id.product_tag3)).getText().toString();
                if (temp != null) product.TAG3 = temp;
                Integer tempi = 0;
                temp = ((EditText) view.findViewById(R.id.product_quantiy_threshold)).getText().toString();
                tempi = Integer.parseInt(temp);
                if (tempi != 0) product.THRESHOLDQUANTITY = tempi;
                temp = ((EditText) view.findViewById(R.id.product_quantiy_available)).getText().toString();
                tempi = Integer.parseInt(temp);
                if (tempi != 0) product.QUANTITYAVAILABLE = tempi;
                if(photoID != null)
                    product.IMAGEURL = photoID;

                new addNewProductPostTask(product).execute();
            }
        });

        takePictureButton = (Button) view.findViewById(R.id.buttonPreview);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });
        uploadPictureButton = (Button) view.findViewById(R.id.buttonUpload);
        uploadPictureButton.setEnabled(false);
        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto(v);
            }
        });

        return view;
    }

    public void takePicture(View view) {
        Log.e("Method fragment", "takePicture");
        ((SellNewActivity)getActivity()).takePicture(view);
    }

    public void uploadPhoto(View view) {
        addServiceButton.setEnabled(false);
        takePictureButton.setEnabled(false);
        uploadPictureButton.setEnabled(false);
        Log.e("Method fragment", "uploadPhoto");
        photoID =  ((SellNewActivity) getActivity()).uploadPhoto(view);
    }

    private class addNewProductPostTask extends AsyncTask<String, Integer, String>{

        private final Product prod;

        public addNewProductPostTask(Product s){
            this.prod = s;
        }
        @Override
        protected String doInBackground(String... params) {
            Log.e("Log1", "here1");
            postData(prod);
            Log.e("Log2", "here2");
            return null;
        }

        private String postData(Product prod){
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Products/AddNewProduct");
                SharedPreferences sp = getActivity().getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
                String sellerid = sp.getString("userid", null);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("NAME", prod.NAME));
                nameValuePairs.add(new BasicNameValuePair("CATEGORYID", prod.CATEGORYID));
                nameValuePairs.add(new BasicNameValuePair("PRICE", ""+prod.PRICE));
                nameValuePairs.add(new BasicNameValuePair("DETAILS", prod.DETAILS));
                nameValuePairs.add(new BasicNameValuePair("TAG1", prod.TAG1));
                nameValuePairs.add(new BasicNameValuePair("TAG2", prod.TAG2));
                nameValuePairs.add(new BasicNameValuePair("TAG3", prod.TAG3));
                nameValuePairs.add(new BasicNameValuePair("SELLERID", sellerid));
                nameValuePairs.add(new BasicNameValuePair("QUANTITYAVAILABLE ", Integer.toString(prod.QUANTITYAVAILABLE)));
                nameValuePairs.add(new BasicNameValuePair("THRESHOLDQUANTITY ", Integer.toString(prod.THRESHOLDQUANTITY)));
                nameValuePairs.add(new BasicNameValuePair("IMAGEURL", prod.IMAGEURL));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.e("Lothre3", ""+prod.THRESHOLDQUANTITY);
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



