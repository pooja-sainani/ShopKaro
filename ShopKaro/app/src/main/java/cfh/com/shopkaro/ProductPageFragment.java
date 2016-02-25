package cfh.com.shopkaro;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static  String Id;
    private ProgressDialog pDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ImageButton addToCart;

    private OnFragmentInteractionListener mListener;

    public ProductPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductPageFragment newInstance(String param1, String param2) {
        ProductPageFragment fragment = new ProductPageFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        Id=bundle.getString("ProductId");


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            new ProductTask().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_product_page, container, false);
        addToCart =(ImageButton)rootView.findViewById(R.id.ButtonAddToCart);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {

//                      Intent productDetailsIntent = new Intent(getActivity(), MyCartFragment.class);
//                  productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
//                      startActivity(productDetailsIntent);
                Log.e("POOJA","Inside Listeners");
                new AddToCartTask().execute();//text, String.valueOf(interestId),image_str
            }
        });

     //   return inflater.inflate(R.layout.fragment_product_page, container, false);
        return  rootView;

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
            Log.e("ASHU","CALL");
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Products/GetProductById/"+Id);

            Log.e("AMruths","http://shopkaroapi.azurewebsites.net/api/Products/GetProductById/"+Id);
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
               // JSONArray json = new JSONArray(result);
                JSONObject jobj=(JSONObject)new JSONObject(result);
                //for(int i=0;i<json.length();i++){
                   // jobj=(JSONObject) json.get(i);
                    TextView pTitle=(TextView) getView().findViewById(R.id.TextViewProductTitle);
                    TextView pDetails=(TextView) getView().findViewById(R.id.TextViewProductDetails);
                    TextView pTags=(TextView) getView().findViewById(R.id.TextViewProductTags);
                    TextView pPrice=(TextView) getView().findViewById(R.id.TextViewProductPrice);
                    pTitle.setText(jobj.getString("NAME"));
                    pDetails.setText(jobj.getString("DETAILS"));
                    pTags.setText(jobj.getString("TAG1")+" "+jobj.getString("TAG2")+" "+jobj.getString("TAG3"));
                    pPrice.setText(jobj.getString("PRICE"));
                    Log.e("POOJA-AMRUTHA","After Call Products");
                    //Landing news=new Landing(jobj.getString("firstName")+" "+jobj.getString("lastName"),jobj.getString("pic") ,jobj.getString("dateTime"), jobj.getString("text"),jobj.getString("imageName"));
                   // myCars.add(news);

             //   }

                //list.setAdapter(adapter);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }


    private class AddToCartTask extends AsyncTask<String, Integer, String> {
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
            HttpPost httppost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Cart/AddProductToCart");

            try {
                // Add your data

                    Log.e("ASHU","abcdf");
                    System.out.println("HELLO1");
                  //  String img_Str = params[0];
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("PRODUCTID", Id));
                    nameValuePairs.add(new BasicNameValuePair("Quantity", "1"));
                    nameValuePairs.add(new BasicNameValuePair("BUYERID", "A5396330-31CB-43FB-B98C-E6E8CDE97F0C"));

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "OK";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
