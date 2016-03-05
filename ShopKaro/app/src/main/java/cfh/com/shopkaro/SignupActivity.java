package cfh.com.shopkaro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    public Button btnSignUp;
    public ProgressDialog pDialog;
    private static  String email;
    private static String password;
    private static String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final String name = ((EditText) findViewById(R.id.input_name)).getText().toString();

        final String email = ((EditText) findViewById(R.id.input_email)).getText().toString();
        final String  password = ((EditText) findViewById(R.id.input_password)).getText().toString();

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
//                Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivity(signupIntent);
        new AddUserTask().execute(name,email,password);
            }
        });
    }

    private class AddUserTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  displayProgressBar("Downloading...");
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("Adding to Cart...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
             name = params[0];
             email = params[1];
             password = params[2];

            //System.out.println("HELLO");
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://shopkaroapi.azurewebsites.net/api/Users/AddNewUser");

            try {
                // Add your data

                Log.e("ASHU", "abcdf");
                System.out.println("HELLO1");
                //  String img_Str = params[0];
              //  List< List<NameValuePair>> test = new ArrayList<>();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair("USERNAME",email));
                nameValuePairs.add(new BasicNameValuePair("NAME", name));
                nameValuePairs.add(new BasicNameValuePair("PASSWORD", password));
                nameValuePairs.add(new BasicNameValuePair("EMAILID", email));

                nameValuePairs.add(new BasicNameValuePair("CONTACTNUMBER", "8796831922"));
                //nameValuePairs.add(new BasicNameValuePair("ROLE", "2"));
                nameValuePairs.add(new BasicNameValuePair("GENDER", "M"));
              //  nameValuePairs.add(new BasicNameValuePair("CONTACT", name));

                //nameValuePairs.add(new BasicNameValuePair("BUYERID", "A5396330-31CB-43FB-B98C-E6E8CDE97F0C"));

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
                return e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return e.getMessage();
            }

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("Result after adding", result);
            pDialog.dismiss();

            SharedPreferences sp = getBaseContext().getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username",email);
            editor.putString("userid",result);
            editor.putString("password",password);
            editor.commit();
            Intent signupIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(signupIntent);
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

}
