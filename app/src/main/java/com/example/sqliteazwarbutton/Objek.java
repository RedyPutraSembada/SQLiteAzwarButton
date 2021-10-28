package com.example.sqliteazwarbutton;

public class Objek {
    String id="", nama="", alamat="";
    Objek(String id, String nama, String alamat)
    {
        this.id=id;
        this.nama=nama;
        this.alamat=alamat;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }
}
