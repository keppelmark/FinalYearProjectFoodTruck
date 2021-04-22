package com.example.fypfoodtruck.fypfoodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class HomePage extends AppCompatActivity {

    public static final String EXTRA_CURRENT_ID = "com.example.fypfoodtruck.fypfoodtruck.EXTRA_CURRENT_ID";


    private RecyclerView menuRecycler;
    public static ArrayList<Item> arrayList = new ArrayList<>();
    private String businessId;
    private String userId;
    FirebaseAuth fAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference businessRef = db.collection("Businesses");


    private MenuAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fAuth = FirebaseAuth.getInstance();
        menuRecycler = findViewById(R.id.firestore_list);
        checkBusiness();


    }

    private void checkBusiness() {
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        userId = user.getUid();
        businessRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            Business business = document.toObject(Business.class);

                            if (Objects.equals(business.getUserId(), userId)) {


                                businessId = document.getId();
                                addProduct();


                                adapter = new MenuAdapter(arrayList);
                                menuRecycler.setLayoutManager(new LinearLayoutManager(this));
                                menuRecycler.setAdapter(adapter);

                            }
                        }
                    }


                });
    }


    private void addProduct() {
        arrayList.clear();
        DocumentReference dr = db.collection("Businesses").document(businessId);
        dr.collection("Menus")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {


                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("fb", document.getId() + " => " + document.getData());
                            try {

                                String documentId = document.getString("documentId");
                                String product = document.getString("product");
                                String price = document.getString("price");
                                String description = document.getString("description");
                                String duration = document.getString("duration");


                                arrayList.add(new Item(documentId, product, price, description, duration));
                                adapter.notifyItemInserted(arrayList.size() - 1);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }


    public void AddItem(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    public void SignOut(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void ViewOrders(MenuItem item) {
        /*startActivity(new Intent(getApplicationContext(), OrderActivity.class));*/
        String currentId = businessId;
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(EXTRA_CURRENT_ID, currentId);
        startActivity(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();


    }
}










