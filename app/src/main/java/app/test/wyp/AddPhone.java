package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= "593";
                String number = txtaddphone.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    txtaddphone.setError("Ingrese un número de teléfono válido");
                    txtaddphone.requestFocus();
                    return;
                }

                String phonenumber = "+" + code + number;

                Intent intent = new Intent(AddPhone.this, AddCodeVerify.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPhone.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, HomeApp.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

}
