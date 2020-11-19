package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpppa.R;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class EditPerkembanganActivity extends AppCompatActivity {
    private String nolp;
    private String perkembangan;
    private String tahun;
    private Toolbar toolbar;
    private TextView tvnolp;
    private Button btnsimpan;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perkembangan);
        toolbar = findViewById(R.id.toolbar_editperkembangan);
        btnsimpan = findViewById(R.id.btn_editperkembangan);
        tvnolp = findViewById(R.id.tv_editlpperkembangan);
        editText = findViewById(R.id.et_perkembangan);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit LP");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        nolp   = bundle.getString("nolp");
        perkembangan = bundle.getString("perkembangan");
        tahun = bundle.getString("tahun");
        tvnolp.setText(nolp);
        editText.setText(perkembangan);

        Toast.makeText(this, "teesss"+tahun+nolp +perkembangan, Toast.LENGTH_SHORT).show();

        btnsimpan.setOnClickListener(view -> {updateData();});
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void updateData(){
        ApiService mApiService = RetrofitClient.updateRetroPerkembangan();
        mApiService.updatePerkembangan("update",tahun,nolp,editText.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(EditPerkembanganActivity.this, "Perkembangan berhasil di ubah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

}