package app.test.wyp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class HomeApp extends AppCompatActivity {

    private Toolbar toolbar;
    TextView txtExplorar;
    TextView txtUserMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeapp);

        txtUserMessage = findViewById(R.id.txtUserMessage);

        Bundle bundle = getIntent().getExtras();
        final String dato = bundle.getString("user_name").toString();
        txtUserMessage.setText("¡Hola, "+dato+"!");

        txtExplorar = findViewById(R.id.textView2);
        txtExplorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeApp.this, Explorar.class);
                intent.putExtra("user_name", dato);
                startActivity(intent);
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
