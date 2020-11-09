
package com.example.lpppa.menuSearch;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.lpppa.R;
import com.example.lpppa.activity.DataActivity;
import com.example.lpppa.activity.ListTahunActivity;
import com.example.lpppa.adapter.AdapterData;
import com.example.lpppa.adapter.AdapterListTahun;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ItemList;
import com.example.lpppa.models.ListTahun;
import com.squareup.picasso.Picasso;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ImageView ivCari;
    private TextView tvTahun;
    private String item;
    private EditText edNolp;
    private List<ItemList> itemLists;
    private RecyclerView recyclerView;

    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ivCari = view.findViewById(R.id.iv_cari);
        edNolp = view.findViewById(R.id.ed_lpsearch);
        recyclerView = view.findViewById(R.id.rv_hasilsearch);
        spinner = view.findViewById(R.id.spinner_tahun);

//        categories.add("Automobile");
//        categories.add("Business Services");
//        categories.add("Computers");
//        categories.add("Education");
//        categories.add("Personal");
//        categories.add("Travel");
        // Spinner click listener
        getTahun();

        spinner.setOnItemSelectedListener(this);


        ivCari.setOnClickListener(view1 -> {getDataLp();});
        itemLists = new ArrayList<>();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        tvTahun.setText(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getTahun(){
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

                        List<String> categories = new ArrayList<>();
                        categories.add(indexSheet);

                        // Creating adapter for spinner
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                android.R.layout.simple_spinner_item, categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);

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
        ApiService mApiService = RetrofitClient.getRetroData();
        mApiService.getPenyidik("read",item).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray(item);

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
}
