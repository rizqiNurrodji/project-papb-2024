package com.example.bwurger;

public class pilihanBahanModel {
    public String nama;
    public String gambar;

    public pilihanBahanModel(String nama, String gambar) {
        this.nama = nama;
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }

    public String getNama() {
        return nama;
    }
}
