package com.example.fypfoodtruck.fypfoodtruck;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<Item> mArrayList;

    public MenuAdapter(ArrayList<Item> arrayList) {
        mArrayList = arrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_single, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mArrayList.get(position);
        holder.productView.setText(item.getProduct());
        holder.priceView.setText(item.getPrice());
        holder.descriptionView.setText(item.getDescription());
        holder.durationView.setText(item.getDuration());


    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productView;
        TextView priceView;
        TextView descriptionView;
        TextView durationView;


        public ViewHolder(View itemView) {
            super(itemView);
            productView = itemView.findViewById(R.id.list_product);
            priceView = itemView.findViewById(R.id.list_price);
            descriptionView = itemView.findViewById(R.id.list_description);
            durationView = itemView.findViewById(R.id.list_duration);

        }

    }

}


