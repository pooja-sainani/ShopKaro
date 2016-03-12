package cfh.com.shopkaro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cfh.com.shopkaro.dummy.OrderProductContent;
import cfh.com.shopkaro.dummy.Product;

/**
 * Created by Pooja on 3/5/2016.
 */
public class ProductsOfferedByMeFragment extends Fragment {

    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    private ProgressDialog pDialog;

    public static ProductsOfferedByMeFragment newInstance() {
        ProductsOfferedByMeFragment fragment = new ProductsOfferedByMeFragment();
        return fragment;
    }

    public ProductsOfferedByMeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment", "ONCREATE Products OfferedBy me");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new GetProductsOfferedByMeTask().execute();
        View view = inflater.inflate(R.layout.fragment_offeredbyme_productslist, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //   recyclerView.setAdapter(new MyOrderProductItemRecyclerViewAdapter(OrderProductContent.ITEMS, mListener));
        }
        return view;
    }

    private class GetProductsOfferedByMeTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  displayProgressBar("Downloading...");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            Log.e("ASHU", "CALL");
            SharedPreferences sp = getContext().getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
            String userid = sp.getString("userid", null);
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Products/GetProductsByOfferedByMe/"+ userid);
            Log.e("ASHU","AFTERCALL");
            try {

                HttpResponse response = httpClient.execute(httpGet, localContext);
                Log.e("ASHU","AFTERRESPONSE");
                HttpEntity entity = response.getEntity();
                Log.e("ASHU","AFTERENTITY");
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                //text = getASCIIContentFromEntity(entity);

            } catch (Exception e) {
                return e.getLocalizedMessage();

            }

            return builder.toString();

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dismissProgressBar();
            pDialog.dismiss();
            try {
                Log.e("FINALASHU", result);
                JSONArray json = new JSONArray(result);
                JSONObject jobj=null;
                List<Product> products = new ArrayList<Product>();
                for(int i=0;i<json.length();i++){
                    jobj=(JSONObject) json.get(i);
                    Product item= new Product(jobj.getString("NAME"), jobj.getDouble("AVGRATING"), jobj.getInt("QUANTITYAVAILABLE"),
                            jobj.getInt("QUANTITYSOLD"));

                    products.add(item);
                }
                recyclerView.setAdapter(new ProductsOfferedByMeRecyclerViewAdapter(products));
                //list.setAdapter(adapter);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }
}
