package com.example.sqliteazwarbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Tambah_data extends AppCompatActivity {

    BiodataTbl biodataTbl;
    EditText nama, alamat;
    Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data);
        nama=findViewById(R.id.nama);
        alamat=findViewById(R.id.alamat);
        simpan=findViewById(R.id.simpan_data);
        getSupportActionBar().setTitle("Tambahkan Data");

        biodataTbl=new BiodataTbl(getApplicationContext());

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan_data();
                finish();
            }
        });
    }
    void simpan_data()
    {
        biodataTbl.simpan_data(
                nama.getText().toString(),
                alamat.getText().toString()
        );
    }
}