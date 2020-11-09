package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.adapter.AdapterData;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ItemList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DetailDataActivity extends AppCompatActivity {
    private TextView noLp, perkembangan, tanggal, uu, penyidik, namaPelapor, jenisKelPelapor,
    alamatPelapor, namaKorban, jenisKelKorban, alamatKorban, namaTerlapor, alamatTerlapor,
    jenisKelTerlapor;
    private String tahun, nolp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        inisiasi();
        Bundle bundle = getIntent().getExtras();
        tahun  = bundle.getString("tahun");
        nolp   = bundle.getString("nolp");
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

        getData();
    }

    private void getData(){
        ApiService mApiService = RetrofitClient.getRetroData();
        mApiService.getPenyidik("read",tahun).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray(tahun);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String noLpx = jsonObject.optString("Nomor");
                        String perkembanganx = jsonObject.optString("Perkembangan");
                        String pelaporx = jsonObject.optString("NamaP");
                        String penyidikx = jsonObject.optString("Penyidik");
                        String alamatPelaporx = jsonObject.optString("AlamatP");
                        String jenisKelPelaporx = jsonObject.optString("KelaminP");
                        String namaKorbanx = jsonObject.optString("NamaK");
                        String alamatKorbanx = jsonObject.optString("AlamatK");
                        String jenisKelKorbanx = jsonObject.optString("KelaminK");
                        String namaTerlaporx = jsonObject.optString("NamaT");
                        String alamatTerlaporx = jsonObject.optString("AlamatT");
                        String JenisKelaminTerlaporx = jsonObject.optString("KelaminT");
                        if (nolp.equals(noLpx)){
                            noLp.setText(noLpx);
                            perkembangan.setText(perkembanganx);
                            namaPelapor.setText(pelaporx);
                            penyidik.setText(penyidikx);
                            alamatPelapor.setText(alamatPelaporx);
                            jenisKelPelapor.setText(jenisKelPelaporx);
                            namaKorban.setText(namaKorbanx);
                            alamatKorban.setText(alamatKorbanx);
                            jenisKelKorban.setText(jenisKelKorbanx);
                            namaTerlapor.setText(namaTerlaporx);
                            jenisKelTerlapor.setText(JenisKelaminTerlaporx);
                            alamatTerlapor.setText(alamatTerlaporx);
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
}