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

import cfh.com.shopkaro.dummy.OrderProductContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyOrderProductsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ProgressDialog pDialog;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyOrderProductsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyOrderProductsFragment newInstance(int columnCount) {
        MyOrderProductsFragment fragment = new MyOrderProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        OrderProductContent.ITEMS.clear();
        new OrderProductTask().execute();
        View view = inflater.inflate(R.layout.fragment_myorder_products_list, container, false);


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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(OrderProductContent.DummyItem item);
    }

    private class OrderProductTask extends AsyncTask<String, Integer, String> {
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
            String email = sp.getString("userid", null);
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Orders/GetAllProductOrdersByUser/"+ email);
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
                //CategoriesContent.COUNT=json.length();
                for(int i=0;i<json.length();i++){
                    jobj=(JSONObject) json.get(i);
                    OrderProductContent.DummyItem item= new OrderProductContent.DummyItem(jobj.getString("ID").replace("-",""),jobj.getString("TOTATPRICE"),jobj.getString("ORDERDATE"));
//                    Landing news=new Landing(jobj.getString("firstName")+" "+jobj.getString("lastName"),jobj.getString("pic") ,jobj.getString("dateTime"), jobj.getString("text"),jobj.getString("imageName"));
//                    myCars.add(news);
                    OrderProductContent.ITEMS.add(item);
                }
                recyclerView.setAdapter(new MyOrderProductsRecyclerViewAdapter(OrderProductContent.ITEMS, mListener));
                //list.setAdapter(adapter);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }

}
