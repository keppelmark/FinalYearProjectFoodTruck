package com.example.fypfoodtruck.fypfoodtruck;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class Admin extends AppCompatActivity {
    private static final String TAG = "Admin";

    private static final String KEY_TITLE = "name";
    private static final String KEY_TYPE = "category";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_COUNTY = "county";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_WEBSITE = "website";

    private EditText editTextName;
    private Spinner mCategorySpinner;
    private EditText editTextAddress;
    private Spinner mCountySpinner;
    private EditText editTextPhone;
    private EditText editTextWebsite;

    FirebaseAuth fAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Businesses");



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        fAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.edit_text_name);
        mCategorySpinner = findViewById(R.id.spinner_category);
        editTextAddress = findViewById(R.id.edit_text_address);
        mCountySpinner = findViewById(R.id.spinner_county);
        editTextPhone = findViewById(R.id.edit_text_number);
        editTextWebsite = findViewById(R.id.edit_text_website);

    }

    protected void onStart() {
        super.onStart();
        userRef.addSnapshotListener(this, (queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            String data = "";
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Business food = documentSnapshot.toObject(Business.class);
                food.setDocumentId(documentSnapshot.getId());
                String documentId = food.getDocumentId();
                String name = food.getName();
                String category = food.getCategory();
                String address = food.getAddress();
                String county = food.getCounty();
                String number = food.getNumber();
                String website = food.getWebsite();
                data += "ID: " + documentId
                        + "\nName: " + name + "\nCategory: " + category + "\nAddress: " + address
                        + "\nCounty: " + county + "\nNumber: " + number + "\nWebsite: " + website + "\n\n";
            }

        });
    }

    public void addBusiness(View v) {
        String name = editTextName.getText().toString();
        String category = (String) mCategorySpinner.getSelectedItem();
        String address = editTextAddress.getText().toString();
        String county = (String) mCountySpinner.getSelectedItem();
        String number = editTextPhone.getText().toString();
        String website = editTextWebsite.getText().toString();
        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
        Business business = new Business(name, category, address, county, number, website, userId);
        userRef.add(business);
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }
}

