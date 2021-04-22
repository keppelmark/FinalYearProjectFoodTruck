package com.example.fypfoodtruck.fypfoodtruck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.example.fypfoodtruck.fypfoodtruck.ProductAdapter.cartModels;
import static com.example.fypfoodtruck.fypfoodtruck.ProductAdapter.productsArray;


public class CartActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "com.example.fypfoodtruck.fypfoodtruck.EXTRA_NUMBER";
    public static final String EXTRA_ORDERID = "com.example.fypfoodtruck.fypfoodtruck.EXTRA_ORDERID";

    @SuppressLint("StaticFieldLeak")
    public static TextView grandTotal;
    public static int grandTotalplus;

    public static ArrayList<ProductImage> temparraylist;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    LinearLayout proceedToBook;
    Context context;
    private String businessId;
    private String customerId;
    FirebaseAuth fAuth;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Orders");


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        fAuth = FirebaseAuth.getInstance();
        context = this;
        temparraylist = new ArrayList<>();
        Toolbar mToolbar = findViewById(R.id.toolbar);
        proceedToBook = findViewById(R.id.proceed_to_book);
        grandTotal = findViewById(R.id.grand_total_cart);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cart");


        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        mToolbar.setNavigationOnClickListener(v -> {

            grandTotalplus = 0;
            for (int i = 0; i < temparraylist.size(); i++) {

            }
            cartModels.addAll(temparraylist);
            MenuDetailActivity.cart_count = (temparraylist.size());
            finish();
        });
        MenuDetailActivity.cart_count = 0;


        Log.d("sizecart_1", String.valueOf(temparraylist.size()));
        Log.d("sizecart_2", String.valueOf(cartModels.size()));


        temparraylist.addAll(cartModels);
        cartModels.clear();
        Log.d("sizecart_11", String.valueOf(temparraylist.size()));
        Log.d("sizecart_22", String.valueOf(cartModels.size()));

        for (int i = 0; i < temparraylist.size(); i++) {
            grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
        }
        grandTotal.setText("" + grandTotalplus);
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartAdapter = new CartAdapter(temparraylist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(cartAdapter);

        proceedToBook.setOnClickListener(v -> openCheckoutActivity());
    }

    public void openCheckoutActivity() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        Order order = new Order(customerId, businessId, date.toString(), 1);

        orderRef.add(order).addOnSuccessListener(documentReference -> {
            TextView itemView = findViewById(R.id.product_cart_code);
            String itemId = itemView.getText().toString();
            TextView quantityView = findViewById(R.id.cart_product_quantity_tv);
            String quantity = quantityView.getText().toString();
            Toast.makeText(CartActivity.this, itemId, Toast.LENGTH_SHORT).show();
            DocumentReference docRef = orderRef.document(documentReference.getId());

            String orderId = docRef.getId();
            OrderItem orderItem = new OrderItem(orderId, itemId, quantity);


            docRef.collection("OrderItems").add(orderItem);


            TextView textView = findViewById(R.id.grand_total_cart);

            int number = Integer.parseInt(textView.getText().toString());
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra(EXTRA_NUMBER, number);
            intent.putExtra(EXTRA_ORDERID, orderId);

            startActivity(intent);


        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        grandTotalplus = 0;
        for (int i = 0; i < temparraylist.size(); i++) {
            MenuDetailActivity.cart_count = (temparraylist.size());

        }
        cartModels.addAll(temparraylist);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;

        // query customerId
        customerId = fAuth.getCurrentUser().getUid();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            businessId = extras.getString("businessId");
        }


    }
}





