package com.example.lpppa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.lpppa.MainActivity;
import com.example.lpppa.R;
import com.example.lpppa.adapter.AdapterPenyidik;
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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenyidikActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Toolbar mActionToolbar;
    private List<Penyidik> penyidiks;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyidik);
        mActionToolbar = findViewById(R.id.toolbar_penyidik);
        swipeRefreshLayout = findViewById(R.id.swipe_penyidik);
        layout = findViewById(R.id.ll_tambahpenyidik);

        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Penyidik");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_penyidik);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        penyidiks = new ArrayList<>();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(true);
            getData();
            }
        );

        layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, TambahPenyidikActivity.class);
            startActivity(intent);
        });
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","penyidik").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("penyidik");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrp = jsonObject.optString("nrp");
                        String nama   = jsonObject.optString("nama");
                        String pangkat = jsonObject.optString("pangkat");
                        String foto = jsonObject.optString("image");

                        Penyidik penyidik = new Penyidik();
                        penyidik.setNama(nama);
                        penyidik.setNrp(nrp);
                        penyidik.setPangkat(pangkat);
                        penyidik.setFoto(foto);

                        penyidiks.add(penyidik);
                        AdapterPenyidik penyidik1 = new AdapterPenyidik(PenyidikActivity.this, penyidiks);
                        recyclerView.setAdapter(penyidik1);


                        if (recyclerView.getAdapter() != null) {
                            count = recyclerView.getAdapter().getItemCount();
                        }

                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        penyidiks.clear();
        getData();
//        Toast.makeText(this, "total:"+count, Toast.LENGTH_SHORT).show();
    }
}