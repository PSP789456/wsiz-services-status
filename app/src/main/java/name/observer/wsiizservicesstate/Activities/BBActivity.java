package name.observer.wsiizservicesstate.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import name.observer.wsiizservicesstate.R;

public class BBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Stan Blackboard'a");

        new JSONTask().execute("https://observer.name/api/wsiz");
    }

    public void Update() {
        new BBActivity.JSONTask().execute("https://observer.name/api/wsiz");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu. refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id. menuRefresh :
                Update();
                return true;
            default :
                return super .onOptionsItemSelected(item);
        }
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
                JSONObject parentArray = parentObject.getJSONObject("bb");
                String Status = parentArray.getString("status");
                int Uptime = parentArray.getInt("uptime");

                final int Hours_In_A_Day = 24;
                final int Minutes_In_A_Hour = 60;
                final int Seconds_In_A_Minute = 60;
                int minutes = Uptime / Seconds_In_A_Minute;
                Uptime -= minutes * Seconds_In_A_Minute;
                int hours = minutes / Minutes_In_A_Hour;
                minutes -= hours * Minutes_In_A_Hour;
                int days = hours / Hours_In_A_Day;
                hours -= days * Hours_In_A_Day;

                return Status.toString() + "\n\n" + days + " days " + hours + " hours " + minutes + " minutes " + Uptime + " seconds";
                //return (new SimpleDateFormat("HH 'hours', mm 'mins,' ss 'seconds'")).format(new Date(Uptime));
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
            super.onPostExecute(result);
            TextView ShowData = findViewById(R.id. ShowData);
            ShowData.setText(result);
        }
    }
}
