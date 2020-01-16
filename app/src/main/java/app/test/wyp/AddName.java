package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddName.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void saveName(){
        String name = txtaddname.getText().toString();


        Intent intent = new Intent(AddName.this, AddGenre.class);
        startActivity(intent);
    }

}
