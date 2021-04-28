/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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


public class OrderAdapter extends FirestoreAdapter<OrderAdapter.ViewHolder> {

    public interface OnRestaurantSelectedListener {

        void onRestaurantSelected(DocumentSnapshot restaurant);

    }

    private OnRestaurantSelectedListener mListener;

    public OrderAdapter(Query query, OnRestaurantSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.list_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView businessView;
        TextView customerView;
        TextView dateView;
        TextView statusView;


        public ViewHolder(View itemView) {
            super(itemView);

            businessView = itemView.findViewById(R.id.list_business);
            customerView = itemView.findViewById(R.id.list_customer);
            dateView = itemView.findViewById(R.id.list_date);
            statusView = itemView.findViewById(R.id.list_status);

        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnRestaurantSelectedListener listener) {

            Order order = snapshot.toObject(Order.class);
            Resources resources = itemView.getResources();

            businessView.setText(order.getBusinessId());
            customerView.setText(order.getCustomerId());
            dateView.setText(order.getDate());
            statusView.setText("" + order.getStatus());


            // Click listener
            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onRestaurantSelected(snapshot);
                }
            });
        }

    }
}
