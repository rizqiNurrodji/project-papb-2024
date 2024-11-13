package com.example.bwurger;

public class rotiModel {
    public String nama;
    public String gambarUrl;

    public rotiModel(String nama, String gambarUrl) {
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
