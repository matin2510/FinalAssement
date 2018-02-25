package nyc.c4q.mustafizurmatin.finalassement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreedsActivity extends AppCompatActivity {

    private TextView welcomeTV;
    private static final String SHARED_PREFS_KEY = "sharedPrefsKey";
    HashMap<String, String> links;
    private ImageView terrier_Image, spaniel_Image, retriever_Image, poodle_Image;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);
        welcomeTV = (TextView) findViewById(R.id.welcomeTV);
        terrier_Image = (ImageView) findViewById(R.id.terrier_Image);
        spaniel_Image = (ImageView) findViewById(R.id.spaniel_Image);
        retriever_Image = (ImageView) findViewById(R.id.retriever_Image);
        poodle_Image = (ImageView) findViewById(R.id.poodle_Image);


        SharedPreferences shared = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String channel = (shared.getString("username", ""));
        String welcometext = getResources().getString(R.string.welcome_breedActivity) + " " + channel + "?";
        welcomeTV.setText(welcometext);
        getNames();

    }

    private void timer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Log.d("TIMERR", "run: ");
                setRandomImages(links);
            }
        }, 1000);
    }

    private void setRandomImages(HashMap<String, String> links) {


        for (Map.Entry<String, String> entry : links.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue();

            if (key.equals("terrier")) {
                Picasso.with(getApplicationContext()).load(value).fit().into(terrier_Image);
            }

            if (key.equals("spaniel")) {
                Picasso.with(getApplicationContext()).load(value).fit().into(spaniel_Image);
            }

            if (key.equals("retriever")) {
                Picasso.with(getApplicationContext()).load(value).fit().into(retriever_Image);
            }

            if (key.equals("poodle")) {
                Picasso.with(getApplicationContext()).load(value).fit().into(poodle_Image);
            }

        }

    }


    private void getNames() {
        List<String> breedList = new ArrayList<>();
        breedList.add("terrier");
        breedList.add("spaniel");
        breedList.add("retriever");
        breedList.add("poodle");
        int counter = 0;
        links = new HashMap<>();
        for (String st : breedList) {

            getRandomImage(st, counter);
            counter++;
        }

        timer();
    }

    public void getRandomImage(final String breed, final int counter) {
        String url = "https://dog.ceo/api/breed/" + breed + "/images/random";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    String link = "";
                    try {
                        JSONObject obj = new JSONObject(jsonData);
                        Log.d("RESULT", "onResponse: " + obj.get("message").toString());
                        link = obj.get("message").toString();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    links.put(breed, link);

                }
            }
        });


    }

    public void onClick(View view) {
        Intent intent = new Intent(this, DogsActivity.class);

        switch (view.getId()) {
            case R.id.terrier_holder:
                intent.putExtra("breed", "terrier");
                break;

            case R.id.spaniel_holder:
                intent.putExtra("breed", "spaniel");
                break;

            case R.id.retriever_holder:
                intent.putExtra("breed", "retriever");
                break;

            case R.id.poodle_holder:
                intent.putExtra("breed", "poodle");
                break;

        }
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "LOGOUT CLICKED", Toast.LENGTH_SHORT).show();
        sp = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("username", "");
        editor.apply();
        editor.commit();
        finish();
        return super.onOptionsItemSelected(item);
    }
}