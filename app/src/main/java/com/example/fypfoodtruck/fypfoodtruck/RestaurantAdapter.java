/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.*/


package com.example.fypfoodtruck.fypfoodtruck;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.bumptech.glide.Glide;
import com.google.firebase.example.fireeats.util.RestaurantUtil;*/
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;



/**
 * RecyclerView adapter for a list of Restaurants.*/


public class RestaurantAdapter extends FirestoreAdapter<RestaurantAdapter.ViewHolder> {

    public interface OnRestaurantSelectedListener {

        void onRestaurantSelected(DocumentSnapshot restaurant);

    }

    private OnRestaurantSelectedListener mListener;

    public RestaurantAdapter(Query query, OnRestaurantSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_restaurant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView nameView;
        TextView categoryView;
        TextView addressView;
        TextView countyView;
        TextView websiteView;
        TextView numberView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.restaurant_item_name);
            categoryView = itemView.findViewById(R.id.restaurant_item_category);
            addressView = itemView.findViewById(R.id.restaurant_item_address);
            countyView = itemView.findViewById(R.id.restaurant_item_county);
            websiteView = itemView.findViewById(R.id.restaurant_item_website);
            numberView = itemView.findViewById(R.id.restaurant_item_number);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnRestaurantSelectedListener listener) {

            Business food = snapshot.toObject(Business.class);
            Resources resources = itemView.getResources();

            // Load image
            /*Glide.with(imageView.getContext())
                    .load(restaurant.getPhoto())
                    .into(imageView);*/

            nameView.setText(food.getName());
            categoryView.setText(food.getCategory());
            addressView.setText(food.getAddress());
            countyView.setText(food.getCounty());
            websiteView.setText(food.getWebsite());
            numberView.setText(food.getNumber());



            // Click listener
            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onRestaurantSelected(snapshot);
                }
            });
        }

    }
}

