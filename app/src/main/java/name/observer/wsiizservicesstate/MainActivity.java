package name.observer.wsiizservicesstate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import name.observer.wsiizservicesstate.Activities.BBActivity;
import name.observer.wsiizservicesstate.Activities.FTPActivity;
import name.observer.wsiizservicesstate.Activities.WUActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
