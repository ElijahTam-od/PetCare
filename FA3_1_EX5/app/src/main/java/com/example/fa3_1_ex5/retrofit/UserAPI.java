package com.example.fa3_1_ex5.retrofit;

import com.example.fa3_1_ex5.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/get-user-by-id/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("/get-user-by-username/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @GET("/get-user-by-email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @GET("/get-user-by-email-and-password")
    Call<User> getUserByEmailAndPassword(@Query("email") String email, @Query("password") String password);

    @POST("/add-user")
    Call <User> add(@Body User user);

    @PUT("/update-user")
    Call<User> updateUser(@Body User updatedUser);

    @DELETE("/delete-user")
    Call<Void> delete(@Path("id") int userId);
}
