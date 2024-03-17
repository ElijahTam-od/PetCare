package com.example.fa3_1_ex5.DogListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fa3_1_ex5.DogPostActivity;
import com.example.fa3_1_ex5.EditDogActivity;
import com.example.fa3_1_ex5.retrofit.DogAPI;
import com.example.fa3_1_ex5.retrofit.RetrofitService;
import com.example.fa3_1_ex5.session.SessionManager;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Response;

import com.example.fa3_1_ex5.R;
import com.example.fa3_1_ex5.model.Dog;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private static final int REQUEST_EDIT_DOG = 1;
    private SessionManager session;
    RetrofitService retrofitService=new RetrofitService();
    DogAPI DogApi = retrofitService.getRetrofit().create(DogAPI.class);
    Context context;
    List<Dog> dogs;

    public MyAdapter(Context context, List<Dog> dogs) {
        this.context = context;
        this.dogs = dogs;
        this.session = new SessionManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dog_item_view, parent, false));
    }

    //Binds all data into every item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dog_name.setText(dogs.get(position).getName());
        Picasso.get().load(dogs.get(position).getPicture()).into(holder.dog_picture);

        //Toast.makeText(context, "Dog at position " + position + " clicked", Toast.LENGTH_SHORT).show();

        //Change bg color of the adoption status
        String adoption_status = dogs.get(position).getAdoption_status();
        String adoption_description = "None";
        Drawable color = ContextCompat.getDrawable(context, R.drawable.rounded_bg_grey);


        if(adoption_status == null || adoption_status.equals("No") || adoption_status.equals("")){
            adoption_description ="Not Adopted";

        }else if(adoption_status.equals("Yes")){
            adoption_description ="Adopted";
            color = ContextCompat.getDrawable(context, R.drawable.rounded_bg_green);
            //holder.dog_adoption_status.setVisibility(View.GONE);
        }else if (adoption_status.equals("Pending")) {
            color = ContextCompat.getDrawable(context, R.drawable.rounded_bg_orange);
            adoption_description ="Pending";
        }

        //Get current user if its admin
        String isAdmin = "No";
        if(session !=null && session.isLoggedIn()){
            isAdmin = session.isAdmin();
        }

        if (isAdmin == null || isAdmin.equals("No")) {
            // If the user is not an admin, hide and disable the "Edit Dog" and "Remove Dog" button
            holder.dog_edit_btn.setVisibility(View.GONE);
            holder.dog_edit_btn.setEnabled(false);

            holder.dog_delete_btn.setVisibility(View.GONE);
            holder.dog_delete_btn.setEnabled(false);
        }

        holder.dog_adoption_status.setText(adoption_description);
        holder.dog_adoption_status.setBackground(color);

        //click to the post
        holder.itemView.setOnClickListener(View ->{
            // Put the Dog object as an extra with a unique key
            Intent intent = new Intent(context, DogPostActivity.class);
            intent.putExtra("DOG_OBJECT", dogs.get(position));
            context.startActivity(intent);
        });

        //Edit
        holder.dog_edit_btn.setOnClickListener(v -> {
            Toast.makeText(context, "Edit dog at position " + position + " clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, EditDogActivity.class);

            // Put the Dog object as an extra with a unique key
            intent.putExtra("DOG_OBJECT", dogs.get(position));

            // Check if the context is an instance of Activity before starting the activity
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, REQUEST_EDIT_DOG);
            } else {
                // If the context is not an Activity, you might need to handle this case appropriately
                // For example, you can create a new activity or log an error.
                Log.e("MyAdapter", "Context is not an instance of Activity");
            }
        });

        //Delete
        holder.dog_delete_btn.setOnClickListener(View ->{
            // Display a confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to delete this dog?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User clicked Yes, delete the dog
                        Toast.makeText(context, "Dog id: " + dogs.get(position).getId() + " clicked", Toast.LENGTH_SHORT).show();
                        deleteDog(dogs.get(position).getId());
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User clicked No, do nothing
                        dialog.dismiss();
                    })
                    .show();

        });


    }

    public void updateDog(Dog updatedDog) {
        for (int i = 0; i < dogs.size(); i++) {
            if (dogs.get(i).getId() == updatedDog.getId()) {
                dogs.set(i, updatedDog);
                notifyItemChanged(i);
                break;
            }
        }
    }

    private void deleteDog(int dogId) {
        DogApi.delete(dogId).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remove the deleted dog from the list
                    for (int i = 0; i < dogs.size(); i++) {
                        if (dogs.get(i).getId() == dogId) {
                            dogs.remove(i);
                            notifyItemRemoved(i);
                            break;
                        }
                    }


                    Toast.makeText(context, "Dog deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error
                    Toast.makeText(context, "Failed to delete dog", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }
}
