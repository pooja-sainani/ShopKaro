package cfh.com.shopkaro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SellServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_sell);

        Spinner staticSpinner = (Spinner) findViewById(R.id.static_service_category_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.service_categories, android.R.layout.simple_spinner_item);
        staticSpinner.setAdapter(staticAdapter);

        Button addServiceButton = (Button) findViewById(R.id.add_service_button);
        addServiceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new addNewServicePostTask().execute();
            }
        });

    }




    private class addNewServicePostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return "abc";
        }

        @Override
        protected void onPostExecute(String result) {
            // something with data retrieved from server in doInBackground
        }

    }

}



