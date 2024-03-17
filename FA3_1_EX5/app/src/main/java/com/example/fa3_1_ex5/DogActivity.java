package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fa3_1_ex5.DogListView.MyAdapter;
import com.example.fa3_1_ex5.model.Dog;
import com.example.fa3_1_ex5.retrofit.DogAPI;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogActivity extends AppCompatActivity {
    private static final List<DogActivity> activeInstances = new ArrayList<>();
    private SessionManager session;
    private DialogBoxTools dialogBox;

    private static final int REQUEST_EDIT_DOG = 1;

    RetrofitService retrofitService=new RetrofitService();
    DogAPI DogApi = retrofitService.getRetrofit().create(DogAPI.class);

    List<Dog> dogs;

    RecyclerView recyclerView;

    private boolean isFirstResume = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);

        initializeComponents();
    }

    private void initializeComponents(){
        session = new SessionManager(this);
        dialogBox = new DialogBoxTools(this);

        recyclerView = findViewById(R.id.dogRecyclerView);
        ImageView new_dog_btn = findViewById(R.id.imageAddNewDogBtn);

        dogs = new ArrayList<Dog>();
        DogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if (response.isSuccessful()) {
                    dogs.addAll(response.body());

                    // Notify the adapter that the data has changed
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(DogActivity.this, "Failed to fetch dog data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                Toast.makeText(DogActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this, dogs));

        //Get current user
        String isAdmin = "No";
        if(session !=null && session.isLoggedIn()){
            isAdmin = session.isAdmin();
        }

        if (isAdmin == null|| isAdmin.isEmpty()) {
            // If the user is not an admin, hide and disable the "New Dog" button
            new_dog_btn.setVisibility(View.GONE);
            new_dog_btn.setEnabled(false);
        } else if (isAdmin.equals("No")) {
            new_dog_btn.setVisibility(View.GONE);
            new_dog_btn.setEnabled(false);
        }

        new_dog_btn.setOnClickListener(View ->{
            Intent intent = new Intent(DogActivity.this, DogAddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_DOG && resultCode == RESULT_OK && data != null) {
            // Check if data contains the updated dog
            if (data.hasExtra("UPDATED_DOG")) {
                Dog updatedDog = (Dog) data.getSerializableExtra("UPDATED_DOG");

                // Update the dog in the adapter
                MyAdapter adapter = (MyAdapter) recyclerView.getAdapter();
                adapter.updateDog(updatedDog);

                //recreate();
                showResultDialog(updatedDog.getName(), updatedDog.getAdoption_status());
                if (getIntent().getBooleanExtra("DATA_UPDATED", false)) {
                    reloadDogData();
                }
            }
        }
    }

    private void reloadDogData() {
        dogs.clear();
        DogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if (response.isSuccessful()) {
                    dogs.addAll(response.body());

                    // Notify the adapter that the data has changed
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(DogActivity.this, "Failed to fetch dog data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                Toast.makeText(DogActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to show the result dialog
    private void showResultDialog(String dogName, String adoptionStatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result")
                .setMessage("Dog: " + dogName + "\nAdoption Status: " + adoptionStatus)
                .setPositiveButton("OK", (dialog, which) -> {
                    // You can add further actions if needed
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstResume) {
            // Only reload data if it's not the first time onResume() is called
            reloadDogData();
        }
        isFirstResume = false;

    }

}