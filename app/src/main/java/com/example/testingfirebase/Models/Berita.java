package com.example.testingfirebase.Models;

public class Berita {

    private String judulBerita,isiBerita,gambarBerita,tanggalBerita;

    public Berita(String judulBerita, String isiBerita, String gambarBerita, String tanggalBerita) {
        this.judulBerita = judulBerita;
        this.isiBerita = isiBerita;
        this.gambarBerita = gambarBerita;
        this.tanggalBerita = tanggalBerita;
    }

    public String getJudulBerita() {
        return judulBerita;
    }

    public void setJudulBerita(String judulBerita) {
        this.judulBerita = judulBerita;
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
    }

    public String getGambarBerita() {
        return gambarBerita;
    }

    public void setGambarBerita(String gambarBerita) {
        this.gambarBerita = gambarBerita;
    }

    public String getTanggalBerita() {
        return tanggalBerita;
    }

    public void setTanggalBerita(String tanggalBerita) {
        this.tanggalBerita = tanggalBerita;
    }
}
