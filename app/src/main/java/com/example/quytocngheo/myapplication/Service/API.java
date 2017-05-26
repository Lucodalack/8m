package com.example.quytocngheo.myapplication.Service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.example.quytocngheo.myapplication.Model.ListQuestion;
import com.example.quytocngheo.myapplication.Model.ResponseLogin;
import com.example.quytocngheo.myapplication.Model.Store;
import com.example.quytocngheo.myapplication.Model.User;

import java.util.List;

/**
 * Created by quytocngheo on 5/21/2017.
 */

public interface API {
    @GET("2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<ListQuestion> loadQuestions(@Query("tagged") String tags);

    @GET("places/get_list")
    Call<List<Store>> loadStore();

    @POST("/login")
    Call<ResponseLogin> pushUser(@Body User user);
}
