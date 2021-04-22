package com.example.fypfoodtruck.fypfoodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;*/
/*import com.example.fypfoodtruck.fypfoodtruck.Restaurant;*/
/*import com.google.firebase.example.fireeats.model.Restaurant;*/
/*import com.google.firebase.example.fireeats.util.RestaurantUtil;
import com.google.firebase.example.fireeats.viewmodel.MainActivityViewModel;*/
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OrderActivity extends AppCompatActivity implements
        View.OnClickListener,
        OrderAdapter.OnRestaurantSelectedListener {

    private static final String TAG = "OrderActivity";


    private RecyclerView mOrdersRecycler;
    private ViewGroup mEmptyView;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private OrderAdapter mAdapter;
    private String currentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        /*CheckBox simpleCheckBox = findViewById(R.id.simpleCheckBox);
        simpleCheckBox.setChecked(true);*/
        mOrdersRecycler = findViewById(R.id.recycler_orders);
        mEmptyView = findViewById(R.id.view_empty);
        Intent intent = getIntent();
        currentId = intent.getStringExtra(HomePage.EXTRA_CURRENT_ID);


        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore and the main RecyclerView
        initFirestore();
        initRecyclerView();


    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("Orders").whereEqualTo("businessId", currentId);

    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new OrderAdapter(mQuery, this) {


            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    mOrdersRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mOrdersRecycler.setVisibility(View.VISIBLE);
                    /*mEmptyView.setVisibility(View.GONE);*/
                }
            }

        };

        mOrdersRecycler.setLayoutManager(new LinearLayoutManager(this));
        mOrdersRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();


        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRestaurantSelected(DocumentSnapshot order) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(OrderDetailActivity.KEY_ORDER_ID, order.getId());

        startActivity(intent);

    }
}