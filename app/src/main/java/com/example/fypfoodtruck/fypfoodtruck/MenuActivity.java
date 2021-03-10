package com.example.fypfoodtruck.fypfoodtruck;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;


public class MenuActivity extends AppCompatActivity {
    private EditText editTextProduct;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextDuration;
    private String businessId;
    private String userId;
    FirebaseAuth fAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference businessRef = db.collection("Businesses");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fAuth = FirebaseAuth.getInstance();
        editTextProduct = findViewById(R.id.edit_text_product);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDuration = findViewById(R.id.edit_text_duration);

    }


    public void addProduct(View v) {


        String product = editTextProduct.getText().toString();
        String price = editTextPrice.getText().toString();
        String description = editTextDescription.getText().toString();
        String duration = editTextDuration.getText().toString();


        Item item = new Item(product, price, description, duration);


        businessRef.document(businessId)
                .collection("Menus").add(item);


        editTextProduct.setText("");
        editTextPrice.setText("");
        editTextDescription.setText("");
        editTextDuration.setText("");


        Toast.makeText(MenuActivity.this, "Item added", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        userId = user.getUid();
        businessRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            /*Log.d(TAG, document.getId() + " => " + document.getData());*/
                            Business business = document.toObject(Business.class);

                            if (Objects.equals(business.getUserId(), userId)) {


                                businessId = document.getId();

                            }
                        }
                    }


                });
    }
}








