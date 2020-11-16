package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.lpppa.adapter.AdapterListTahun;
import com.example.lpppa.adapter.AdapterPenyidik;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ListTahun;
import com.example.lpppa.models.Penyidik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTahunActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    private List<ListTahun> tahunList;
    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private String jenisLaporan, rekom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tahun);
        recyclerView = findViewById(R.id.rv_item_tahun);
        toolbar = findViewById(R.id.toolbar_listtahun);
        refreshLayout = findViewById(R.id.swipe_listtahun);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Tahun");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        jenisLaporan     = bundle.getString("jenis");
        rekom   = bundle.getString("rekom");

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListTahunActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        tahunList = new ArrayList<>();
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

    private void getData() {
        refreshLayout.setRefreshing(true);
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

                        ListTahun listTahun = new ListTahun();
                        listTahun.setTahun(indexSheet);
                        listTahun.setJenisLaporan(jenisLaporan);
                        listTahun.setRekom(rekom);

                        tahunList.add(listTahun);
                        AdapterListTahun listTahun1 = new AdapterListTahun(ListTahunActivity.this, tahunList);
                        recyclerView.setAdapter(listTahun1);

                    }
                    refreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
        tahunList.clear();
        getData();
    }
}