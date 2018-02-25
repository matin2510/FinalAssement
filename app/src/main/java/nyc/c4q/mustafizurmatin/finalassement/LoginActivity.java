package nyc.c4q.mustafizurmatin.finalassement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameET, passwordET;
    private Button loginBtn;
    private static final String SHARED_PREFS_KEY = "sharedPrefsKey";
    private SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameET=(EditText) findViewById(R.id.usernameET);
        passwordET=(EditText) findViewById(R.id.passwordET);
        loginBtn=(Button) findViewById(R.id.loginBtn);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String imgSett = prefs.getString("username", "");

        Log.d("SHARED", "onCreate: "+ imgSett);

        SharedPreferences shared = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String channel = (shared.getString("username", ""));
        Log.d("SHARED", "onCreate: "+ channel);
        if (!channel.equals("")){
            goToBreedsActivity();
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= usernameET.getText().toString();
                String password = passwordET.getText().toString();
                Log.d("Clicked", "onClick: button clicked");
                checkEmptyEntries(username, password);
            }
        });

    }

    private void checkEmptyEntries(String username, String password) {
        if (!username.equals("") && !password.equals("")){
            if (password.contains(username)){
                passwordET.setError("Password cannot contain username");
                passwordET.setText("");
            }
            else {
                //Save to shared prefs
                sp = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
                editor = sp.edit();
                editor.putString("username", username);
                editor.apply();
                editor.commit();
                goToBreedsActivity();
                usernameET.setText("");
                passwordET.setText("");
            }

        }

        if (username.equals("")){
            usernameET.setError("Please enter a Username");
        }
        if(password.equals("")){
            passwordET.setError("Please enter a Password");
        }


        Log.d("END", "checkEmptyEntries: ");
    }

    private void goToBreedsActivity() {
       Intent intent = new Intent(this, BreedsActivity.class);
        //intent.putExtra("username", username);
        startActivity(intent);

    }


}

