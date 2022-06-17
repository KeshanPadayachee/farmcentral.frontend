package com.keshanpadayachee.farmcentral.Farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keshanpadayachee.farmcentral.DBUtils.FarmCentralAPI;
import com.keshanpadayachee.farmcentral.DBUtils.RetrofitClient;
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.Models.mProduct;
import com.keshanpadayachee.farmcentral.MyAdapter;
import com.keshanpadayachee.farmcentral.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FViewProducts extends AppCompatActivity {

    // Farmer object
    private mFarmer farmerObj;

    // Declaring values for the GUI components
    private RecyclerView recycler;
    private ImageView imgBack;

    // Declaring a list to hold all the farmers products
    List<mProduct> lstFarmersProducts = new ArrayList<>();

    // Variables for the API
    CompositeDisposable compositeDisposable;
    FarmCentralAPI APIService;
    ArrayList<mProduct> lstAllProducts = new ArrayList<>();

    // Constructor
    public FViewProducts() {
        compositeDisposable = new CompositeDisposable();

        // Creating an instance of the RetrofitClient class
        Retrofit retrofit = RetrofitClient.getRetrofit();
        APIService = retrofit.create(FarmCentralAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fview_products);

        farmerObj = (mFarmer) getIntent().getSerializableExtra("FARMER");

        // Assigning GUI components to variables
        recycler = findViewById(R.id.rvVPFarmers_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(FViewProducts.this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        imgBack = findViewById(R.id.imgFViewProducts_Back);

        // Calling method to get all the products
        getProductsFromAPI();
    }

    // Method to get all the Products from the Database via the API
    public void getProductsFromAPI() {
        // Creating a composite disposable object
        compositeDisposable = new CompositeDisposable();

        // Building the CompositeDisposable
        compositeDisposable.add(APIService.GetProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<mProduct>>() {
                    @Override
                    public void accept(List<mProduct> mProducts) throws Exception {
                        lstAllProducts.clear();
                        for (mProduct reader : mProducts) {
                            int productID = reader.getProductID();
                            String farmerID = reader.getFarmerID();
                            String name = reader.getProductName();
                            String desc = reader.getProductDescription();
                            double price = reader.getProductPrice();
                            int qty = reader.getProductQuantity();
                            String type = reader.getProductType();
                            mProduct product = new mProduct(productID, farmerID, name, desc, qty, price, type);
                            lstAllProducts.add(product);
                        }

                        // Calling the method to get the farmers products
                        getFarmersProducts();

                        // Initialising the MyAdapter class
                        MyAdapter myAdapter = new MyAdapter(FViewProducts.this, lstFarmersProducts);
                        recycler.setAdapter(myAdapter);


                        // END OF FOR LOOP
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // logging the exceptions
                        Log.d("Composite Disposable", throwable.getMessage());
                    }
                }));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to farmer hub
                Intent toFarmerHub = new Intent(FViewProducts.this, FarmerHub.class);
                toFarmerHub.putExtra("FARMER",farmerObj);
                startActivity(toFarmerHub);
                finish();
            }
        });
    }


    // Method to get all the farmers products
    public void getFarmersProducts() {
        lstFarmersProducts.clear();
        for (mProduct sorter : lstAllProducts) {
            String fID = farmerObj.getFarmerID();
            if (sorter.getFarmerID().equals(farmerObj.getFarmerID())) {
                // Gettingt the product information
                int pid = sorter.getProductID();
                String fid = sorter.getFarmerID();
                String name = sorter.getProductName();
                String desc = sorter.getProductDescription();
                String type = sorter.getProductType();
                double price = sorter.getProductPrice();
                int qty = sorter.getProductQuantity();
                // Creating a product object
                mProduct prod = new mProduct(pid, fID, name, desc, qty, price, type);
                // adding the object to the list
                lstFarmersProducts.add(prod);
            }
        }
    }

    // Method to clear the CompositeDisposable
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}


