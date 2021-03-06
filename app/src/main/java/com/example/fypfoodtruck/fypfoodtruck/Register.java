package com.example.fypfoodtruck.fypfoodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    EditText fullName, email, password, phone;
    Button registerBtn, goToLogin;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox isBusinessBox, isCustomerBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.gotoLogin);

        isBusinessBox = findViewById(R.id.isBusiness);
        isCustomerBox = findViewById(R.id.isCustomer);

        isCustomerBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (compoundButton.isChecked()) {
                isBusinessBox.setChecked(false);
            }

        });

        isBusinessBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (compoundButton.isChecked()) {
                isCustomerBox.setChecked(false);
            }

        });


        registerBtn.setOnClickListener(v -> {
            checkField(fullName);
            checkField(email);
            checkField(password);
            checkField(phone);

            if (!(isBusinessBox.isChecked() || isCustomerBox.isChecked())) {
                Toast.makeText(Register.this, "Select the Account Type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (valid) {
                fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(authResult -> {
                    FirebaseUser user = fAuth.getCurrentUser();
                    String userId = user.getUid();
                    Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("FullName", fullName.getText().toString());
                    userInfo.put("UserEmail", email.getText().toString());
                    userInfo.put("PhoneNumber", phone.getText().toString());


                    if (isBusinessBox.isChecked()) {
                        userInfo.put("isBusiness", "1");
                    }
                    if (isCustomerBox.isChecked()) {
                        userInfo.put("isCustomer", "1");
                    }


                    df.set(userInfo);

                    if (isBusinessBox.isChecked()) {
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                        finish();
                    }

                    if (isCustomerBox.isChecked()) {
                        startActivity(new Intent(getApplicationContext(), Consumer.class));
                        finish();
                    }
                }).addOnFailureListener(e -> Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_SHORT).show());

            }


        });

        goToLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));
    }


    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }
}