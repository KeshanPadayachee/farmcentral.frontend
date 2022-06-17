package com.keshanpadayachee.farmcentral.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keshanpadayachee.farmcentral.DBUtils.FarmCentralAPI;
import com.keshanpadayachee.farmcentral.DBUtils.RetrofitClient;
import com.keshanpadayachee.farmcentral.Models.mEmployee;
import com.keshanpadayachee.farmcentral.Models.mFarmer;
import com.keshanpadayachee.farmcentral.Models.mProduct;
import com.keshanpadayachee.farmcentral.MyAdapter;
import com.keshanpadayachee.farmcentral.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class EView_Products extends AppCompatActivity {

    // Declaring variables for the GUI components
    private mEmployee employeeObj;
    private RecyclerView recycler;
    private EditText edtSearch, edtFilter;
    private Button btnFilter;
    private ImageView imgBack;
    private TextView txtFarmerDisplay;


    // List for the filtered products
    List<mProduct> lstFilteredProducts = new ArrayList<>();

    // Variables for the API
    CompositeDisposable compositeDisposable;
    FarmCentralAPI APIService;
    ArrayList<mProduct> lstAllProducts = new ArrayList<>();

    // Constructor
    public EView_Products() {
        compositeDisposable = new CompositeDisposable();

        // Creating an instance of the RetrofitClient
        Retrofit retrofit = RetrofitClient.getRetrofit();
        APIService = retrofit.create(FarmCentralAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eview_products);

        employeeObj = (mEmployee) getIntent().getSerializableExtra("EMPLOYEE");

        edtSearch = findViewById(R.id.edtEViewProducts_FarmerID);
        edtFilter = findViewById(R.id.edtEViewProducts_Filter);
        btnFilter = findViewById(R.id.btnFilter);
        recycler = findViewById(R.id.rvEViewProducts_Recycler);
        imgBack = findViewById(R.id.imgEViewProducts_Back);
        txtFarmerDisplay = findViewById(R.id.txtEViewProducts_FarmerName);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtSearch.getText().toString();
                String type = edtFilter.getText().toString();

                if (id.isEmpty() || type.isEmpty()) {
                    Toast.makeText(EView_Products.this, "Please enter both filter criteria.", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    // Displaying the farmers ID
                    txtFarmerDisplay.setText("Viewing products for:\n" + id);
                    // Calling method to filter the products
                    filterFarmerProducts(id, type);
                    // Initialising the adapter class
                    MyAdapter myAdapter = new MyAdapter(EView_Products.this, lstFilteredProducts);
                    // Setting the recyclers adapted
                    recycler.setAdapter(myAdapter);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to Employee Hub
                Intent toEmployeeHub = new Intent(EView_Products.this, EmployeeHub.class);
                toEmployeeHub.putExtra("EMPLOYEE", employeeObj);
                startActivity(toEmployeeHub);
                finish();
            }
        });

        // Configuring the recycler
        recycler.setLayoutManager(new LinearLayoutManager(EView_Products.this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);

        // Calling method to get Products from the API
        getProductsFromAPI();
    }

    public void getProductsFromAPI() {
        // Creating a composite disposable object
        compositeDisposable = new CompositeDisposable();

        // Building the composite disposable
        compositeDisposable.add(APIService.GetProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        // END OF FOR LOOP
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // Logging any exceptions
                        Log.d("Composite Disposable", throwable.getMessage());
                    }
                }));
    }

    public void filterFarmerProducts(String farmerID, String type) {
        // Clearing the filtered products list
        lstFilteredProducts.clear();
        // Looping through all the products
        for (mProduct filter : lstAllProducts) {
            // Filtering products by farmer ID and productType
            if (filter.getFarmerID().equals(farmerID) && filter.getProductType().equalsIgnoreCase(type)) {
                // Getting the values from the iterated list
                int productID = filter.getProductID();
                String farmerId = filter.getFarmerID();
                String productName = filter.getProductName();
                String productDescription = filter.getProductDescription();
                double productPrice = filter.getProductPrice();
                int productQuantity = filter.getProductQuantity();
                String productType = filter.getProductType();
                // Creating a Product object
                mProduct filteredProduct = new mProduct(productID, farmerID, productName, productDescription, productQuantity, productPrice, productType);
                // Adding the Product object to the list of filtered objects
                lstFilteredProducts.add(filteredProduct);
            }
            // END OF IF
        }
        // END OF FOR LOOP
    }

}