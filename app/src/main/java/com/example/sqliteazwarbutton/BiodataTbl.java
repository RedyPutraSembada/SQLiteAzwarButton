package com.example.sqliteazwarbutton;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class BiodataTbl extends SQLiteOpenHelper {

    Context context;
    Cursor cursor;
    SQLiteDatabase database;

    public static String nama_database="data";
    public static String nama_table="biodata";

    public BiodataTbl(@Nullable Context context) {
        super(context, "name", null, 3);
        this.context=context;
        database=getReadableDatabase();
        database=getWritableDatabase();
    }
// Database Di Buat
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE IF NOT EXISTS "+nama_table+" (id varchar(50), nama varchar(50), alamat varchar(100))";
        sqLiteDatabase.execSQL(query);
    }
// Angka Acak
    String random()
    {
        // 100-999
        int acak=new Random().nextInt(888+1)+100;
        return String.valueOf(acak);
    }
//Insert Ke Database
    void simpan_data(String nama, String alamat){
        database.execSQL(
                "INSERT INTO "+nama_table+" values" +
                        "('"+random()+"','"+nama+"','"+alamat+"')"
        );
        Toast.makeText(context, "Data Tersimpan", Toast.LENGTH_SHORT).show();
    }
//    Update Data
    void Update_data(String id, String nama, String alamat)
    {
        database.execSQL("UPDATE "+nama_table+" SET nama='"+nama+"', alamat='"+alamat+"'" +
                " WHERE id='"+id+"'" +
                "");
        Toast.makeText(context, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show();
    }
//    Tampil Data
    Cursor tampil_data()
    {
        Cursor cursor=database.rawQuery("SELECT * FROM "+nama_table, null);
        return cursor;
    }
//    Hapus Data Yang di pilih
    void delete_data(String id)
    {
        database.execSQL("DELETE FROM "+nama_table+" WHERE id='"+id+"'");
        Toast.makeText(context, "Data Berhasil di hapus berdasarkan yang anda pilih!", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
