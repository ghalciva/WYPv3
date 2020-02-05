package app.test.wyp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class AddGenre extends AppCompatActivity {

    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "app.test.datasaved";
    private BroadcastReceiver broadcastReceiver;
    private DatabaseHelper db;

    private List<ModelUser> users;

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

        users = new ArrayList<>();

        btnSiguiente = findViewById(R.id.bntSiguiente);
        btnBack = findViewById(R.id.btnBack);
        cbxmale = findViewById(R.id.cbxmale);
        cbxfemale = findViewById(R.id.cbxfemale);
        cbxother = findViewById(R.id.cbxother);

        db = new DatabaseHelper(this);

        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(),"in the receiver",Toast.LENGTH_LONG).show();
            }
        };
        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        unregisterReceiver(broadcastReceiver);

        Bundle bundle = getIntent().getExtras();
        final String number = bundle.getString("phone").toString();
        final String name= bundle.getString("user_name").toString();

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cbxmale.isChecked() || cbxfemale.isChecked() || cbxother.isChecked()){
                    guardarDatos(number, name, sexo);

                    Intent intent = new Intent(AddGenre.this, HomeApp.class);
                    intent.putExtra("phone", number);
                    intent.putExtra("user_name", name);
                    intent.putExtra("genre", sexo);

                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Seleccione una opción para continuar", Toast.LENGTH_LONG).show();
                }
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

        /*Thread thread = new Thread(new Runnable() {
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
        */

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando sus datos...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        saveNameToLocalStorage(number, name, sexo, NAME_NOT_SYNCED_WITH_SERVER);
        progressDialog.dismiss();

    }

    //saving the name to local storage
    private void saveNameToLocalStorage(String phone, String name, String genre, int status) {
        db.addName(phone, name, genre, status);
        ModelUser n = new ModelUser(phone,name, genre,status);
        users.add(n);
    }



}
