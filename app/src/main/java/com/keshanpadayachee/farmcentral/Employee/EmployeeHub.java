package com.keshanpadayachee.farmcentral.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keshanpadayachee.farmcentral.Models.mEmployee;
import com.keshanpadayachee.farmcentral.R;
import com.keshanpadayachee.farmcentral.StartScreen;

public class EmployeeHub extends AppCompatActivity implements View.OnClickListener {

    // Declaring variables for the GUI components
    private TextView txtEmployeeName;
    private Button btnAddEmployee, btnAddFarmer, btnViewProducts, btnSignOut;

    // Employee object
    mEmployee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_hub);

        // Getting Employee from the Intent
        employee = (mEmployee) getIntent().getSerializableExtra("EMPLOYEE");
        // Displaying the employees name
        String employeeName = employee.getFirstName();


        // Assigning GUI components to the variables
        txtEmployeeName = findViewById(R.id.txtEmpHub_Name);
        btnAddEmployee = findViewById(R.id.btnEmpHub_AddEmployee);
        btnAddFarmer = findViewById(R.id.btnEmpHub_AddFarmer);
        btnViewProducts = findViewById(R.id.btnEmpHub_ViewProducts);
        btnSignOut = findViewById(R.id.btnEmpHub_SignOut);

        // Showing the Employees Name as the Header
        txtEmployeeName.setAllCaps(true);
        txtEmployeeName.setText(employeeName);

        // Handling the Button OnClicks
        btnAddEmployee.setOnClickListener(this);
        btnAddFarmer.setOnClickListener(this);
        btnViewProducts.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

    }

    // Implementing the OnClockListener Interface
    @Override
    public void onClick(View v) {
        // Handling button clicks
        switch (v.getId()){
            // ADD EMPLOYEE
            case R.id.btnEmpHub_AddEmployee:
                Intent toAddEmployee = new Intent(EmployeeHub.this, AddEmployee.class);
                toAddEmployee.putExtra("EMPLOYEE",employee);
                startActivity(toAddEmployee);
                finish();
                break;

            // ADD FARMER
            case R.id.btnEmpHub_AddFarmer:
                Intent toAddFarmer = new Intent(EmployeeHub.this,AddFarmer.class);
                toAddFarmer.putExtra("EMPLOYEE",employee);
                startActivity(toAddFarmer);
                finish();
                break;

            // VIEW PRODUCTS
            case R.id.btnEmpHub_ViewProducts:
                Intent toViewProduct = new Intent(EmployeeHub.this,EView_Products.class);
                toViewProduct.putExtra("EMPLOYEE",employee);
                startActivity(toViewProduct);
                finish();
                break;

            // SIGN OUT
            case R.id.btnEmpHub_SignOut:
                Intent toStartScreen = new Intent(EmployeeHub.this, StartScreen.class);
                startActivity(toStartScreen);
                finish();
                break;
        }
        // END OF SWITCH
    }
}