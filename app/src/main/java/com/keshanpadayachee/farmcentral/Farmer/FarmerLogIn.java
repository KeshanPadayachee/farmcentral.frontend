package com.keshanpadayachee.farmcentral.Farmer;

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
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.R;
import com.keshanpadayachee.farmcentral.StartScreen;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FarmerLogIn extends AppCompatActivity {

    // Declaring variables for the GUI components
    private EditText edtIDNumber, edtPassword;
    private Button btnLogIn;
    private ImageView imgBack;

    // Variables for API
    CompositeDisposable compositeDisposable;
    FarmCentralAPI APIService;
    List<mFarmer> lstAllFarmers = new ArrayList<>();


    public FarmerLogIn() {
        compositeDisposable = new CompositeDisposable();
        // Creating an instance of the RetrofitClient class
        Retrofit retrofit = RetrofitClient.getRetrofit();
        APIService = retrofit.create(FarmCentralAPI.class);
        // Calling method to get all Employees
        getFarmersFromAPI();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_log_in);

        // Assigning GUI components to the variables
        edtIDNumber = findViewById(R.id.edtFLogIn_IDNumber);
        edtPassword = findViewById(R.id.edtFLogIn_Password);
        btnLogIn = findViewById(R.id.btnFLogIn_LogIn);
        imgBack = findViewById(R.id.imgFarmerLogIn_Back);

        // Handling the LOGIN BUTTON ONCLICK
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting the Farmers ID Number and Password
                String IDNumber = edtIDNumber.getText().toString();
                String Password = edtPassword.getText().toString();

                if (IDNumber.isEmpty() || Password.isEmpty()){
                    // No Login details entered
                    Toast.makeText(FarmerLogIn.this, "Please enter your login details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    // Searching for the Farmer
                    for (mFarmer checker : lstAllFarmers) {
                        if (checker.getFarmerID().equals(IDNumber) && checker.getPasswordHash().equals(Password)) {
                            // Farmer is found, going to hub
                            Intent toFarmerHub = new Intent(FarmerLogIn.this, FarmerHub.class);
                            toFarmerHub.putExtra("FARMER", checker);
                            startActivity(toFarmerHub);
                            finish();
                            return;
                        }
                        // END OF IF
                    }
                    // END OF FOR LOOP
                }
                // END OF IF-ELSE
                Toast.makeText(FarmerLogIn.this, "Incorrect login details", Toast.LENGTH_SHORT).show();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to Start Screen
                Intent toStartScreen = new Intent(FarmerLogIn.this, StartScreen.class);
                startActivity(toStartScreen);
                finish();
            }
        });
    }

    // Method to get all the farmers from the database via API
    public void getFarmersFromAPI() {
        // Creating a composite disposable object
        compositeDisposable = new CompositeDisposable();

        // Building the CompositeDisposable
        compositeDisposable.add(APIService.GetFarmer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<mFarmer>>() {
                    @Override
                    public void accept(List<mFarmer> mFarmers) throws Exception {
                        lstAllFarmers.clear();
                        // Looping through database records and creating a local version
                        for (mFarmer reader : mFarmers) {
                            String ID = reader.getFarmerID();
                            String name = reader.getFirstName();
                            String last = reader.getLastName();
                            String salt = reader.getPasswordSalt();
                            String hash = reader.getPasswordHash();
                            // Creating a Farmer object
                            mFarmer farmer = new mFarmer(ID, name, last, salt, hash);
                            // Adding Farmer object to the list
                            lstAllFarmers.add(farmer);
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