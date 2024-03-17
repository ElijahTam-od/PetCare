package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fa3_1_ex5.model.Dog;
import com.example.fa3_1_ex5.retrofit.DogAPI;
import com.example.fa3_1_ex5.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogAddActivity extends AppCompatActivity {
    RetrofitService retrofitService=new RetrofitService();
    DogAPI DogApi = retrofitService.getRetrofit().create(DogAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_add);

        initializeComponents();
    }

    private void initializeComponents(){
        EditText input_dog_name = findViewById(R.id.newDogName);
        EditText input_dog_picture = findViewById(R.id.newDogPicture);
        EditText input_dog_description = findViewById(R.id.newDgoDescription);
        ImageView submitBtn = findViewById(R.id.imageBtnRegisterSubmit);
        ImageView backBtn = findViewById(R.id.imageBackBtn);

        //Submit Dog
        submitBtn.setOnClickListener(View ->{
            String name = input_dog_name.getText().toString();
            String picture = input_dog_picture.getText().toString();
            String description = input_dog_description.getText().toString();


            Dog newDog = new Dog();
            newDog.setName(name);
            newDog.setPicture(picture);
            newDog.setDescription(description);
            newDog.setAdoption_status("No");

            DogApi.save(newDog).enqueue(new Callback<Dog>() {
                @Override
                public void onResponse(Call<Dog> call, Response<Dog> response) {
                    Toast.makeText(DogAddActivity.this, "Save Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Dog> call, Throwable t) {
                    Toast.makeText(DogAddActivity.this, "Save Failed!", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(DogAddActivity.class.getName()).log(Level.SEVERE, "Error Occured", t);
                }
            });
        });

        //Go back
        backBtn.setOnClickListener(View ->{
            finish();
        });

    }

}