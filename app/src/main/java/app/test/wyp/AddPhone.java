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

public class AddPhone extends AppCompatActivity {

    Button btnSiguiente;
    ImageButton btnBack;
    TextView txtaddphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphone);

        btnSiguiente = findViewById(R.id.bntSiguiente);
        btnBack = findViewById(R.id.btnBack);
        txtaddphone = findViewById(R.id.txtaddphone);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPhone.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    //            String code= "593";

                String number = txtaddphone.getText().toString().trim();
                comprobarCampo(number);
/*
                if (number.isEmpty() || number.length() < 10) {
                    txtaddphone.setError("Ingrese un número de teléfono válido");
                    txtaddphone.requestFocus();
                    return;
                }
*/

                //String phonenumber = "+" + code + number;

            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    private void comprobarCampo(String number){

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(number)) {
            /**Envia el error a la caja de Texto*/
            txtaddphone.setError("");
            txtaddphone.setError("Ingrese un número de teléfono");
            focusView = txtaddphone;
            cancel = true;
        }

        if (!TextUtils.isEmpty(number)){
            Intent intent = new Intent(AddPhone.this, AddName.class);
            intent.putExtra("phone", number);
            startActivity(intent);
        }

    }

}
