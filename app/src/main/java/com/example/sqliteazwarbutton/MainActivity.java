package com.example.sqliteazwarbutton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button tambah_data;
    ArrayList<Objek> list;

    SQLiteDatabase database;
    Cursor cursor;
    BiodataTbl biodataTbl;
    Custom_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        tambah_data=findViewById(R.id.tambah_data);
        getSupportActionBar().setTitle("Data-data Biodata");

        biodataTbl=new BiodataTbl(getApplicationContext());

        tambah_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tambah_data.class));
            }
        });
        ambil_data();
    }
    void ambil_data()
    {
        list=new ArrayList<Objek>();
        cursor=biodataTbl.tampil_data();
        if (cursor!=null && cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                String id=cursor.getString(0);//id_data
                String nama=cursor.getString(1);//get nama
                String alamat=cursor.getString(2);//get alamat
                list.add(new Objek(
                        id,
                        nama,
                        alamat
                ));
            }
            adapter=new Custom_adapter(getApplicationContext(), list, MainActivity.this);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ambil_data();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}

class Custom_adapter extends BaseAdapter{

    Activity activity;
    BiodataTbl biodataTbl;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Objek> model;
    Custom_adapter(Context context, ArrayList<Objek> list, Activity activity)
    {
        this.context=context;
        this.model=list;
        this.activity=activity;
        layoutInflater=LayoutInflater.from(context);
        biodataTbl=new BiodataTbl(context);
    }



    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView id, nama, alamat;
    Button edit, hapus;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1=layoutInflater.inflate(R.layout.list_data, viewGroup, false);

        id=view1.findViewById(R.id.id);
        nama=view1.findViewById(R.id.nama);
        alamat=view1.findViewById(R.id.alamat);

        id.setText(model.get(position).getId());
        nama.setText(model.get(position).getNama());
        alamat.setText(model.get(position).getAlamat());

        System.out.println(alamat);

        edit=view1.findViewById(R.id.edit);
        hapus=view1.findViewById(R.id.hapus);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Edit_data.class);
                intent.putExtra("nama", model.get(position).getNama());
                intent.putExtra("alamat", model.get(position).getAlamat());
                intent.putExtra("id", model.get(position).getId());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Tanya");
                builder.setMessage("Apakah Anda yakin untuk MENGHAPUS data ini?");
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        biodataTbl.delete_data(model.get(position).getId());
                        Intent intent=new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //((Activity)context).finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });

        return view1;
    }
}