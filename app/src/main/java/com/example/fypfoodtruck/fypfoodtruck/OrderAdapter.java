package com.example.fypfoodtruck.fypfoodtruck;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.bumptech.glide.Glide;
import com.google.firebase.example.fireeats.util.RestaurantUtil;*/
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;


public class OrderAdapter extends FirestoreAdapter<OrderAdapter.ViewHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;
    private Query mQuery;

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


        TextView customerView;
        TextView dateView;
        TextView statusView;
        CheckBox check;


        public ViewHolder(View itemView) {
            super(itemView);


            customerView = itemView.findViewById(R.id.list_customer);
            dateView = itemView.findViewById(R.id.list_date);
            statusView = itemView.findViewById(R.id.list_status);
            check = itemView.findViewById(R.id.checkbox);

        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnRestaurantSelectedListener listener) {

            Order order = snapshot.toObject(Order.class);
            Resources resources = itemView.getResources();

            customerView.setText("Customer : " + order.getCustomerName());
            Date date = new Date(order.getDate());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String stringDate = DateFor.format(date);
            dateView.setText("Date : " + stringDate);

            String status = "Status :";
            if (order.getStatus() == 1) {
                status += " Ordered";
            } else if (order.getStatus() == 2) {
                status += " Paid";

            } else if (order.getStatus() == 3) {
                status += " Collected";

            }
            statusView.setText(status);
            check.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    statusView.setText("Status : Collected");
                }

            });


            // Click listener
            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onRestaurantSelected(snapshot);
                }
            });
        }

    }
}
