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


public class Consumer extends AppCompatActivity {
    private static final String TAG = "Consumer";

    private static final String KEY_TITLE = "fullName";
    private static final String KEY_NUMBER = "phoneNumber";
    private static final String KEY_EMAIL = "emailAddress";


    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;

    FirebaseAuth fAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Customers");


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);

        fAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.edit_text_name);
        editTextPhone = findViewById(R.id.edit_text_number);
        editTextEmail = findViewById(R.id.edit_text_email);


    }

    protected void onStart() {
        super.onStart();
        userRef.addSnapshotListener(this, (queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }
            String data = "";
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Customer customer = documentSnapshot.toObject(Customer.class);
                customer.setDocumentId(documentSnapshot.getId());
                String documentId = customer.getDocumentId();
                String fullName = customer.getFullName();
                String phoneNumber = customer.getPhoneNumber();
                String userEmail = customer.getUserEmail();


                data += "ID: " + documentId
                        + "\nFull Name: " + fullName + "\nPhone Number: " + phoneNumber + "\nUser Email: "
                        + userEmail + "\n\n";

            }

        });
    }

    public void addCustomer(View v) {
        String fullName  = editTextName.getText().toString();
        String phoneNumber  = editTextPhone.getText().toString();
        String userEmail  = editTextEmail.getText().toString();
        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
        Customer customer = new Customer(fullName, phoneNumber, userEmail, userId);
        userRef.add(customer);
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}
