package com.random.fact;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Home extends ActionBarActivity {
    TextView fact_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fact_container = (TextView) findViewById(R.id.fact_container);
        String url = "http://randomfunfacts.com/";
        new RetrieverTask().execute(url);

        final Button button = (Button) findViewById(R.id.refresh);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "http://randomfunfacts.com/";
                new RetrieverTask().execute(url);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class RetrieverTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            String content = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                content = client.execute(request, responseHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            fact_container.setText(result.substring(result.indexOf("strong")+10,result.indexOf("/strong")-5));
        }
    }
}



