package com.keshanpadayachee.farmcentral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity implements View.OnClickListener {

    // Declaring variables for the GUI components
    private Button btnEmployee, btnFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // Assigning GUI components to the variables
        btnEmployee = findViewById(R.id.btnStartScreen_Employee);
        btnFarmer = findViewById(R.id.btnStartScreen_Farmer);

        btnEmployee.setOnClickListener(this);
        btnFarmer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // EMPLOYEE BUTTON
            case R.id.btnStartScreen_Employee:
                Intent EmployeeLogIn = new Intent(StartScreen.this, com.keshanpadayachee.farmcentral.Employee.EmployeeLogIn.class);
                startActivity(EmployeeLogIn);
                break;
            // FARMER BUTTON
            case R.id.btnStartScreen_Farmer:
                Intent FarmerLogIn = new Intent(StartScreen.this, com.keshanpadayachee.farmcentral.Farmer.FarmerLogIn.class);
                startActivity(FarmerLogIn);
                break;
        }
        // END OF SWITCH
    }
}