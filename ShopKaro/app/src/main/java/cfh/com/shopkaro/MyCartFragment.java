package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cfh.com.shopkaro.dummy.CartContent;
import cfh.com.shopkaro.dummy.CategoriesContent;
import cfh.com.shopkaro.dummy.ProductsInOrder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCartFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnListFragmentInteractionListener mListener;

    private ProgressDialog pDialog;
    private ProgressDialog pDialogCart;
    private static View view;
    private static  RecyclerView recyclerView;
    private int mColumnCount = 1;
    public Context context;
    public static MyCartRecyclerViewAdapter objectAdapter;
    public Button buyButton;
    String email;
    private TextView tvTotalPrice;

    public MyCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CartContent.ITEMS.clear();

        view = inflater.inflate(R.layout.fragment_mycart_list, container, false);
//        View footerView = inflater.inflate(R.layout.footer_cart, null);
//        ListView listview = (ListView) view.findViewById(R.id.list);
//        listview.addFooterView(footerView);

       // buyButton =(Button)view.findViewById(R.id.ButtonAddToCart);
        tvTotalPrice = (TextView) view.findViewById(R.id.total_price_value);
        buyButton=(Button)view.findViewById(R.id.buy);
        // Set the adapter
       // if (view instanceof RecyclerView) {
             context = view.getContext();
        SharedPreferences sp = context.getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
        email = sp.getString("userid", null);
        new CartTask().execute();
         Log.e("ID FOR USER",email);
            recyclerView = (RecyclerView) view.findViewById(R.id.cartlist);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
               new BuyTask().execute();
                      Intent thankYouIntent = new Intent(getActivity(), LastPage.class);

                      startActivity(thankYouIntent);

            }
        });

        return view;
       // return inflater.inflate(R.layout.fragment_mycart_list, container, false);
    }

    public  void removeProductFromCart(String id){
        new RemoveFromCartTask().execute(id);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(CartContent.CartItem uri) {
        if (mListener != null) {
            mListener.onListFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public class CartTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  displayProgressBar("Downloading...");
//            pDialog = new ProgressDialog(getActivity());
//            pDialog.setMessage("Loading...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            Log.e("ASHU", "CALL");
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Cart/GetProductsForCart/"+email);
            // String text = null;
            //172.168.129.103
           // Log.e("ASHU after Http get","http://shopkaroapi.azurewebsites.net/api/Cart/GetProductsForCart/"+email);
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
           // pDialog.dismiss();
            try {
                CartContent.ITEMS.clear();
                Log.e("FINALASHU", result);
                Double totalprice= 0.0;
                JSONArray json = new JSONArray(result);
                JSONObject jobj=null;
                CategoriesContent.COUNT=json.length();
                for(int i=0;i<json.length();i++){
                    jobj=(JSONObject) json.get(i);
                    CartContent.CartItem item= new CartContent.CartItem(jobj.getString("ID"),jobj.getString("ProductName"),jobj.getString("PRODUCTID"),jobj.getInt("QUANTITY"),jobj.getDouble("Price"),jobj.getString("BUYERID"),jobj.getInt("QUANTITY")*jobj.getDouble("Price"));
                    totalprice= totalprice+(jobj.getInt("QUANTITY")*jobj.getDouble("Price"));
                    CartContent.ITEMS.add(item);
                }
                tvTotalPrice.setText(Double.toString(totalprice));
                //objectAdapter = new MyCartRecyclerViewAdapter(CartContent.ITEMS, mListener);
//                recyclerView.setAdapter(new MyCartRecyclerViewAdapter(CartContent.ITEMS, mListener));
                recyclerView.setAdapter( new MyCartRecyclerViewAdapter(CartContent.ITEMS, mListener));
                //list.setAdapter(adapter);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }

    private class RemoveFromCartTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  displayProgressBar("Downloading...");

//            pDialogCart = new ProgressDialog(context.);
//
//            pDialogCart.setMessage("Adding to Cart...");
//            pDialogCart.setIndeterminate(false);
//            pDialogCart.setCancelable(false);
//            pDialogCart.show();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
            String id = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            Log.e("ASHU", "CALL");
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Cart/DeleteProductFromCart/"+id);//A5396330-31CB-43FB-B98C-E6E8CDE97F0C");
            // String text = null;
            //172.168.129.103
            Log.e("DELETE CALL","http://shopkaroapi.azurewebsites.net/api/Cart/DeleteProductFrmCart/"+id);
            Log.e("ASHU","AFTERCALL");
            try {

                HttpResponse response = httpClient.execute(httpGet, localContext);
                Log.e("ASHU", "AFTERRESPONSE");
                HttpEntity entity = response.getEntity();
                Log.e("ASHU", "AFTERENTITY");
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
           //pDialogCart.dismiss();
            Log.e("Delete REsult",result);
            new CartTask().execute();
        }
    }

    private class BuyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  displayProgressBar("Downloading...");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Adding to Cart...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();


            //System.out.println("HELLO");
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Orders/AddOrder");

            try {
                // Add your data
                SharedPreferences sp = getContext().getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
                String email = sp.getString("userid", null);
                Log.e("ASHU","abcdf");
                System.out.println("HELLO1");
                List<ProductsInOrder> nameValuePairsGlobal = new ArrayList<ProductsInOrder>();
                //  String img_Str = params[0];
                JSONArray jArray = new JSONArray();
                try {
                    for (int i = 0; i < CartContent.ITEMS.size(); i++) {
                        JSONObject jGroup = new JSONObject();
                        jGroup.put("BUYERID",email);
                        jGroup.put("Price", Double.toString(CartContent.ITEMS.get(i).Amount));
                        jGroup.put("PRODUCTID", CartContent.ITEMS.get(i).productId);
                        jGroup.put("QUANTITY", Integer.toString(CartContent.ITEMS.get(i).Quantity));
//                        ProductsInOrder pio = new ProductsInOrder();
//                        pio.setBUYERID(email);
//                        pio.setPrice((CartContent.ITEMS.get(i).Amount));
//                        pio.setPRODUCTID(CartContent.ITEMS.get(i).productId);
//                        pio.setQUANTITY(CartContent.ITEMS.get(i).Quantity);
//                     nameValuePairs.add(new BasicNameValuePair("PRODUCTID", CartContent.ITEMS.get(i).productId));
//                    nameValuePairs.add(new BasicNameValuePair("Price", Double.toString(CartContent.ITEMS.get(i).Amount)));
//                   nameValuePairs.add(new BasicNameValuePair("QUANTITY", Integer.toString(CartContent.ITEMS.get(i).Quantity)));
//                    nameValuePairs.add(new BasicNameValuePair("BUYERID", email));
                       // nameValuePairsGlobal.add(pio);
                        jArray.put(jGroup);
                    }
                }catch(Exception e){

                }

               // JSONArray jsArray = new JSONArray(nameValuePairsGlobal);
                StringEntity se = new StringEntity(jArray.toString());
                httppost.setHeader("Accept","application/json");
                httppost.setHeader("Content-type", "application/json");
                httppost.setEntity(se);//new UrlEncodedFormEntity(nameValuePairsGlobal)
                // Execute HTTP Post Request
                Log.e("BEFORE SENDING REQUEST",jArray.toString());
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                Log.e("ASHU","AFTERENTITY");
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "ok";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("Result after adding",result);
            pDialog.dismiss();
            // dismissProgressBar();
		      /*try {
		    	  Log.e("FINALASHU",result);
				JSONArray json = new JSONArray(result);
				JSONObject jobj=null;
				for(int i=0;i<json.length();i++){
					jobj=(JSONObject) json.get(i);
					Interest interest=new Interest(true,jobj.getInt("interestId") ,jobj.getString("interestName"));
					myInterest.add(interest);

				}

			//	list.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/


        }
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
        void onListFragmentInteraction(CartContent.CartItem item);
    }
}
