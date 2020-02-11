package com.example.testingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testingfirebase.Models.Murid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    Button btn_signUp,btn_signIn;
    EditText txtEmail,txtPassword,txtNis,txtTelp,txtNama;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn_signIn = (Button) findViewById(R.id.btnSignIn);
        btn_signUp = (Button) findViewById(R.id.btnSignUp);

        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtPassword = (EditText) findViewById(R.id.edtPassword);
        txtNis = (EditText) findViewById(R.id.edtNis);
        txtTelp = (EditText) findViewById(R.id.edtTelp);
        txtNama = (EditText) findViewById(R.id.edtNama);

        btn_signIn.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Toast.makeText(this, "Anda Sudah Login", Toast.LENGTH_SHORT).show();
        }

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child("Murid");

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.btnSignUp:
                final String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                final String nis = txtNis.getText().toString().trim();
                final String nama = txtNama.getText().toString().trim();
                final String telp= txtTelp.getText().toString().trim();


                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String id =mAuth.getCurrentUser().getUid();
                            Murid murid = new Murid(email,nama,nis,telp);
                            ref.child(id).setValue(murid);
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.signUpContainer),"Register Berhasil",Snackbar.LENGTH_LONG);
                            snackbar.show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, String.valueOf(task.getException()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    }
}
