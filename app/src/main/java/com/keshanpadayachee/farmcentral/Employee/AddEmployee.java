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
import com.keshanpadayachee.farmcentral.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddEmployee extends AppCompatActivity {

    // Employee object
    private mEmployee employee;

    // Declaring variables for the GUI components
    private EditText edtIDNumber, edtFirstName, edtLastName, edtPassword;
    private Button btnCreateFarmer;
    private ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Getting Employee object from the intent
        employee = (mEmployee) getIntent().getSerializableExtra("EMPLOYEE");

        // Assigning GUI components to the variables
        edtIDNumber = findViewById(R.id.edtAddEmployee_IDNumber);
        edtFirstName = findViewById(R.id.edtAddEmployee_FirstName);
        edtLastName = findViewById(R.id.edtAddEmployee_LastName);
        edtPassword = findViewById(R.id.edtAddEmployee_Password);
        btnCreateFarmer = findViewById(R.id.btnAddEmployee_AddFarmer);
        imgBack = findViewById(R.id.imgAddEmployee_Back);


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
                mEmployee newEmployee = new mEmployee(IDNumber, firstName, lastName, password, password);

                // Calling the method to add a new Employee
                postData(newEmployee);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to employee hub
                Intent toEmployeeHub = new Intent(AddEmployee.this, EmployeeHub.class);
                toEmployeeHub.putExtra("EMPLOYEE",employee);
                startActivity(toEmployeeHub);
                finish();
            }
        });
    }

    // Method to add a new Employee
    private void postData(mEmployee addEmployee) {

        // Creating instances for API connection
        Retrofit retrofit = RetrofitClient.getRetrofit();
        FarmCentralAPI retrofitAPI = retrofit.create(FarmCentralAPI.class);


        // calling a method to create a post and passing our modal class.
        Call<mEmployee> call = retrofitAPI.PostEmployee(addEmployee);

        // on below line we are executing our method.
        call.enqueue(new Callback<mEmployee>() {
            @Override
            public void onResponse(Call<mEmployee> call, Response<mEmployee> response) {
                // this method is called when we get response from our api.
                Toast.makeText(AddEmployee.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();

                // Clearing text fields
                edtIDNumber.setText("");
                edtFirstName.setText("");
                edtLastName.setText("");
                edtPassword.setText("");
            }

            @Override
            public void onFailure(Call<mEmployee> call, Throwable t) {
                // Displaying the errors
                Toast.makeText(AddEmployee.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}