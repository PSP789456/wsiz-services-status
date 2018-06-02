package name.observer.wsizserversstatus;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import name.observer.wsizserversstatus.Activities.BBActivity;
import name.observer.wsizserversstatus.Activities.FTPActivity;
import name.observer.wsizserversstatus.Activities.KandydaciActivity;
import name.observer.wsizserversstatus.Activities.WSIZActivity;
import name.observer.wsizserversstatus.Activities.WUActivity;
import name.observer.wsizserversstatus.Helpers.CheckNetwork;

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

        final Button bBUS = findViewById(R.id.bBUS);
        bBUS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            if (CheckNetwork.isInternetAvailable(MainActivity.this)) {
                bBUS.setText(R.string.BUS);
                new MainActivity.JSONTask().execute("https://observer.name/api/wsiz");
            } else {
                bBUS.setText("No internet");
            }
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentArray = parentObject.getJSONObject("wsizbus");
                String URL = parentArray.getString("url");

                return URL;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            DownloadManager downloadManager;
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(result);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long reference = downloadManager.enqueue(request);
        }
    }
}
