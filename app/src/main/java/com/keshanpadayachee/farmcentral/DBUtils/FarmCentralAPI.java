package com.keshanpadayachee.farmcentral.DBUtils;


import com.keshanpadayachee.farmcentral.Models.mEmployee;
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.Models.mProduct;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FarmCentralAPI {


    // FARMERS
    @GET("Farmers")
    Observable<List<mFarmer>> GetFarmer();

    @POST("Farmers")
    Call<mFarmer> PostFarmer(@Body mFarmer addFarmer);


    // EMPLOYEES
    @GET("Employees")
    Observable<List<mEmployee>>GetEmployee();

    @POST("Employees")
    Call<mEmployee> PostEmployee(@Body mEmployee employee);


    // PRODUCTS
    @GET("Products")
    Observable<List<mProduct>>GetProduct();

    @POST("Products")
    Call<mProduct> PostProduct(@Body mProduct product);

}