package com.example.lpppa.menuTambahLP;

import android.app.ProgressDialog;
import android.content.Intent;
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


public class TambahLPFragment extends Fragment {

    ArrayList<String> penyidik = new ArrayList<>();
    ArrayList<String> nrp = new ArrayList<>();
    ArrayList<String> tahun = new ArrayList<>();
    private Spinner spinJenis, spinPasal, spinPenyidik, spintahun;
    private EditText etNomor, etNamaPelapor, etAlamatPelapor, etNamaKorban, etAlamatkorban, etNamaTerlapor,
            etAlamatterlapor, etMO, etLokasi, etWaktu, etKerugian;
    private TextView tvNrp;
    private RadioButton rbLDPelapor, rbPDPelapor, rbLDKorban, rbPDKorban,
    rbLDTerlapor, rbPDTerlapor;
    private CheckBox cbKorbansama;
    private String rbpelapor, rbkorban, rbterlapor;
    private String[] jenis = {"LP", "SuratPengaduan","Rekom","LimpahPengaduan"};
    ProgressDialog pDialog;
    private Button btnSimpan;
    RadioGroup rgKorban;
    private String jenisBaru, pasalbaru, tahunbaru;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tambah_lp, container, false);
        spinJenis = view.findViewById(R.id.spin_jenis);
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
        tvNrp = view.findViewById(R.id.tv_spin_penyidik);
        cbKorbansama = view.findViewById(R.id.cb_tlpkorban);
        rbLDKorban = view.findViewById(R.id.rb_tlp_ldkorban);
        rbPDKorban = view.findViewById(R.id.rb_tlp_pdkorban);
        rbLDTerlapor = view.findViewById(R.id.rb_tlp_ldterlapor);
        rbPDTerlapor = view.findViewById(R.id.rb_tlp_pdterlapor);
        rbLDPelapor = view.findViewById(R.id.rb_tlp_ldpelapor);
        rbPDPelapor = view.findViewById(R.id.rb_tlp_pdpelapor);
        btnSimpan = view.findViewById(R.id.btn_simpantambahlp);
        rgKorban = view.findViewById(R.id.rg_korban);

        getPenyidik();
        cariTahun();

        ///////spin namapenyidik
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, penyidik);
        spinPenyidik.setAdapter(adapter);
        spinPenyidik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cariNrp(adapter.getItem(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ///////spin jenis
        final ArrayAdapter<String> adapterjenis = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, jenis);
        spinPenyidik.setAdapter(adapterjenis);
        spinPenyidik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenisBaru = adapterjenis.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ///////spin tahun
        final ArrayAdapter<String> adaptertahun = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, jenis);
        spinPenyidik.setAdapter(adaptertahun);
        spinPenyidik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tahunbaru = adaptertahun.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSimpan.setOnClickListener(view1 -> {cariNomor();});

        return view;
    }

    public void RadioButtonPelapor(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_tlp_ldpelapor:
                if (checked)
                    rbpelapor = "Laki-laki";
                break;
            case R.id.rb_tlp_pdpelapor:
                if (checked)
                    rbpelapor = "Perempuan";
                break;
        }
    }

    public void RadioButtonkorban(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_tlp_ldkorban:
                if (checked)
                    rbkorban = "Laki-laki";
                break;
            case R.id.rb_tlp_pdkorban:
                if (checked)
                    rbkorban = "Perempuan";
                break;
        }
    }

    public void RadioButtonterlapor(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_tlp_ldterlapor:
                if (checked)
                    rbterlapor = "Laki-laki";
                break;
            case R.id.rb_tlp_pdterlapor:
                if (checked)
                    rbterlapor = "Perempuan";
                break;
        }
    }

    public void checkBoxKorbansama(View view){
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.cb_tlpkorban) {
            if (checked) {
                etNamaKorban.setVisibility(View.GONE);
                etNamaKorban.setText(etNamaPelapor.getText().toString());
                rgKorban.setVisibility(View.GONE);
                rbkorban = rbterlapor;
                etAlamatkorban.setVisibility(View.GONE);
                etAlamatkorban.setText(etAlamatPelapor.getText().toString());
            }
        }
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
                                    etNamaPelapor.setError("Nama Korban tidak boleh kosong");
                                } else if (etNamaTerlapor.getText().toString().length() == 0) {
                                    etNamaPelapor.setError("Nama Terlapor tidak boleh kosong");
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String indexSheet = jsonObject.optString("sheetnames");
                        tahun.add(indexSheet);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrpx = jsonObject.optString("nrp");
                        String nama = jsonObject.optString("nama");

                        penyidik.add(nama);
                        nrp.add(nrpx);
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

    private void cariNrp(String nama){
        for (int i = 0; i < nrp.size(); i++) {
            String nrpy = nrp.get(i);
            if (nama.equals(nrpy)){
                tvNrp.setText(nrpy);
            }
        }

    }

    private void tambahLP(){
//        ApiService mApiService = RetrofitClient.updateRetroPenyidik();
//        mApiService.tambahPenyidik("insert",tahunbaru,etNrp.getText().toString(),etNama.getText().toString(),
//                "unitidik6",pangkatbaru,userJabatan,etNotelpon.getText().toString())
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        pDialog.hide();
//                        Toast.makeText(getContext(), "penyidik berhasil di tambah", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    }
//                });
    }

}