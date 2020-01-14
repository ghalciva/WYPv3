package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class AddName extends AppCompatActivity {

    Button btnSiguiente;
    ImageButton btnBack;
    TextView txtaddname;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addname);

        mAuth = FirebaseAuth.getInstance();

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

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void saveName(){
        String name = txtaddname.getText().toString();

        if (name.isEmpty()){
            txtaddname.setError("Ingrese un nombre");
            txtaddname.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user!= null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.d("addName", "name registered");
                    }
                }
            });
        }

        Intent intent = new Intent(AddName.this, AddGenre.class);
        startActivity(intent);
    }

}
