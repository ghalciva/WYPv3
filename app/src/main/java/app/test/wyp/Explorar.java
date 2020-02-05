package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Explorar extends AppCompatActivity {

    TextView backIcon;
    Button btnIr;
    Button btnNoIr;
    ImageButton btnSearch;
    ImageButton btnMap;
    ImageButton btnMore;
    ImageView halloweenP;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        backIcon = findViewById(R.id.back_icon);
        btnSearch = findViewById(R.id.btnSearch);
        btnMap = findViewById(R.id.btnFindMap);
        btnIr = findViewById(R.id.btnIr);
        btnNoIr = findViewById(R.id.btnNoIr);
        btnMore = findViewById(R.id.btnMore);
        halloweenP = findViewById(R.id.halloweenP);

        Bundle bundle = getIntent().getExtras();
        final String dato = bundle.getString("user_name").toString();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explorar.this, Search.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explorar.this, HomeApp.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explorar.this, MapsActivity.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isPressed()){
                    Toast.makeText(getApplicationContext(), "Me interesa asistir a este evento", Toast.LENGTH_LONG).show();
                    btnIr.setEnabled(false);
                    counter = counter + 1;
                    saveLikes(counter);
                }
            }
        });

        btnNoIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isPressed()){
                    Toast.makeText(getApplicationContext(), "No me interesa asistir a este evento", Toast.LENGTH_LONG).show();
                    btnNoIr.setEnabled(false);
                }
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explorar.this, More.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        halloweenP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explorar.this, Evento.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void saveLikes(int counter){
        //save likes
    }

}
