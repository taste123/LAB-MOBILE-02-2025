package com.example.tp6;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("character")
    Call<UserResponse> getUsers(@Query("page") int page);

    @GET("character/{id}")
    Call<User> getCharacter(@Path("id") int id);
}
