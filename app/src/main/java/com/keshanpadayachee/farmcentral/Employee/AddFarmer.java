package com.keshanpadayachee.farmcentral.Employee;

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
import com.keshanpadayachee.farmcentral.Models.mEmployee;
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.R;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFarmer extends AppCompatActivity {

    // Employee object
    private mEmployee employee;

    // Declaring variables for the GUI components
    private EditText edtIDNumber, edtFirstName, edtLastName, edtPassword;
    private Button btnCreateFarmer;
    private ImageView imgBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer);

        // Getting Employee object from the intent
        employee = (mEmployee) getIntent().getSerializableExtra("EMPLOYEE");

        // Assigning GUI components to the variables
        edtIDNumber = findViewById(R.id.edtAddFarmer_IDNumber);
        edtFirstName = findViewById(R.id.edtAddFarmer_FirstName);
        edtLastName = findViewById(R.id.edtAddFarmer_LastName);
        edtPassword = findViewById(R.id.edtAddFarmer_Password);
        btnCreateFarmer = findViewById(R.id.btnAddFarmer_AddFarmer);
        imgBack = findViewById(R.id.imgAddFarmer_Back);

        // Handling the ADD FARMER BUTTON CLICK
        btnCreateFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting values from input fields
                String IDNumber = edtIDNumber.getText().toString();
                String firstName = edtFirstName.getText().toString();
                String lastName = edtLastName.getText().toString();
                String password = edtPassword.getText().toString();

                // Creating a Farmer object
                mFarmer newFarmer = new mFarmer(IDNumber, firstName, lastName, password, password);

                // Calling the method to add farmer to db
                postData(newFarmer);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to Employee hub
                Intent toEmployeeHub = new Intent(AddFarmer.this, EmployeeHub.class);
                toEmployeeHub.putExtra("EMPLOYEE",employee);
                startActivity(toEmployeeHub);
                finish();
            }
        });
    }

    // Method to add a new Farmer
    private void postData(mFarmer addFarmer) {

        // Creating instances for API connection
        Retrofit retrofit = RetrofitClient.getRetrofit();
        FarmCentralAPI retrofitAPI = retrofit.create(FarmCentralAPI.class);

        // calling a method to create a post and passing our modal class.
        Call<mFarmer> call = retrofitAPI.PostFarmer(addFarmer);

        // on below line we are executing our method.
        call.enqueue(new Callback<mFarmer>() {
            @Override
            public void onResponse(Call<mFarmer> call, Response<mFarmer> response) {
                // this method is called when we get response from our api.
                Toast.makeText(AddFarmer.this, "Farmer Added Successfully", Toast.LENGTH_SHORT).show();

                // Clearing the input fields
                edtIDNumber.setText("");
                edtFirstName.setText("");
                edtLastName.setText("");
                edtPassword.setText("");
            }

            @Override
            public void onFailure(Call<mFarmer> call, Throwable t) {
                // Displaying the error
                Toast.makeText(AddFarmer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}