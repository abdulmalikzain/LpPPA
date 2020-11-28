package com.example.lpppa.menuTambahLP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpppa.MainActivity;
import com.example.lpppa.R;
import com.example.lpppa.activity.ListTahunActivity;
import com.example.lpppa.activity.PenyidikActivity;
import com.example.lpppa.activity.TambahPenyidikActivity;
import com.example.lpppa.adapter.AdapterListTahun;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ListTahun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.lpppa.Login.LoginActivity.my_shared_preferences;


public class TambahLPFragment extends Fragment  {

    private Spinner spinPasal;
    private Spinner spinPenyidik;
    private Spinner spintahun;
    private EditText etNrp, etNomor, etNamaPelapor, etAlamatPelapor, etNamaKorban, etAlamatkorban, etNamaTerlapor,
            etAlamatterlapor, etMO, etLokasi, etWaktu, etKerugian;
    private String rbpelapor, rbkorban, rbterlapor;
    private String[] jenis = {"LP", "SuratPengaduan","Rekom","LimpahPengaduan"};
    ProgressDialog pDialog;
    RadioGroup rgKorban, rgPelapor, rgTerlapor;
    private String jenisBaru, pasalbaru, pnydbaru, tahunbaru, nrp;
    private RelativeLayout rl;
    private TextView tvPeringatan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tambah_lp, container, false);
        Spinner spinJenis = view.findViewById(R.id.spin_jenis);
        spinPasal = view.findViewById(R.id.spin_uud);
        spintahun = view.findViewById(R.id.spin_tahun);
        spinPenyidik = view.findViewById(R.id.spin_penyidik);
        etNamaKorban = view.findViewById(R.id.et_tlp_namakorban);
        etAlamatkorban = view.findViewById(R.id.et_tlp_alamatkorban);
        etNamaPelapor = view.findViewById(R.id.et_tlp_namapelapor);
        etNamaTerlapor = view.findViewById(R.id.et_tlp_namaterlapor);
        etAlamatterlapor = view.findViewById(R.id.et_tlp_alamatterlapor);
        etNomor = view.findViewById(R.id.et_tlp_nomor);
        etAlamatPelapor = view.findViewById(R.id.et_tlp_alamatpelapor);
        etMO = view.findViewById(R.id.et_tlp_mo);
        etLokasi = view.findViewById(R.id.et_tlp_lokasi);
        etWaktu = view.findViewById(R.id.et_tlp_waktu);
        etKerugian = view.findViewById(R.id.et_tlp_kerugian);
        etNrp = view.findViewById(R.id.et_tlp_nrppenyidik);
        CheckBox cbKorbansama = view.findViewById(R.id.cb_tlpkorban);
        Button btnSimpan = view.findViewById(R.id.btn_simpantambahlp);
        rgKorban = view.findViewById(R.id.rg_korban);
        rgPelapor = view.findViewById(R.id.rg_pelapor);
        rgTerlapor = view.findViewById(R.id.rg_terlapor);
        rl = view.findViewById(R.id.rl_tambahlp);
        tvPeringatan = view.findViewById(R.id.tv_tmbhlpbukanbamin);


        SharedPreferences sharedpreferences = Objects.requireNonNull(getContext())
                .getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nrp = (sharedpreferences.getString("nrp", ""));

        getPenyidik();
        cariTahun();
        listPasal();

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        pDialog.setMessage("Menambahkan Laporan Polisi");

        rgPelapor.setOnCheckedChangeListener((radioGroup, checked) -> {
            switch(checked) {
                case R.id.rb_tlp_ldpelapor:
                        rbpelapor = "Laki-laki";
                    break;
                case R.id.rb_tlp_pdpelapor:
                        rbpelapor = "Perempuan";
                    break;
            }
        });
        rgKorban.setOnCheckedChangeListener((radioGroup, checked) -> {
            switch(checked) {
                case R.id.rb_tlp_ldkorban:
                        rbkorban = "Laki-laki";
                    break;
                case R.id.rb_tlp_pdkorban:
                        rbkorban = "Perempuan";
                    break;
            }
        });
        rgTerlapor.setOnCheckedChangeListener((radioGroup, checked) -> {
            switch(checked) {
                case R.id.rb_tlp_ldterlapor:
                        rbterlapor = "Laki-laki";
                    break;
                case R.id.rb_tlp_pdterlapor:
                        rbterlapor = "Perempuan";
                    break;
            }
        });

        cbKorbansama.setOnCheckedChangeListener((chekorbansama, checked) -> {
                if (checked) {
                    etNamaKorban.setVisibility(View.GONE);
                    etNamaKorban.setText(etNamaPelapor.getText().toString());
                    rgKorban.setVisibility(View.GONE);
                    rbkorban = rbterlapor;
                    etAlamatkorban.setVisibility(View.GONE);
                    etAlamatkorban.setText(etAlamatPelapor.getText().toString());
                }else {
                    etNamaKorban.setVisibility(View.VISIBLE);
                    rgKorban.setVisibility(View.VISIBLE);
                    etAlamatkorban.setVisibility(View.VISIBLE);
                }

        });

        ///////spin jenis
        final ArrayAdapter<String> adapterjenis = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, jenis);
        spinJenis.setAdapter(adapterjenis);
        spinJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenisBaru = adapterjenis.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSimpan.setOnClickListener(view1 -> {cariNomor();});

        return view;
    }

    private void cariNomor(){
        ApiService mApiService = RetrofitClient.cariRetroNrpoPenyidik();
        mApiService.cariNomor("cariNomor",tahunbaru,etNomor.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {

                            JSONObject object = new JSONObject(response.body().string());
                            String hasil = object.optString("hasil");
                            if (hasil.equals("yes")){
                                etNomor.setError("No LP sudah ada");
                            }else {
                                if (etNomor.getText().toString().length() == 0) {
                                    etNomor.setError("No tidak boleh kosong");
                                } else if (etNamaPelapor.getText().toString().length() == 0) {
                                    etNamaPelapor.setError("Nama Pelapor tidak boleh kosong");
                                } else if (etNamaKorban.getText().toString().length() == 0) {
                                    etNamaKorban.setError("Nama Korban tidak boleh kosong");
                                } else if (etNamaTerlapor.getText().toString().length() == 0) {
                                    etNamaTerlapor.setError("Nama Terlapor tidak boleh kosong");
                                } else {
                                    pDialog.show();
                                    tambahLP();
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

    private void cariTahun(){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","indexsheet").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("indexsheet");

                    ArrayList<String> tahun = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String indexSheet = jsonObject.optString("sheetnames");
                        tahun.add(indexSheet);
                    }
                    tahunxxx(tahun);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void tahunxxx(ArrayList<String> strings){
        final ArrayAdapter<String> adaptertahun = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, strings);
        spintahun.setAdapter(adaptertahun);
        spintahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tahunbaru = adaptertahun.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void listPasal(){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","listuud").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("listuud");
                    ArrayList<String> pasal = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String indexSheet = jsonObject.optString("listpasal");
                        pasal.add(indexSheet);
                    }
                    pasalxxx(pasal);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    private void pasalxxx(ArrayList<String> pasalx){
        final ArrayAdapter<String> adapterpasal = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, pasalx);
        spinPasal.setAdapter(adapterpasal);
        spinPasal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pasalbaru = adapterpasal.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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
                    ArrayList<String> penyidik = new ArrayList<>();
                    ArrayList<String> nrp = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrpx = jsonObject.optString("nrp");
                        String nama = jsonObject.optString("nama");
                        String jabatan = jsonObject.optString("jabatan");

                        penyidik.add(nama);
                        nrp.add(nrpx);

                        if (nrp.equals(nrpx)){
                            if (!jabatan.equals("Bamin")){
                                rl.setVisibility(View.GONE);
                                tvPeringatan.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    namapenyidik(penyidik,nrp);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    private void namapenyidik(ArrayList<String> pny, ArrayList<String> nrp){
        ///////spin namapenyidik
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, pny);
        spinPenyidik.setAdapter(adapter);
        spinPenyidik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pnydbaru = adapter.getItem(i);
                getNRPPenyidik(pnydbaru);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getNRPPenyidik(String namapny){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","penyidik").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("penyidik");
                    ArrayList<String> penyidik = new ArrayList<>();
                    ArrayList<String> nrp = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrpx = jsonObject.optString("nrp");
                        String nama = jsonObject.optString("nama");

                        if (nama.equals(namapny)){
                            etNrp.setText(nrpx);
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

    private void tambahLP(){

        ApiService mApiService = RetrofitClient.getRetroData();
        mApiService.tambahLP("insert",tahunbaru,jenisBaru,etNomor.getText().toString(),
                pasalbaru,etNamaPelapor.getText().toString(),rbpelapor,etAlamatPelapor.getText().toString(),
                etNamaKorban.getText().toString(),rbkorban,etAlamatkorban.getText().toString(),
                etNamaTerlapor.getText().toString(),rbterlapor,etAlamatterlapor.getText().toString(),
                pnydbaru,etNrp.getText().toString(), etMO.getText().toString(),
                etKerugian.getText().toString(),etWaktu.getText().toString(),etLokasi.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pDialog.hide();
                        Toast.makeText(getContext(), "Laporan Polisi berhasil di tambah", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

}