package com.example.fypfoodtruck.fypfoodtruck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class Login extends AppCompatActivity {
    EditText email,password;
    Button loginBtn,gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        gotoRegister = findViewById(R.id.gotoRegister);

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               checkField(email);
               checkField(password);

               if(valid){
                   fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                           checkUserAccessLevel(authResult.getUser().getUid());
                           /*startActivity(new Intent(getApplicationContext(), MainActivity.class));*/
                           /*finish();*/

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                       }
                   });

               }
           }
       });


        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));

            }
        });



    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                if(documentSnapshot.getString("isBusiness") != null) {

                    /*Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    intent.putExtra(HomePage.KEY_RESTAURANT_ID, documentSnapshot.getId());

                    startActivity(intent);*/

                    /*startActivity(new Intent(getApplicationContext(), MenuActivity.class));*/
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                    finish();
                }

                if(documentSnapshot.getString("isCustomer") != null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

            }
        });
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }


}