package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.lpppa.R;
import com.example.lpppa.adapter.AdapterData;
import com.example.lpppa.adapter.AdapterListTahun;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ItemList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private String tahun;
    private String jenisLaporan;
    private List<ItemList> itemLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        toolbar = findViewById(R.id.toolbar_data);
        refreshLayout = findViewById(R.id.swipe_data);
        recyclerView = findViewById(R.id.rv_data);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data LP");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        tahun  = bundle.getString("tahun");
        jenisLaporan     = bundle.getString("jenis");

        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        itemLists = new ArrayList<>();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(() -> {
                    refreshLayout.setRefreshing(true);
                    getData();
                }
        );

    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        refreshLayout.setRefreshing(true);
        ApiService mApiService = RetrofitClient.getRetroData();
        mApiService.getPenyidik("read",tahun).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray(tahun);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String noLp = jsonObject.optString("Nomor");
                        String perkembangan = jsonObject.optString("Perkembangan");
                        String pelapor = jsonObject.optString("NamaP");
                        String penyidik = jsonObject.optString("Penyidik");
                        String kategori = jsonObject.optString("Kategori");

                        if (jenisLaporan.equals(kategori)){
                            ItemList list = new ItemList();
                            list.setNoLp(noLp);
                            list.setNamapelapor(pelapor);
                            list.setPerkembangan(perkembangan);
                            list.setNamapenyidik(penyidik);

                            itemLists.add(list);
                            AdapterData adapterData = new AdapterData(DataActivity.this, itemLists);
                            recyclerView.setAdapter(adapterData);
                        }

                    }
                    refreshLayout.setRefreshing(false);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        itemLists.clear();
        getData();
    }

}