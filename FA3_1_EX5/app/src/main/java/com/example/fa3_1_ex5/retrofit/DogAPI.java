package com.example.fa3_1_ex5.retrofit;

import com.example.fa3_1_ex5.model.Dog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DogAPI {
    @GET("/dogs")
    Call<List<Dog>> getAllDogs();

    @GET("/get-dog/{id}")
    Call<Dog> getDogById(@Path("id") int dogId);

    @POST("/add-dog")
    Call<Dog> save(@Body Dog dog);

    @PUT("/update-dog")
    Call<Dog> edit(@Body Dog updatedDog);

    @DELETE("/delete-dog/{id}")
    Call<Void> delete(@Path("id") int dogId);
}
