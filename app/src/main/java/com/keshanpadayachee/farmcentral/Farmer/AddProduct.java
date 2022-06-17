package com.keshanpadayachee.farmcentral.Farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.keshanpadayachee.farmcentral.DBUtils.FarmCentralAPI;
import com.keshanpadayachee.farmcentral.DBUtils.RetrofitClient;
import com.keshanpadayachee.farmcentral.Employee.AddEmployee;
import com.keshanpadayachee.farmcentral.Models.mEmployee;
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.Models.mProduct;
import com.keshanpadayachee.farmcentral.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddProduct extends AppCompatActivity {

    // Declaring variables for GUI components
    private EditText edtProductName,edtProductDescription, edtProductPrice, edtProductQuantity, edtProductType;
    private Button btnAddProduct;
    private ImageView imgBack;

    // Farmer object
    mFarmer farmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Getting the Farmer object from Intent
        farmer = (mFarmer) getIntent().getSerializableExtra("FARMER");

        // Assigning GUI components to the variables
        edtProductName = findViewById(R.id.edtAddProduct_ProductName);
        edtProductDescription = findViewById(R.id.edtAddProduct_ProductDescription);
        edtProductPrice = findViewById(R.id.edtAddProduct_ProductPrice);
        edtProductQuantity = findViewById(R.id.edtAddProduct_ProductQuantity);
        edtProductType = findViewById(R.id.edtAddProduct_ProductType);
        btnAddProduct = findViewById(R.id.btnAddProduct_AddProduct);
        imgBack = findViewById(R.id.imgAddProduct_Back);

        // Handling the button click
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting values from input fields
                String farmerID = farmer.getFarmerID();
                String productName = edtProductName.getText().toString();
                String productDescription = edtProductDescription.getText().toString();
                double productPrice = Double.parseDouble(edtProductPrice.getText().toString());
                int productQuantity = Integer.parseInt(edtProductQuantity.getText().toString());
                String productType = edtProductType.getText().toString();

                // Creating a product object
                mProduct newProduct = new mProduct(farmerID,productName,productDescription,productQuantity,productPrice,productType);

                // Calling method to add new Product
                postData(newProduct);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To Farmer Hub
                Intent toFarmerHub = new Intent(AddProduct.this, FarmerHub.class);
                toFarmerHub.putExtra("FARMER",farmer);
                startActivity(toFarmerHub);
                finish();
            }
        });
    }

    // Method to add a new Product
    private void postData(mProduct addProduct) {

        // Creating instances for API connection
        Retrofit retrofit = RetrofitClient.getRetrofit();
        FarmCentralAPI retrofitAPI = retrofit.create(FarmCentralAPI.class);


        // calling a method to create a post and passing our modal class.
        Call<mProduct> call = retrofitAPI.PostProduct(addProduct);

        // on below line we are executing our method.
        call.enqueue(new Callback<mProduct>() {
            @Override
            public void onResponse(Call<mProduct> call, Response<mProduct> response) {
                // this method is called when we get response from our api.
                Toast.makeText(AddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();

                // Clearing text fields
                edtProductName.setText("");
                edtProductDescription.setText("");
                edtProductPrice.setText("");
                edtProductQuantity.setText("");
                edtProductType.setText("");
            }

            @Override
            public void onFailure(Call<mProduct> call, Throwable t) {
                // Displaying the errors
                Toast.makeText(AddProduct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}