package com.example.mireclifour.utils;

import com.example.mireclifour.model.Product;
import com.example.mireclifour.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @POST("/users")
    Call<Void> postUser(@Body User user);

    @PUT("/users")
    Call<Void> putUser(@Body User user);

    @DELETE("/users/{userId}")
    Call<Void> deleteUser(@Path("userId") String userId);

    @GET("/users/products/{userId}")
    Call<List<Product>> getProducts(@Path("userId") String userId);

    @GET("/users/products/{userId}/{productId}")
    Call<Product> getProduct(@Path("userId") String userId, @Path("productId") String productId);

    @POST("/users/products/{userId}")
    Call<Void> postProduct(@Path("userId") String userId, @Body Product product);

    @DELETE("/users/products/{userId}/{productId}")
    Call<Void> deleteProduct(@Path("userId") String userId, @Path("productId") String productId);

}
