
package com.example.lpppa.menuSearch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Objects;

public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private TextView datatdkada;
    private EditText edNolp;
    private List<ItemList> itemLists;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private String tahunbaru;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ImageView ivCari = view.findViewById(R.id.iv_cari);
        edNolp = view.findViewById(R.id.ed_lpsearch);
        recyclerView = view.findViewById(R.id.rv_hasilsearch);
        refreshLayout = view.findViewById(R.id.swipe_pencarian);
        spinner = view.findViewById(R.id.spinner_tahuncari);
        datatdkada = view.findViewById(R.id.tv_datatdkada);
        datatdkada.setVisibility(View.GONE);

        cariTahun();

        ivCari.setOnClickListener(view1 -> {cariNomor();});
        itemLists = new ArrayList<>();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);

        return view;
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
    private void tahunxxx(ArrayList<String> tahunx){
        ///////spin tahun
        final ArrayAdapter<String> adaptertahun = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, tahunx);
        spinner.setAdapter(adaptertahun);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tahunbaru = adaptertahun.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void cariNomor(){
        ApiService mApiService = RetrofitClient.cariRetroNrpoPenyidik();
        mApiService.cariNomor("cariNomor",tahunbaru,edNolp.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {

                            JSONObject object = new JSONObject(response.body().string());
                            String hasil = object.optString("hasil");
                            if (hasil.equals("yes")){
                                datatdkada.setVisibility(View.GONE);
                                getDataLp();
                            }else {
                                recyclerView.setVisibility(View.GONE);
                                datatdkada.setVisibility(View.VISIBLE);
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

    private void getDataLp(){
        refreshLayout.setRefreshing(true);
        ApiService mApiService = RetrofitClient.getRetroData();
        mApiService.getData("read",tahunbaru).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray(tahunbaru);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String noLp = jsonObject.optString("Nomor");
                        String perkembangan = jsonObject.optString("Perkembangan");
                        String pelapor = jsonObject.optString("NamaP");
                        String penyidik = jsonObject.optString("Penyidik");

                        if (noLp.equals(edNolp.getText().toString())){
                            ItemList list = new ItemList();
                            list.setNoLp(noLp);
                            list.setNamapelapor(pelapor);
                            list.setPerkembangan(perkembangan);
                            list.setNamapenyidik(penyidik);

                            itemLists.add(list);
                            AdapterData adapterData = new AdapterData(getContext(), itemLists);
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
        getDataLp();
    }
}
