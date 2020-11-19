package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    jenisKelTerlapor, tvLokasi, tvWaktu, tvMO, tvKerugian, tvBox;
    private String tahun, nolp, perkembanganx;
    private Button btnEdit;
    private Toolbar toolbar;
    ImageButton arrow, arowKorban, arowTerlapor;
    LinearLayout hiddenView, hiddenKorban, hiddenTerlapor;
    CardView cardView, cvKorban, cvTerlapor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        toolbar = findViewById(R.id.toolbar_detaildata);

        Bundle bundle = getIntent().getExtras();
        tahun  = bundle.getString("tahun");
        nolp   = bundle.getString("nolp");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        inisiasi();

        arrow.setOnClickListener(view -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_spinner);
            }
            else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_spinner_up);
            }
        });

        arowKorban.setOnClickListener(view -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cvKorban, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_spinner);
            }
            else {
                TransitionManager.beginDelayedTransition(cvKorban, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_spinner_up);
            }
        });

        arowTerlapor.setOnClickListener(view -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cvTerlapor, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_spinner);
            }
            else {
                TransitionManager.beginDelayedTransition(cvTerlapor, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_spinner_up);
            }
        });
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void inisiasi(){
        noLp = findViewById(R.id.tv_ddlp);
        perkembangan = findViewById(R.id.tv_ddperkembangan);
        tanggal = findViewById(R.id.tv_ddtanggal);
        uu = findViewById(R.id.tv_ddpasal);
        penyidik = findViewById(R.id.tv_ddpenyidik);
        namaPelapor = findViewById(R.id.tv_ddnamapelapor);
        jenisKelPelapor = findViewById(R.id.tv_ddjeniskelpelapor);
        alamatPelapor = findViewById(R.id.tv_ddalamatpelapor);
        namaKorban = findViewById(R.id.tv_ddnamakorban);
        alamatKorban = findViewById(R.id.tv_ddalamatkorban);
        jenisKelKorban = findViewById(R.id.tv_ddjeniskelkorban);
        namaTerlapor = findViewById(R.id.tv_ddnamaterlapor);
        alamatTerlapor = findViewById(R.id.tv_ddalamatterlapor);
        jenisKelTerlapor = findViewById(R.id.tv_ddjeniskelterlapor);
        btnEdit = findViewById(R.id.btn_editLP);
        cardView = findViewById(R.id.base_cardview);
        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);
        cvKorban = findViewById(R.id.base_cardviewkorban);
        cvTerlapor = findViewById(R.id.base_cardviewterlapor);
        arowKorban = findViewById(R.id.arrow_buttonkorban);
        arowTerlapor = findViewById(R.id.arrow_buttonterlapor);
        hiddenKorban = findViewById(R.id.hidden_viewkorban);
        hiddenTerlapor = findViewById(R.id.hidden_viewterlapor);
        tvBox = findViewById(R.id.tv_ddbox);
        tvKerugian = findViewById(R.id.tv_ddkerugian);
        tvLokasi = findViewById(R.id.tv_ddlokasi);
        tvWaktu = findViewById(R.id.tv_ddwaktu);
        tvMO = findViewById(R.id.tv_ddMo);

        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditPerkembanganActivity.class);
            intent.putExtra("nolp", nolp);
            intent.putExtra("perkembangan", perkembanganx);
            intent.putExtra("tahun", tahun);
            startActivity(intent);
        });

        getData();
    }

    private void getData(){
        ApiService mApiService = RetrofitClient.getRetroData();
        mApiService.getData("read",tahun).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray(tahun);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String noLpx = jsonObject.optString("Nomor");
                        perkembanganx = jsonObject.optString("Perkembangan");
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
                        String Mox = jsonObject.optString("mo");
                        String kerugianX = jsonObject.optString("kerugian");
                        String waktuX = jsonObject.optString("waktuKejadian");
                        String lokasiX = jsonObject.optString("lokasi");
                        String boxX = jsonObject.optString("Box");

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
                            tvMO.setText(Mox);
                            tvKerugian.setText(kerugianX);
                            tvLokasi.setText(lokasiX);
                            tvWaktu.setText(waktuX);
                            tvBox.setText(boxX);
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