package com.example.testingfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testingfirebase.Models.Berita;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BeritaActivity extends AppCompatActivity {

    private EditText judul;
    private EditText isi;
    private Button kirim, gambarbtn;
    private ImageView gambar;

    String judulVal, isiVal;
    public static final int pReqCode = 1;
    public static final int REQUESCODE = 1;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        judul = findViewById(R.id.tv_judul_berita);
        isi = findViewById(R.id.tv_isi_berita);
        kirim = findViewById(R.id.btn_kirim_berita);
        gambar = findViewById(R.id.gambar_berita);
        gambarbtn = findViewById(R.id.btn_gambar);

        gambarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermissions();
                } else {
                    openGallery();
                }
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judulVal = judul.getText().toString();
                isiVal = isi.getText().toString();

                if (imgUri != null || !judulVal.isEmpty() || !isiVal.isEmpty()) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("gambar_berita");
                    final StorageReference gambarBerita =storageReference.child(imgUri.getLastPathSegment());
                    gambarBerita.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            gambarBerita.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Task<Uri> uriGambar  = taskSnapshot.getStorage().getDownloadUrl();
                                    while(!uriGambar.isComplete());
                                    Uri url =  uriGambar.getResult();

                                    DatabaseReference beritaRef = FirebaseDatabase.getInstance().getReference("Berita");
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                    Date date = Calendar.getInstance().getTime();
                                    String currentDate = dateFormat.format(date);
                                    String id = beritaRef.push().getKey();
                                    Berita berita = new Berita (judulVal,isiVal,url.toString(),currentDate);

                                    beritaRef.child(id).setValue(berita);
                                    Toast.makeText(BeritaActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else if(imgUri == null || !judulVal.isEmpty() || !isiVal.isEmpty()){
                    DatabaseReference beritaRef = FirebaseDatabase.getInstance().getReference("Berita");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date = Calendar.getInstance().getTime();
                    String currentDate = dateFormat.format(date);
                    String id = beritaRef.push().getKey();
                    Berita berita = new Berita (judulVal,isiVal,"",currentDate);

                    beritaRef.child(id).setValue(berita);
                    Toast.makeText(BeritaActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                } else if(judulVal.isEmpty() || isiVal.isEmpty()) {
                    Toast.makeText(BeritaActivity.this, "Isi data dengan lengkap", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void checkAndRequestForPermissions() {
        if (ContextCompat.checkSelfPermission(BeritaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(BeritaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Pleast accept for required permission", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(BeritaActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, pReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            imgUri = data.getData();
            gambar.setImageURI(imgUri);
        }
    }
}
