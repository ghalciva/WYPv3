package app.test.wyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Search extends AppCompatActivity {

    ImageButton btnExplore;
    ImageButton btnSearch;
    ImageButton btnMap;
    ImageButton btnMore;
    ImageView fiestasCasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch = findViewById(R.id.btnSearch);
        btnExplore = findViewById(R.id.btnExplore);
        btnMap = findViewById(R.id.btnFindMap);
        btnMore = findViewById(R.id.btnMore);
        fiestasCasa = findViewById(R.id.fiestasCasa);

        Bundle bundle = getIntent().getExtras();
        final String dato = bundle.getString("user_name").toString();

        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, Explorar.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, MapsActivity.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, More.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        fiestasCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, Evento.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });


    }

}
