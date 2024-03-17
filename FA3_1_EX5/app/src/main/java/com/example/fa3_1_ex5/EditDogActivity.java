package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fa3_1_ex5.model.Dog;
import com.example.fa3_1_ex5.model.User;
import com.example.fa3_1_ex5.retrofit.DogAPI;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.retrofit.UserAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDogActivity extends AppCompatActivity {
    // Define a request code
    private static final int REQUEST_SELECT_USER = 1;

    String[] adoptionStatusArray ={"Pending", "Yes", "No"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    TextView input_dog_owner;

    RetrofitService retrofitService=new RetrofitService();
    DogAPI DogApi = retrofitService.getRetrofit().create(DogAPI.class);

    UserAPI UserApi = retrofitService.getRetrofit().create(UserAPI.class);

    User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dog);

        initializeComponents();
    }

    private void initializeComponents(){

        autoCompleteTextView = findViewById(R.id.autocompleteTxtView);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_dropdown, adoptionStatusArray);
        final String[] selected_item = new String[1];



        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selected_item[0] = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(EditDogActivity.this, "Selected: "+ selected_item[0], Toast.LENGTH_SHORT).show();
            }
        });

        //Initialize Components
        EditText input_dog_id = findViewById(R.id.editDogId);
        EditText input_dog_name = findViewById(R.id.editDogName);
        EditText input_dog_picture = findViewById(R.id.editDogPicture);
        EditText input_dog_description = findViewById(R.id.editDogDescription);
        input_dog_owner = findViewById(R.id.editDogOwner);
        ImageView submitBtn = findViewById(R.id.imageBtnEditDogSubmit);
        ImageView back_btn = findViewById(R.id.editDogBackBtn);

        RetrofitService retrofitService=new RetrofitService();
        DogAPI DogAPI = retrofitService.getRetrofit().create(DogAPI.class);

        //LOAD DOG DATA
        // Retrieve the Dog object from the Intent
        Dog dog = (Dog) getIntent().getSerializableExtra("DOG_OBJECT");
        if (dog != null) {
            input_dog_id.setText(String.valueOf(dog.getId()));
            input_dog_name.setText(dog.getName());
            input_dog_description.setText(dog.getDescription());
            input_dog_picture.setText(dog.getPicture());

            // Set the text without triggering filtering
            autoCompleteTextView.setAdapter(null);

            // Set the selected_item to be the current dog's adoption_status
            selected_item[0] = dog.getAdoption_status();
            autoCompleteTextView.setText(selected_item[0]);

            // Re-enable filtering with the original adapter
            autoCompleteTextView.setAdapter(adapterItems);

            UserApi.getUserById(dog.getOwner()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User owner = response.body();
                    if (owner != null) {
                        input_dog_owner.setText(owner.getUsername());
                        selectedUser = owner;
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle failure if needed
                }
            });



            //Weird ass autocomplete
            // Disable filtering temporarily
            autoCompleteTextView.setAdapter(null);

            // Set the text without triggering filtering
            autoCompleteTextView.setText(dog.getAdoption_status());

            // Re-enable filtering with the original adapter
            autoCompleteTextView.setAdapter(adapterItems);
        }

        //Submit Button
        submitBtn.setOnClickListener(View ->{
            int id = Integer.parseInt(input_dog_id.getText().toString());
            String name = input_dog_name.getText().toString();
            String picture = input_dog_picture.getText().toString();
            String description = input_dog_description.getText().toString();
            String adoption_status = selected_item[0];

            Dog updatedDog = new Dog();
            updatedDog.setId(id);
            updatedDog.setName(name);
            updatedDog.setPicture(picture);
            updatedDog.setDescription(description);
            updatedDog.setAdoption_status(adoption_status);

            if(selectedUser==null){
            }else{
                updatedDog.setOwner(selectedUser.getId());
            }


            DogAPI.edit(updatedDog).enqueue(new Callback<Dog>() {
                @Override
                public void onResponse(Call<Dog> call, Response<Dog> response) {

                    UserApi.getUserById(updatedDog.getOwner()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User owner = response.body();
                            if (owner != null) {
                                input_dog_owner.setText(owner.getUsername());

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("UPDATED_DOG", updatedDog);
                                setResult(RESULT_OK, resultIntent);

                                // Start DogActivity with a new Intent
                                Intent dogActivityIntent = new Intent(EditDogActivity.this, DogActivity.class);
                                dogActivityIntent.putExtra("DATA_UPDATED", true);
                                Toast.makeText(EditDogActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();

                                finish();


                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(EditDogActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(DogAddActivity.class.getName()).log(Level.SEVERE, "Error Occurred", t);
                        }
                    });


                }

                @Override
                public void onFailure(Call<Dog> call, Throwable t) {
                    Toast.makeText(EditDogActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(DogAddActivity.class.getName()).log(Level.SEVERE, "Error Occured", t);
                }
            });
            finish();
        });


        //Back
        back_btn.setOnClickListener(View ->{
            // Simply finish the current activity to navigate back to the previous activity on the stack
            finish();
        });

        //Owner Selection
        input_dog_owner.setOnClickListener(View ->{
            Toast.makeText(EditDogActivity.this, "Owner Selection clicked", Toast.LENGTH_SHORT).show();

            // Start UsersActivity with startActivityForResult
            Intent intent = new Intent(EditDogActivity.this, UsersActivity.class);
            startActivityForResult(intent, REQUEST_SELECT_USER);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_USER && resultCode == RESULT_OK && data != null) {
            // Retrieve the selected user data from the Intent
            selectedUser = (User) data.getSerializableExtra("SELECTED_USER");

            // Update the owner field or perform any other action with the selected user data
            if (selectedUser != null) {
                input_dog_owner.setText(selectedUser.getUsername());
            }
        }
    }


}