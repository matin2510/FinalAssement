package nyc.c4q.mustafizurmatin.finalassement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private ImageView imageHolder;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREFS_KEY = "sharedPrefsKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageHolder=(ImageView)findViewById(R.id.imageHolder);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d("URL", "onCreate: "+url);

        Picasso.with(getApplicationContext()).load(url).into(imageHolder);
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
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
