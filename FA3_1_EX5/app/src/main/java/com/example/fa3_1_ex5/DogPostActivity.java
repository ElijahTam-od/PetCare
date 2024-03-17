package com.example.fa3_1_ex5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fa3_1_ex5.model.Dog;
import com.squareup.picasso.Picasso;

public class DogPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_post);

        initializeComponents();
    }

    private void initializeComponents(){
        TextView name = findViewById(R.id.dogPostName);
        TextView adoption_status = findViewById(R.id.dogPostAdoptionStatus);
        ImageView image = findViewById(R.id.dogPostImage);
        TextView description = findViewById(R.id.dogPostDescription);

        Dog dog = (Dog) getIntent().getSerializableExtra("DOG_OBJECT");

        name.setText(dog.getName());
        adoption_status.setText((dog.getAdoption_status()));
        description.setText(dog.getDescription());

        // Load image using Picasso
        if (dog.getPicture() != null && !dog.getPicture().isEmpty()) {
            Picasso.get().load(dog.getPicture()).into(image);
        }

        //Set color of the adoption status tag
        String adoption_status_string = dog.getAdoption_status();
        String adoption_description = "None";
        Drawable color = ContextCompat.getDrawable(this, R.drawable.rounded_bg_grey);


        if(adoption_status_string == null || adoption_status_string.equals("No") || adoption_status_string.equals("")){
            adoption_description ="Not Adopted";

        }else if(adoption_status_string.equals("Yes")){
            adoption_description ="Adopted";
            color = ContextCompat.getDrawable(this, R.drawable.rounded_bg_green);
            //holder.dog_adoption_status.setVisibility(View.GONE);
        }else if (adoption_status_string.equals("Pending")) {
            color = ContextCompat.getDrawable(this, R.drawable.rounded_bg_orange);
            adoption_description ="Pending";
        }

        adoption_status.setText(adoption_description);
        adoption_status.setBackground(color);
    }
}