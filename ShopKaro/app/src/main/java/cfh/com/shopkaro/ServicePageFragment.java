package cfh.com.shopkaro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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

public class ServicePageFragment extends Fragment {

    private static  String Id;
    private ProgressDialog pDialog;
    private ImageButton subscribeService;

    private OnFragmentInteractionListener mListener;

    public ServicePageFragment() {
        // Required empty public constructor
    }

    public static ServicePageFragment newInstance(String param1) {
        ServicePageFragment fragment = new ServicePageFragment();
        Bundle args = new Bundle();
        args.putString(Id, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        Id=bundle.getString("Id");
        if (getArguments() != null) {
            new GetServiceTask().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_service_page, container, false);
        subscribeService = (ImageButton) rootview.findViewById(R.id.ButtonSubscribeService);
        subscribeService.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.e("Activity", "Service page subscribe button click");
                new SubscribeToServiceTask().execute();
            }
        });
        return  rootview;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class GetServiceTask extends AsyncTask<String, Integer, String> {
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
            HttpGet httpGet = new HttpGet("http://shopkaroapi.azurewebsites.net/api/Services/GetServiceById/"+Id);

            Log.e("AMruths","http://shopkaroapi.azurewebsites.net/api/Services/GetServiceById/"+Id);
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
                JSONObject jobj=(JSONObject)new JSONObject(result);
                TextView pTitle=(TextView) getView().findViewById(R.id.TextViewServiceTitle);
                TextView pDetails=(TextView) getView().findViewById(R.id.TextViewServiceDetails);
                TextView pTags=(TextView) getView().findViewById(R.id.TextViewServiceTags);
                TextView pPrice=(TextView) getView().findViewById(R.id.TextViewServicePrice);
                TextView pLocation=(TextView) getView().findViewById(R.id.TextViewServiceLocation);
                pTitle.setText(jobj.getString("NAME"));
                pDetails.setText(jobj.getString("DETAILS"));
                String tags = "";
                String temp = jobj.getString("TAG1");
                if(temp != null && !temp.isEmpty()){
                    tags += "#"+ temp;
                }
                temp = jobj.getString("TAG2");
                if(temp != null && !temp.isEmpty()){
                    tags += "#"+ temp;
                }
                temp = jobj.getString("TAG3");
                if(temp != null && !temp.isEmpty()){
                    tags += "#"+ temp;
                }
                pTags.setText(tags);
                pPrice.setText(jobj.getString("PRICE"));
                pLocation.setText(jobj.getString("PLACE")+", "+ jobj.getString("CITY"));
                Log.e("POOJA-AMRUTHA","After Call Service");
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


    private class SubscribeToServiceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  displayProgressBar("Downloading...");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Subscribing to service...");
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
            HttpPost httppost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Orders/SubscribeService");

            try {
                // Add your data
                SharedPreferences sp = getContext().getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
                String userid = sp.getString("userid", null);
                Log.e("ASHU","abcdf");
                System.out.println("HELLO1");
                //  String img_Str = params[0];
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("SERVICEID", Id));
                nameValuePairs.add(new BasicNameValuePair("BUYERID", userid));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
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
        }
    }


}

