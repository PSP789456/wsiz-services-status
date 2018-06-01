package name.observer.wsiizservicesstate;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import name.observer.wsiizservicesstate.Activities.BBActivity;
import name.observer.wsiizservicesstate.Activities.FTPActivity;
import name.observer.wsiizservicesstate.Activities.KandydaciActivity;
import name.observer.wsiizservicesstate.Activities.WSIZActivity;
import name.observer.wsiizservicesstate.Activities.WUActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        Button bWU = findViewById(R.id.bWU);
        bWU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WUActivity.class);
                startActivity(intent);
            }
        });

        Button bBB = findViewById(R.id.bBB);
        bBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BBActivity.class);
                startActivity(intent);
            }
        });

        Button bFTP = findViewById(R.id.bFTP);
        bFTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FTPActivity.class);
                startActivity(intent);
            }
        });

        Button bWSIZ = findViewById(R.id.bWSIZ);
        bWSIZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WSIZActivity.class);
                startActivity(intent);
            }
        });

        Button bKandydaci = findViewById(R.id.bKandydaci);
        bKandydaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KandydaciActivity.class);
                startActivity(intent);
            }
        });
    }
}
