package com.example.testingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testingfirebase.Adapters.MuridAdapter;
import com.example.testingfirebase.Models.Murid;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private List<Murid> listMurid;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child("Murid");


        listMurid =new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_container);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });


        Button btnKelas = findViewById(R.id.btnKelas);
        btnKelas.setOnClickListener(this);

        Button btnPDF = findViewById(R.id.btn_pdf);
        btnPDF.setOnClickListener(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Button btnBerita = findViewById(R.id.btn_berita);
        btnBerita.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnKelas) {
            Intent MoveToKelas  = new Intent(HomeActivity.this, KelasActivity.class);
            startActivity(MoveToKelas);
        } else if(view.getId() == R.id.btn_pdf) {
            Intent moveToPdf = new Intent(HomeActivity.this, UploadPDFActivity.class);
            startActivity(moveToPdf);
        } else if(view.getId() == R.id.btn_berita) {
            Intent moveToBerita = new Intent(HomeActivity.this, BeritaActivity.class);
            startActivity(moveToBerita);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMurid.clear();
                for(DataSnapshot muridSnapshot: dataSnapshot.getChildren()) {
                    Murid murid = muridSnapshot.getValue(Murid.class);
                    listMurid.add(murid);
                }

                MuridAdapter adapter = new MuridAdapter(listMurid,HomeActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
