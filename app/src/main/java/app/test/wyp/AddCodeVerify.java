package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AddCodeVerify extends AppCompatActivity {

    Button btnSiguiente;
    ImageButton btnBack;
    TextView txtaddverifyphone;
    private String verificationid;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcodeverify);

        progressBar = findViewById(R.id.progressBar);
        String phonenumber = getIntent().getStringExtra("phonenumber");

        btnSiguiente = findViewById(R.id.bntSiguiente);
        btnBack = findViewById(R.id.btnBack);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String code = txtaddverifyphone.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    txtaddverifyphone.setError("Ingrese el código...");
                    txtaddverifyphone.requestFocus();
                    return;
                }*/
                Intent intent = new Intent(AddCodeVerify.this, AddName.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCodeVerify.this, AddPhone.class);
                startActivity(intent);
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


}

