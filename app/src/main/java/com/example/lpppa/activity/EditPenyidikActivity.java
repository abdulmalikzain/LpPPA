package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lpppa.R;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import static com.example.lpppa.Login.LoginActivity.my_shared_preferences;

public class EditPenyidikActivity extends AppCompatActivity {

    private String nrpx;
    private EditText etNama, etJabatan, etPangkat, etNotelpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_penyidik);
        etNama = findViewById(R.id.et_editnama);
        etJabatan = findViewById(R.id.et_editjabatan);
        etNotelpon = findViewById(R.id.et_editnotelpon);
        etPangkat = findViewById(R.id.et_editpangkat);
        Button cvsimpan = findViewById(R.id.btn_simpaneditpny);
        Toolbar toolbar= findViewById(R.id.toolbar_editpenyidik);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Penyidik");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences sharedpreferences = this.getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nrpx = (sharedpreferences.getString("nrp", ""));

        getData();

        cvsimpan.setOnClickListener(view -> {editPenyidik();});
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
                    assert response.body() != null;
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("penyidik");

                    assert jsonArray != null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrp = jsonObject.optString("nrp");
                        String nama   = jsonObject.optString("nama");
                        String pangkat = jsonObject.optString("pangkat");
                        String jabatan = jsonObject.optString("jabatan");
                        String notelpon = jsonObject.optString("notelpon");

                        if (nrp.equals(nrpx)){
                            etNama.setText(nama);
                            etJabatan.setText(jabatan);
                            etNotelpon.setText(notelpon);
                            etPangkat.setText(pangkat);
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

    private void editPenyidik(){
        ApiService mApiService = RetrofitClient.updateRetroPenyidik();
        mApiService.updatePenyidik("update","penyidik",nrpx,etNama.getText().toString(),
                etPangkat.getText().toString(), etJabatan.getText().toString(), etNotelpon.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(EditPenyidikActivity.this, "penyidik berhasil di edit", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditPenyidikActivity.this, DetailPenyidikActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }
}