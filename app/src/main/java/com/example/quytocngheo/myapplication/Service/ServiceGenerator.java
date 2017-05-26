package com.example.quytocngheo.myapplication.Service;

import okhttp3.OkHttpClient;
//import retrofit2.
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by quytocngheo on 5/21/2017.
 */

public class ServiceGenerator {
    public ServiceGenerator() {
    }

    public static final String API_BASE_URL = "http://foodvat.herokuapp.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
    // basic
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
