package com.keshanpadayachee.farmcentral.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.keshanpadayachee.farmcentral.DBUtils.FarmCentralAPI;
import com.keshanpadayachee.farmcentral.DBUtils.RetrofitClient;
import com.keshanpadayachee.farmcentral.Models.mEmployee;
import com.keshanpadayachee.farmcentral.R;
import com.keshanpadayachee.farmcentral.StartScreen;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class EmployeeLogIn extends AppCompatActivity {

    // Declaring variables for the GUI components
    private EditText edtIDNumber, edtPassword;
    private Button btnLogIn;
    private ImageView imgBack;

    // Variables for API
    CompositeDisposable compositeDisposable;
    FarmCentralAPI APIService;
    List<mEmployee> lstAllEmployees = new ArrayList<>();


    // CONSTRUCTOR
    public EmployeeLogIn() {
        compositeDisposable = new CompositeDisposable();
        // Creating an instance of the RetrofitClient class
        Retrofit retrofit = RetrofitClient.getRetrofit();
        APIService = retrofit.create(FarmCentralAPI.class);
        // Calling method to get all Employees
        getEmployeesFromAPI();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_log_in);

        // Assigning the GUI components to the variables
        edtIDNumber = findViewById(R.id.edtELogIn_IDNumber);
        edtPassword = findViewById(R.id.edtELogIn_Password);
        btnLogIn = findViewById(R.id.btnELogIn_LogIn);
        imgBack = findViewById(R.id.imgEmployeeLogIn_Back);

        // Handling the LOGIN BUTTON ONCLICK
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting the Employees ID Number and Password
                String enteredIDNumber = edtIDNumber.getText().toString();
                String enteredPassword = edtPassword.getText().toString();

                // Checking if the fields are empty
                if (enteredPassword.isEmpty() || enteredIDNumber.isEmpty()){
                    // Notifying user
                    Toast.makeText(EmployeeLogIn.this, "Please enter your login details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    // Searching for the Employees details
                    for (mEmployee checker : lstAllEmployees) {
                        if (checker.getEmployeeID().equals(enteredIDNumber) && checker.getPasswordHash().equals(enteredPassword)) {
                            // Employee is found, going to hub
                            Intent toEmployeeHub = new Intent(EmployeeLogIn.this, EmployeeHub.class);
                            toEmployeeHub.putExtra("EMPLOYEE", checker);
                            startActivity(toEmployeeHub);
                            finish();
                            return;
                        }
                        // END OF IF
                    }
                    // END OF FOR LOOP
                }
                // No user found
                Toast.makeText(EmployeeLogIn.this, "Incorrect login details", Toast.LENGTH_SHORT).show();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to the Start Screen
                Intent toStartScreen = new Intent(EmployeeLogIn.this, StartScreen.class);
                startActivity(toStartScreen);
                finish();
            }
        });
    }


    // Method to get all the Employee records from database via API
    public void getEmployeesFromAPI() {
        // Creating a new CompositeDisposable object
        compositeDisposable = new CompositeDisposable();

        // Building the CompositeDisposable
        compositeDisposable.add(APIService.GetEmployee()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<mEmployee>>() {
                    @Override
                    public void accept(List<mEmployee> mEmployeesList) throws Exception {
                        lstAllEmployees.clear();
                        // Looping through database records and creating a local version
                        for (mEmployee reader : mEmployeesList) {
                            String ID = reader.getEmployeeID();
                            String name = reader.getFirstName();
                            String last = reader.getLastName();
                            String salt = reader.getPasswordSalt();
                            String hash = reader.getPasswordHash();
                            // Creating an Employee object
                            mEmployee emp = new mEmployee(ID, name, last, salt, hash);
                            // Adding Employee object to the list
                            lstAllEmployees.add(emp);
                        }
                        // END OF FOR LOOP
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // Logging any exceptions
                        Log.d("Disposable", throwable.getMessage());
                    }
                }));
    }
}