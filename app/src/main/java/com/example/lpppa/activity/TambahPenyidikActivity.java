package com.example.lpppa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lpppa.MainActivity;
import com.example.lpppa.R;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.Penyidik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPenyidikActivity extends AppCompatActivity {

    private String[] strings = {
            "AKBP", "AKP", "IPTU", "IPDA", "AIPTU", "AIPDA", "BRIPKA", "BRIGPOL", "BRIPTU",
            "BRIPDA"
    };
    private EditText etNrp, etNama, etNotelpon;
    RadioButton Kanit, Panit, Penyidik, Banum;
    String userJabatan="";
    String pangkatbaru = "";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyidik);

        Spinner spin = findViewById(R.id.spinner1);
        etNama = findViewById(R.id.et_tp_nama);
        etNrp = findViewById(R.id.et_tp_nrp);
        Kanit = findViewById(R.id.rb_tp_kanit);
        Panit = findViewById(R.id.rb_tp_panit);
        Penyidik = findViewById(R.id.rb_tp_penyidik);
        Banum = findViewById(R.id.rb_tp_banum);
        etNotelpon = findViewById(R.id.et_tp_notelpon);
        Button tambah = findViewById(R.id.btn_simpantambahpny);
        Toolbar mActionToolbar = findViewById(R.id.toolbar_tambahpenyidik);

        setSupportActionBar(mActionToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tambah Penyidik");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, strings);

        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pangkatbaru = adapter.getItem(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menambahkan Anggota");

        tambah.setOnClickListener(view -> {
            cariNrp();
        });


    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        Intent intent = new Intent(this, PenyidikActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void cariNrp(){
        ApiService mApiService = RetrofitClient.cariRetroNrpoPenyidik();
        mApiService.cariNrpPenyidik("cariNrp","penyidik",etNrp.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {

                            JSONObject object = new JSONObject(response.body().string());
                            String hasil = object.optString("hasil");
                            if (hasil.equals("yes")){
                                etNrp.setError("Penyidik sudah ada");
                            }else {
                                if (etNrp.getText().toString().length() == 0) {
                                    etNrp.setError("NRP tidak boleh kosong");
                                } else if (etNama.getText().toString().length() == 0) {
                                    etNama.setError("Nama tidak boleh kosong");
                                } else {
                                    pDialog.show();
                                    tambahPenyidik();
                                }
                            }

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    public void RadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_tp_kanit:
                if (checked)
                    userJabatan = "Kanit";
                break;
            case R.id.rb_tp_panit:
                if (checked)
                    userJabatan = "Panit";
                break;
            case R.id.rb_tp_banum:
                if (checked)
                    userJabatan = "Banum";
                break;
            case R.id.rb_tp_penyidik:
                if (checked)
                    userJabatan = "Penyidik";
                break;
        }
    }

    private void tambahPenyidik(){
        pDialog.show();
        ApiService mApiService = RetrofitClient.updateRetroPenyidik();
        mApiService.tambahPenyidik("insert","penyidik",etNrp.getText().toString(),etNama.getText().toString(),
                "unitidik6",pangkatbaru,userJabatan,etNotelpon.getText().toString(),
                "https://drive.google.com/uc?export=view&id=1x2a7NJnvUZUFdXOeLb_jP0UM0GbdahIF")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pDialog.hide();
                        Toast.makeText(TambahPenyidikActivity.this, "penyidik berhasil di tambah", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahPenyidikActivity.this, PenyidikActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }


}