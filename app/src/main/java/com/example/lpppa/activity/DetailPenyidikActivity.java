package com.example.lpppa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPenyidikActivity extends AppCompatActivity {

    private String nrp;
    private Toolbar toolbar;
    private TextView tvNama, tvNrp, tvJabatan, tvPangkat, tvNotelp;
    private Button btnEdit;
    private CircleImageView civPenyidik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyidik);
        tvNrp = findViewById(R.id.tv_detailnrp);
        tvJabatan = findViewById(R.id.tv_detailjabatan);
        tvPangkat = findViewById(R.id.tv_detailpangkat);
        tvNotelp = findViewById(R.id.tv_detailnotelp);
        tvNama = findViewById(R.id.tv_detailnamapenyidik);
        civPenyidik = findViewById(R.id.civ_detailpenyidik);
        toolbar = findViewById(R.id.toolbar_detailpenyidik);
        btnEdit = findViewById(R.id.btn_editPenyidik);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        nrp  = bundle.getString("nrp");

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Penyidik");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getData();

        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditPenyidikActivity.class);
            startActivity(intent);
        });
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
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
                        String nama   = jsonObject.optString("nama");
                        String pangkat = jsonObject.optString("pangkat");
                        String jabatan = jsonObject.optString("jabatan");
                        String noTelp = jsonObject.optString("noTelpon");
                        String foto = jsonObject.optString("image");
                        String urlImagedefault = "https://drive.google.com/uc?export=view&id=1x2a7NJnvUZUFdXOeLb_jP0UM0GbdahIF";
                        if (nrp.equals(nrpx)){
                            tvNrp.setText(nrpx);
                            tvPangkat.setText(pangkat);
                            tvJabatan.setText(jabatan);
                            tvNotelp.setText(noTelp);
                            tvNama.setText(nama);
                            if (foto.equals(urlImagedefault)){
                                Picasso.get().load(urlImagedefault).error(R.drawable.user_police).into(civPenyidik);
                            }else {
                                Picasso.get().load(foto).error(R.drawable.user_police).into(civPenyidik);
                            }
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

//    private void DialogForm() {
//        AlertDialog.Builder dialog;
//        dialog = new AlertDialog.Builder(DetailPenyidikActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.form_data, null);
//        dialog.setView(dialogView);
//        dialog.setCancelable(true);
//        dialog.setIcon(R.mipmap.ic_launcher);
//        dialog.setTitle("Form Biodata");
//
//        txt_nama    = dialogView.findViewById(R.id.txt_nama);
//
//        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                nama    = txt_nama.getText().toString();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}