package com.example.fypfoodtruck.fypfoodtruck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuDetailActivity extends AppCompatActivity implements ProductAdapter.CallBackUs, ProductAdapter.HomeCallBack {

    public static final String KEY_RESTAURANT_ID = "key_restaurant_id";
    public static ArrayList<Item> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    ProductAdapter productAdapter;
    RecyclerView productRecyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String restaurantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        // Get restaurant ID from extras
        restaurantId = getIntent().getExtras().getString(KEY_RESTAURANT_ID);
        if (restaurantId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_RESTAURANT_ID);

        }
        addProduct();



        productAdapter = new ProductAdapter(arrayList, this, this);
        productRecyclerView = findViewById(R.id.product_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(gridLayoutManager);
        productRecyclerView.setAdapter(productAdapter);



    }


    private void addProduct() {
        arrayList.clear();
        DocumentReference dr = db.collection("Businesses").document(restaurantId);
        dr.collection("Menus")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {


                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("fb", document.getId() + " => " + document.getData());
                            try {

                                String product = document.getString("product");
                                String price = document.getString("price");
                                String description = document.getString("description");
                                String duration = document.getString("duration");


                                arrayList.add(new Item(product, price, description, duration));
                                productAdapter.notifyItemInserted(arrayList.size()-1);




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
    public void addCartItemView() {
        //addItemToCartMethod();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(MenuDetailActivity.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == R.id.cart_action) {
            if (cart_count < 1) {
                Toast.makeText(this, "there is no item in cart", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CartActivity.class));
            }
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }
}


