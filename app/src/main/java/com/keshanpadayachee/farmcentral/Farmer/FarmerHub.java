package com.keshanpadayachee.farmcentral.Farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keshanpadayachee.farmcentral.DBUtils.FarmCentralAPI;
import com.keshanpadayachee.farmcentral.DBUtils.RetrofitClient;
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.Models.mProduct;
import com.keshanpadayachee.farmcentral.R;
import com.keshanpadayachee.farmcentral.StartScreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FarmerHub extends AppCompatActivity implements View.OnClickListener {

    // Declaring variables for the GUI components
    private TextView txtFarmer;
    private Button btnAddProduct, btnViewProducts, btnSignOut;

    // Farmer object
    private mFarmer farmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_hub);

        // Getting the Farmer object from the Intent
        farmer = (mFarmer) getIntent().getSerializableExtra("FARMER");

        // Assigning the GUI components to the variables
        txtFarmer = findViewById(R.id.txtFarmerHub_Name);
        btnAddProduct = findViewById(R.id.btnFarmerHub_AddProduct);
        btnViewProducts = findViewById(R.id.btnFarmerHub_MyProducts);
        btnSignOut = findViewById(R.id.btnFarmerHub_SignOut);

        // Setting the Hub header
        txtFarmer.setAllCaps(true);
        txtFarmer.setText(farmer.getFirstName());

        // Handling the On Clicks
        btnAddProduct.setOnClickListener(this);
        btnViewProducts.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }

    // Implementing the OnClick Listener Interface
    @Override
    public void onClick(View v) {
        // Handling clicks
        switch(v.getId()){
            // ADD PRODUCT
            case R.id.btnFarmerHub_AddProduct:
                Intent toAddProduct = new Intent(FarmerHub.this, AddProduct.class);
                toAddProduct.putExtra("FARMER",farmer);
                startActivity(toAddProduct);
                finish();
                break;

            // VIEW PRODUCTS
            case R.id.btnFarmerHub_MyProducts:
                Intent toViewProducts = new Intent(FarmerHub.this, FViewProducts.class);
                toViewProducts.putExtra("FARMER",farmer);
                startActivity(toViewProducts);
                finish();
                break;

            // SIGN OUT
            case R.id.btnFarmerHub_SignOut:
                Intent toStartScreen = new Intent(FarmerHub.this, StartScreen.class);
                startActivity(toStartScreen);
                finish();
                break;
        }
        // END OF SWITCH
    }
}