package app.test.wyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Evento extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private static String url = "https://db-node-api.herokuapp.com/api/events";
    private ProgressDialog pDialog;
    private ListView lv;
    TextView backIcon;
    //TextView txtCounter;
    //private int counter = 0;

    ArrayList<HashMap<String, String>> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        eventList = new ArrayList<>();
        backIcon = findViewById(R.id.back_icon);
        lv = findViewById(R.id.list);
        //txtCounter = findViewById(R.id.counter);
        //txtCounter.setText(counter);

        new GetEvents().execute();

        Bundle bundle = getIntent().getExtras();
        final String dato = bundle.getString("user_name").toString();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Evento.this, Explorar.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

    }

    private class GetEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Evento.this);
            pDialog.show();
            pDialog.setMessage("Please wait...");

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.i(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray events = new JSONArray(jsonStr);

                    // looping through All Events
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject c = events.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("event_name");
                        String date = c.getString("event_date");
                        String address = c.getString("event_address");
                        String description = c.getString("event_description");
                        String host = c.getString("event_host");

                        // tmp hash map for single event
                        HashMap<String, String> event = new HashMap<>();

                        // adding each child node to HashMap key => value
                        event.put("id", id);
                        event.put("event_name", name);
                        event.put("event_date", date);
                        event.put("event_address", address);
                        event.put("event_description",description);
                        event.put("event_host",host);

                        // adding contact to contact list
                        eventList.add(event);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server. No internet connection!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. No internet connection!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    Evento.this, eventList,
                    R.layout.list_item, new String[]{"event_name", "event_date", "event_address",
                    "event_description", "event_host"}, new int[]{R.id.name,
                    R.id.date, R.id.address, R.id.description, R.id.host});

            lv.setAdapter(adapter);
        }

    }

}
