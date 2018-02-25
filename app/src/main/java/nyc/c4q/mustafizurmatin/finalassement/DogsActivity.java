package nyc.c4q.mustafizurmatin.finalassement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.mustafizurmatin.finalassement.adapters.DogsAdapter;
import nyc.c4q.mustafizurmatin.finalassement.api.EndPointApi;
import nyc.c4q.mustafizurmatin.finalassement.models.RootObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsActivity extends AppCompatActivity {
    private TextView textBreed;
    private RecyclerView recyclerContainer;
    private DogsAdapter dogsAdapter;
    private Retrofit retrofit;
    private RootObject rootObject;
    List<String> listDogs;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREFS_KEY = "sharedPrefsKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);
        textBreed=(TextView)findViewById(R.id.textBreed);
        recyclerContainer=(RecyclerView)findViewById(R.id.recyclerContainer);

        Intent intent = getIntent();
        String breed = intent.getStringExtra("breed");
        textBreed.setText(breed.toUpperCase());


        dogsAdapter= new DogsAdapter(this);
        recyclerContainer.setAdapter(dogsAdapter);
        recyclerContainer.setHasFixedSize(true);
        int value = this.getResources().getConfiguration().orientation;
        GridLayoutManager gridLayoutManager=null;

        if (value == Configuration.ORIENTATION_PORTRAIT) {

            gridLayoutManager= new GridLayoutManager(this, 2);
        }

        if (value == Configuration.ORIENTATION_LANDSCAPE) {

            gridLayoutManager= new GridLayoutManager(this, 3);
        }
        recyclerContainer.setLayoutManager(gridLayoutManager);


        retrofitDogs();
        listDogs=new ArrayList<>();
        obtenerDatos(breed);
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }*/
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


    private void retrofitDogs() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private void obtenerDatos(final String breed) {

        EndPointApi service = retrofit.create(EndPointApi.class);
        Call<RootObject> response = service.getDogs(breed);
        response.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                if (response.isSuccessful()){
                    Log.d("CALL", "onResponse: "+response);
                    rootObject = null;
                    rootObject = response.body();
                    listDogs.addAll(rootObject.getMessage());
                    dogsAdapter.addImages(listDogs);

                }


            }

            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Log.d("FAILL", "onFailure: ");

            }
        });
        /*response.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                if(response.isSuccessful()){
                    Log.d("CALL", "onResponse: "+response);
                    rootObject = null;
                    rootObject = response.body();
                    listDogs.addAll(rootObject.getMessage());

                    dogsAdapter.addImages(listDogs);
                }
            }

            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {

                Log.d("FAILL", "onFailure: ");
            }
        });*/



    }
    }
