package com.example.testingfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testingfirebase.Adapters.PDFAdapter;
import com.example.testingfirebase.Models.UploadPDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadPDFActivity extends AppCompatActivity implements View.OnClickListener {

    StorageReference mStorageRef;
    DatabaseReference database;
    EditText edtName;
    Button btnUpload;
    List<UploadPDF> mList;
    RecyclerView recyclerView;
    PDFAdapter adapter;
    private static final int REQ_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);


        edtName = findViewById(R.id.pdf_name);
        btnUpload = findViewById(R.id.btn_upload);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference("Perpustakaan");

        btnUpload.setOnClickListener(this);

        mList = new ArrayList<>();

        viewAllPdf();
        recyclerView = findViewById(R.id.rv_pdf);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void viewAllPdf() {

        database = FirebaseDatabase.getInstance().getReference("Perpustakaan");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList.clear();
                for(DataSnapshot files : dataSnapshot.getChildren()) {
                    UploadPDF uploadPDF = files.getValue(UploadPDF.class);
                    mList.add(uploadPDF);
                }

                adapter = new PDFAdapter(mList,UploadPDFActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_upload) {
            selectPDFFile();
        }
    }

    private void selectPDFFile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select pdf file"),REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQ_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
                uploadPDFFile(data.getData());
        }

    }

    private void uploadPDFFile(final Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading....");
        progressDialog.show();

        StorageReference reference = mStorageRef.child("perpustakaan");
        StorageReference pdfFilePath = reference.child(UUID.randomUUID().toString());
        pdfFilePath.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri  = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url =  uri.getResult();

                UploadPDF uploadPDF = new UploadPDF(edtName.getText().toString().trim(), url.toString());
                String id = database.push().getKey();
                database.child(id).setValue(uploadPDF);
                Toast.makeText(UploadPDFActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded : " + (int) progress+"%" );
            }
        });

    }
}
