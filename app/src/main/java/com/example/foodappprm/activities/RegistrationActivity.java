package com.example.foodappprm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodappprm.MainActivity;
import com.example.foodappprm.Models.User;
import com.example.foodappprm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fullname, registerEmail, registerPassword;
    private Button registerButton;
    private TextView loginRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.editText1);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button);
        loginRedirect = findViewById(R.id.loginRedirect);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullname.getText().toString().trim();
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                if (name.isEmpty()) {
                    registerButton.setError("Full name cannot be empty");
                }
                if (email.isEmpty()) {
                    registerButton.setError("Email cannot be empty");
                }
                if (password.isEmpty()) {
                    registerPassword.setError("Password cannot be empty");
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Cập nhật displayName (full name)
                                    if (user != null) {
                                        UserProfileChangeRequest profileUpdates =
                                                new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(name)
                                                    .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(profileTask -> {
                                                if (profileTask.isSuccessful()) {
                                                    Log.d("REGISTER", "User profile updated.");
                                                }
                                            });

                                        String uid = user.getUid();
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                        User newUser = new User(name, email);
                                        ref.child(uid).setValue(newUser)
                                                .addOnCompleteListener(databaseTask -> {
                                                    if (databaseTask.isSuccessful()) {
                                                        Log.d("REGISTER", "User data saved to database");
                                                    } else {
                                                        Log.e("REGISTER", "Failed to save user data", databaseTask.getException());
                                                    }
                                                });
                                }

                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("REGISTER", "Error", task.getException());
                            }
                        }
                    });
                }
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void mainActivity(View view) {
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
    }
}