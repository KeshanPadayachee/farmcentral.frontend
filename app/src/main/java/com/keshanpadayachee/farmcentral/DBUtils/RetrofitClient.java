package com.keshanpadayachee.farmcentral.DBUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    // Creating the Connection to the API
    public static Retrofit getRetrofit() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://apifarmcentral.azurewebsites.net/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        return retrofit;
    }

}
