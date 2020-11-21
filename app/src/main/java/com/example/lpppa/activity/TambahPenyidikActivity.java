package com.example.lpppa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

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

import androidx.appcompat.app.AppCompatActivity;
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
        CardView tambah = findViewById(R.id.cv_simpantambahpny);

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
            getPenyidik();

        });


    }

    private void getPenyidik(){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","penyidik").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("penyidik");

                    ArrayList<String> items = new ArrayList<>();

                    assert jsonArray != null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrpx = jsonObject.optString("nrp");


                        if (etNrp.getText().toString().equals(nrpx) ) {
                            etNrp.setError("Penyidik sudah ada");

                        }else {
                            items.add(nrpx);
                        }

                    }cobanrp(items);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TambahPenyidikActivity.this, "masukk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    private void cobanrp(ArrayList <String> list){
        for (int i = 0; i < list.size(); i++) {
            if (etNrp.getText().toString().equals(list.get(i))) {
//                Toast.makeText(TambahPenyidikActivity.this, "Penyidik sudah terdaftar",
//                        Toast.LENGTH_SHORT).show();
//                etNrp.setError("Penyidik sudah ada");

            } else {
                if (etNrp.getText().toString().length() == 0) {
                    etNrp.setError("NRP tidak boleh kosong");
                } else if (etNama.getText().toString().length() == 0) {
                    etNama.setError("Username diperlukan!");
                } else {
                    pDialog.show();
                    tambahPenyidik();
                }break;
            }break;

        }

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

//    private void dropdown(ArrayList<String> items){
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(adapter);
//        spin.setOnItemSelectedListener(this);
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        getPenyidik();
//    }
}