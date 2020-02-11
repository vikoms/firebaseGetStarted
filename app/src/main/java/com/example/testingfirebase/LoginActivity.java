package com.example.testingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_signUp, btn_signIn;
    EditText txtEmail, txtPassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        btn_signIn = (Button) findViewById(R.id.btnSignIn);
        btn_signIn.setOnClickListener(this);
        btn_signUp = (Button) findViewById(R.id.btnSignUp);
        btn_signUp.setOnClickListener(this);

        txtEmail = (EditText) findViewById(R.id.edtEmailLogin);
        txtPassword = (EditText) findViewById(R.id.edtPasswordLogin);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Intent main = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(main);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                final String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, String.valueOf(task.getException()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btnSignUp :
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
