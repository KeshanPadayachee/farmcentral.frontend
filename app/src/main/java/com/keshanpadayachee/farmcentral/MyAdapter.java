package com.keshanpadayachee.farmcentral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.keshanpadayachee.farmcentral.Models.mProduct;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //String names[], descs[], types[], prices[], qtys[];
    List<mProduct> lstProduct = new ArrayList<>();
    Context context;

    // Constructor
    public MyAdapter(Context ct, List<mProduct> lProducts) {
        context = ct;
        lstProduct = lProducts;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Declaring variables for the GUI components
        TextView txtName, txtDescription, txtType, txtPrice, txtQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assigning GUI components to the variables
            txtName = itemView.findViewById(R.id.txtVPFormat_ProductName);
            txtDescription = itemView.findViewById(R.id.txtVPFormat_ProductDescription);
            txtType = itemView.findViewById(R.id.txtVPFormat_ProductType);
            txtPrice = itemView.findViewById(R.id.txtVPFormat_ProductPrice);
            txtQuantity = itemView.findViewById(R.id.txtVPFormat_ProductQuantity);
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Setting the view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_products_format, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        // Giving the GUI components values
        holder.txtName.setText(lstProduct.get(position).getProductName());
        holder.txtDescription.setText(lstProduct.get(position).getProductDescription());
        holder.txtType.setText(lstProduct.get(position).getProductType());
        holder.txtPrice.setText(String.valueOf(lstProduct.get(position).getProductPrice()));
        holder.txtQuantity.setText(String.valueOf(lstProduct.get(position).getProductQuantity()));
    }

    // Getting the number of items
    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

}
