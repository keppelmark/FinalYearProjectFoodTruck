package com.example.fypfoodtruck.fypfoodtruck;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String KEY_ORDER_ID = "key_order_id";
    public static ArrayList<OrderItem> arrayList = new ArrayList<>();
    OrderItemAdapter orderItemAdapter;
    RecyclerView productRecyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String restaurantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // Get restaurant ID from extras
        restaurantId = getIntent().getExtras().getString(KEY_ORDER_ID);
        if (restaurantId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_ORDER_ID);

        }
        addProduct();


        orderItemAdapter = new OrderItemAdapter(arrayList);
        productRecyclerView = findViewById(R.id.product_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(gridLayoutManager);
        productRecyclerView.setAdapter(orderItemAdapter);


    }


    private void addProduct() {
        arrayList.clear();
        DocumentReference dr = db.collection("Orders").document(restaurantId);
        dr.collection("OrderItems")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {


                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("fb", document.getId() + " => " + document.getData());
                            try {
                                String orderId = document.getString("orderId");
                                String item = document.getString("item");
                                String quantity = document.getString("quantity");

                                arrayList.add(new OrderItem(orderId, item, quantity));
                                orderItemAdapter.notifyItemInserted(arrayList.size() - 1);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                    } else {
                        Log.d("fb", "Error getting documents: ", task.getException());
                    }

                });


    }


    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }
}