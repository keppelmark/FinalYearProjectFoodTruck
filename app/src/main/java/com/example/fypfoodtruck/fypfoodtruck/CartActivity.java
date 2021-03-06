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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.fypfoodtruck.fypfoodtruck.ProductAdapter.cartModels;
import static com.example.fypfoodtruck.fypfoodtruck.ProductAdapter.productsArray;


public class CartActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "com.example.fypfoodtruck.fypfoodtruck.EXTRA_NUMBER";
    public static final String EXTRA_ORDERID = "com.example.fypfoodtruck.fypfoodtruck.EXTRA_ORDERID";
    public static final String EXTRA_DURATION = "com.example.fypfoodtruck.fypfoodtruck.EXTRA_DURATION";

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
    private String customerName;
    FirebaseAuth fAuth;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Orders");
    private CollectionReference userRef = db.collection("Users");


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

        Order order = new Order(customerName, customerId, businessId, date.toString(), 1);

        orderRef.add(order).addOnSuccessListener(documentReference -> {


            DocumentReference docRef = orderRef.document(documentReference.getId());

            for (int i = 0; i < temparraylist.size(); i++) {
                Map<String, Object> docData = new HashMap<>();
                String orderId = docRef.getId();
                docData.put("orderId", orderId);
                docData.put("itemName", temparraylist.get(i).getProductName());
                docData.put("item", temparraylist.get(i).getProductCode());
                docData.put("quantity", temparraylist.get(i).productQuantity);


                OrderItem orderItem = new OrderItem(Objects.requireNonNull(docData.get("orderId")).toString(), Objects.requireNonNull(docData.get("itemName")).toString(), Objects.requireNonNull(docData.get("item")).toString(), Objects.requireNonNull(docData.get("quantity")).toString());


                DocumentReference subRef = docRef.collection("OrderItems").document();
                subRef.set(orderItem);


                TextView textView = findViewById(R.id.grand_total_cart);

                int number = Integer.parseInt(textView.getText().toString());
                String duration = temparraylist.get(i).getProductDuration();
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra(EXTRA_NUMBER, number);
                intent.putExtra(EXTRA_ORDERID, orderId);
                intent.putExtra(EXTRA_DURATION, duration);


                startActivity(intent);
            }


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
        DocumentReference docRef = userRef.document(customerId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    customerName = document.getString("FullName");

                } else {
                    Log.d("LOGGER", "No such document");
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.getException());
            }
        });




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            businessId = extras.getString("businessId");
        }


    }
}





