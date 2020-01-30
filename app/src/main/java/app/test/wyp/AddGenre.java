package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class AddGenre extends AppCompatActivity {

    Button btnSiguiente;
    ImageButton btnBack;
    CheckBox cbxmale;
    CheckBox cbxfemale;
    CheckBox cbxother;
    String sexo="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgenre);

        btnSiguiente = findViewById(R.id.bntSiguiente);
        btnBack = findViewById(R.id.btnBack);
        cbxmale = findViewById(R.id.cbxmale);
        cbxfemale = findViewById(R.id.cbxfemale);
        cbxother = findViewById(R.id.cbxother);

        Bundle bundle = getIntent().getExtras();
        final String number = bundle.getString("phone").toString();
        final String name= bundle.getString("user_name").toString();

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos(number, name, sexo);

                Intent intent = new Intent(AddGenre.this, HomeApp.class);
                intent.putExtra("phone", number);
                intent.putExtra("user_name", name);
                intent.putExtra("genre", sexo);

                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGenre.this, AddName.class);
                startActivity(intent);
            }
        });

        cbxmale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbxfemale.setChecked(false);
                    cbxother.setChecked(false);
                    sexo = "Masculino";
                }
            }
        });

        cbxfemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbxmale.setChecked(false);
                    cbxother.setChecked(false);
                    sexo = "Femenino";
                }
            }
        });

        cbxother.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbxfemale.setChecked(false);
                    cbxmale.setChecked(false);
                    sexo = "LGBTI/Otro";
                }
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void guardarDatos(final String number, final String name, final String sexo) {

        //URL url= new URL("https://db-node-api.herokuapp.com/api/users");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://db-node-api.herokuapp.com/api/user");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_name", name);
                    jsonParam.put("genre", sexo);
                    jsonParam.put("phone", number);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream localDataOutputStream = new DataOutputStream(conn.getOutputStream());
                    localDataOutputStream.writeBytes(jsonParam.toString());
                    localDataOutputStream.flush();
                    localDataOutputStream.close();


                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }




}
