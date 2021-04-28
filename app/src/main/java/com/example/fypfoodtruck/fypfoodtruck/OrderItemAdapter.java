package com.example.fypfoodtruck.fypfoodtruck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    public static ArrayList<OrderItem> ordersArray;

    public OrderItemAdapter(ArrayList<OrderItem> ordersArray) {
        OrderItemAdapter.ordersArray = ordersArray;

    }


    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_adapter_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.productName.setText(ordersArray.get(i).getItemName());
        viewHolder.productId.setText(ordersArray.get(i).getQuantity());

    }

    @Override
    public int getItemCount() {
        return ordersArray.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productId;

        ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.android_gridview_text);
            productId = itemView.findViewById(R.id.android_itemId);
        }
    }
}


