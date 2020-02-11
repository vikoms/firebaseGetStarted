package com.example.testingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testingfirebase.Models.Kelas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KelasActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtKelas;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayAdapter<Kelas> adapter;
    ArrayList<Kelas> SpinnerList;
    Spinner spinnerKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);


        edtKelas = findViewById(R.id.edt_kelas);
        Button btnKelas = findViewById(R.id.btn_kelas);
        btnKelas.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Kelas");

        spinnerKelas = findViewById(R.id.spinner_kelas);

        SpinnerList = new ArrayList<>();
        adapter = new ArrayAdapter<>(KelasActivity.this,android.R.layout.simple_spinner_item,SpinnerList);


        spinnerKelas.setAdapter(adapter);
        spinnerKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Kelas selected = (Kelas) adapterView.getSelectedItem();
                Toast.makeText(KelasActivity.this, selected.getKeyKelas(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()) {
                    Kelas kelas = new Kelas(item.getKey(),item.getValue().toString());
                    SpinnerList.add(kelas);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_kelas) {
            String kelasVal =  edtKelas.getText().toString();
            saveData(kelasVal);
        }
    }

    private void saveData(String kelasVal) {

        ref.push().setValue(kelasVal);
    }
}
