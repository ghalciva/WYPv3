package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AddName extends AppCompatActivity {

    Button btnSiguiente;
    ImageButton btnBack;
    TextView txtaddname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addname);

        btnSiguiente = findViewById(R.id.bntSiguiente);
        btnBack = findViewById(R.id.btnBack);
        txtaddname = findViewById(R.id.txtaddphone);

        Bundle bundle = getIntent().getExtras();
        final String number = bundle.getString("phone");

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = number;
                String name = txtaddname.getText().toString().trim();
                comprobarCampo(phone, name);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddName.this, AddPhone.class);
                startActivity(intent);
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    private void comprobarCampo(String phone, String name){

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            /**Envia el error a la caja de Texto*/
            txtaddname.setError("");
            txtaddname.setError("Ingrese un nombre");
            focusView = txtaddname;
            cancel = true;
        }

        if (!TextUtils.isEmpty(name)){
            Intent intent = new Intent(AddName.this, AddGenre.class);
            intent.putExtra("phone", phone);
            intent.putExtra("user_name", name);
            startActivity(intent);
        }

    }
}
