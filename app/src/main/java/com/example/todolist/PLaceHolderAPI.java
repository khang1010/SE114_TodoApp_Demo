package com.example.todolist;

import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PLaceHolderAPI {

    Moshi moshi = new Moshi.Builder().build();

    PLaceHolderAPI placeHolderApi = new Retrofit.Builder()
            .baseUrl("https://3bro.hoanghy.tech/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PLaceHolderAPI.class);
    @GET("todos")
    Call<List<ToDoItem>> getAllTodo();

    @POST("todos")
    Call<ToDoItem> addTodo(@Body ToDoItem todoItem);

    @PUT("todos/{id}")
    Call<ToDoItem> updateTodo(@Path("id") int id, @Body ToDoItem todoItem);

    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") int id);
}
