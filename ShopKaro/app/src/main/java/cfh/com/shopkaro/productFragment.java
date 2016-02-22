package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

import cfh.com.shopkaro.dummy.ProductContent;
import cfh.com.shopkaro.dummy.ProductContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class productFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ProgressDialog pDialog;
    private static View view;
    private static  RecyclerView recyclerView;

    private static String category;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public productFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static productFragment newInstance(int columnCount) {
        productFragment fragment = new productFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        category=bundle.getString("CategoryName");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProductContent.ITEMS.clear();
        new ProductTask().execute();
        view = inflater.inflate(R.layout.fragment_products_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            recyclerView.setAdapter(new MyCategoriesRecyclerViewAdapter(CategoriesContent.ITEMS, mListener));
        }




    return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    private class ProductTask extends AsyncTask<String, Integer, String> {
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
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Products/GetAllProductsByCategory/" + category);
            Log.e("POOJA - BEFORE CALL","http://shopkaroapi.azurewebsites.net/api/Products/GetAllProductsByCategory/" + category);
            // String text = null;
            //172.168.129.103
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
                ProductContent.COUNT=json.length();
                for(int i=0;i<json.length();i++){
                    jobj=(JSONObject) json.get(i);
                    ProductContent.DummyItem item= new ProductContent.DummyItem(jobj.getString("ID"),jobj.getString("NAME"),jobj.getDouble("PRICE"),jobj.getString("TAG1")+" "+jobj.getString("TAG2")+" "+jobj.getString("TAG3"));
//                    Landing news=new Landing(jobj.getString("firstName")+" "+jobj.getString("lastName"),jobj.getString("pic") ,jobj.getString("dateTime"), jobj.getString("text"),jobj.getString("imageName"));
//                    myCars.add(news);
                    ProductContent.ITEMS.add(item);
                }
                recyclerView.setAdapter(new MyproductRecyclerViewAdapter(ProductContent.ITEMS, mListener));
                //list.setAdapter(adapter);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }

}
