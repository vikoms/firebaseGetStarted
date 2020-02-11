package com.example.testingfirebase.Models;

import androidx.annotation.NonNull;

public class Kelas {
    private String keyKelas,namaKelas;


    public Kelas(String keyKelas, String namaKelas) {
        this.keyKelas = keyKelas;
        this.namaKelas = namaKelas;
    }

    public String getKeyKelas() {
        return keyKelas;
    }

    public void setKeyKelas(String keyKelas) {
        this.keyKelas = keyKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    @NonNull
    @Override
    public String toString() {
        return namaKelas;
    }
}
