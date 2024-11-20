package com.example.bwurger;

public class pilihanBahanModel {
    public String nama;
    public String gambarUrl;

    public pilihanBahanModel(String nama, String gambarUrl) {
        this.nama = nama;
        this.gambarUrl = gambarUrl;
    }

    public String getGambarUrl() {
        return gambarUrl;
    }

    public String getNamaUrl() {
        return nama;
    }


}
