package app.test.wyp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class More extends AppCompatActivity {

    ImageButton btnSearch;
    ImageButton btnExplore;
    ImageButton btnMap;
    Button btnFb;
    Button btnIg;
    private String facebookId   ="fb://page/111183350431897";
    private String facebookUrl  ="https://www.facebook.com/wypapp/";
    private String instagramUrl = "https://www.instagram.com/wyp_ec/";
    private String instagramId = "ig://page/28693380083";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        btnSearch = findViewById(R.id.btnSearch);
        btnExplore = findViewById(R.id.btnExplore);
        btnMap = findViewById(R.id.btnFindMap);
        btnFb = findViewById(R.id.btnFb);
        btnIg = findViewById(R.id.btnIg);

        Bundle bundle = getIntent().getExtras();
        final String dato = bundle.getString("user_name").toString();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, Search.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, MapsActivity.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, Explorar.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                }

            }
        });

        btnIg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instagramId)));
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl)));
                }
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
