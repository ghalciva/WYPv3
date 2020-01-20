package app.test.wyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSiguiente = findViewById(R.id.bntSiguiente);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPhone.class);
                startActivity(intent);
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            String link="https://vod-progressive.akamaized.net/exp=1579531839~acl=%2A%2F452088437.mp4%2A~hmac=a4971705c1df7b6830c45b6a009a1200fc3c22585cbaf99f55c9be34ecb695ca/vimeo-prod-skyfire-std-us/01/4722/5/148614385/452088437.mp4";
            VideoView videoView = findViewById(R.id.videoView);
            MediaController mediaController = new MediaController(this);
            //mediaController.setAnchorView(videoView);
            Uri video = Uri.parse(link);
            videoView.setMediaController(null);
            videoView.setVideoURI(video);
            videoView.start();
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
        }

    }


}
