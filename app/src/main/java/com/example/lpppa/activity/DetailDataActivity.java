package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lpppa.R;

public class DetailDataActivity extends AppCompatActivity {
    private TextView noLp, perkembangan, tanggal, uu, penyidik, namaPelapor, jenisKelPelapor,
    alamatPelapor, namaKorban, jenisKelKorban, alamatKorban, namaTerlapor, alamatTerlapor,
    jenisKelTerlapor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
    }

    private void inisiasi(){
        noLp = findViewById(R.id.tv_ddlp);
        perkembangan = findViewById(R.id.tv_perkembangan);
        tanggal = findViewById(R.id.tv_ddtanggal);
        uu = findViewById(R.id.tv_ddpasal);
        penyidik = findViewById(R.id.tv_ddpenyidik);
        namaPelapor = findViewById(R.id.tv_ddnamapelapor);
        jenisKelPelapor = findViewById(R.id.tv_ddjeniskelpelapor);
        alamatPelapor = findViewById(R.id.tv_ddlp);
        namaKorban = findViewById(R.id.tv_ddlp);
        alamatKorban = findViewById(R.id.tv_ddlp);
        jenisKelKorban = findViewById(R.id.tv_ddlp);
        namaTerlapor = findViewById(R.id.tv_ddlp);
        alamatTerlapor = findViewById(R.id.tv_ddlp);
        jenisKelTerlapor = findViewById(R.id.tv_ddlp);
    }
}