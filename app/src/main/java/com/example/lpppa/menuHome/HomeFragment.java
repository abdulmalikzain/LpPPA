package com.example.lpppa.menuHome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.lpppa.R;
import com.example.lpppa.activity.KdrtActivity;
import com.example.lpppa.activity.ListTahunActivity;
import com.example.lpppa.activity.PenyidikActivity;
import com.example.lpppa.activity.PidanaAnakActivity;
import com.example.lpppa.activity.UUActivity;
import com.example.lpppa.adapter.AdapterHomePenyidik;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.Penyidik;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.lpppa.Login.LoginActivity.my_shared_preferences;

public class HomeFragment extends Fragment {
    private LinearLayout llpenyidik, llu, llLp, llAduan, llLimpah, llanak, llkdrt;
    private RecyclerView Rvpenyidik;
    private AVLoadingIndicatorView loadingIndicatorView;
    private TextView tvLihatsemua;
    private TextView tvnamaPenyidik;
    private List<Penyidik> penyidiks;
    private String LP = "LP";
    private String Aduan = "SuratAduan";
    private String Limpah = "Limpah";
    private String nrp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view1 = inflater.inflate(R.layout.fragment_home, container, false);

        llpenyidik = view1.findViewById(R.id.ll_homepenyidik);
        llu = view1.findViewById(R.id.ll_homeuu);
        loadingIndicatorView = view1.findViewById(R.id.av_homepenyidik);
        Rvpenyidik = view1.findViewById(R.id.rv_homepenyidik);
        tvLihatsemua = view1.findViewById(R.id.tv_homelihatsemua);
        tvnamaPenyidik = view1.findViewById(R.id.tv_homepenyidik);
        TextView tvNrp = view1.findViewById(R.id.tvhomenrp);
        Rvpenyidik = view1.findViewById(R.id.rv_homepenyidik);
        llLp = view1.findViewById(R.id.ll_homelp);
        llAduan = view1.findViewById(R.id.ll_homeaduan);
        llLimpah = view1.findViewById(R.id.ll_homelimpah);
        llanak = view1.findViewById(R.id.ll_homeanak);
        llkdrt = view1.findViewById(R.id.ll_homekdrt);

        SharedPreferences sharedpreferences = Objects.requireNonNull(getContext()).getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nrp = (sharedpreferences.getString("nrp", ""));
        tvNrp.setText(nrp);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        Rvpenyidik.setLayoutManager(layoutManager);

        penyidiks = new ArrayList<>();

        eventClick();
        return view1;
    }

    private void eventClick(){

        llpenyidik.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), PenyidikActivity.class);
            startActivity(intent);
        });
        tvLihatsemua.setOnClickListener(view -> {Intent intent = new Intent(getContext(), PenyidikActivity.class);
            startActivity(intent);});
        llu.setOnClickListener(view -> {Intent intent = new Intent(getContext(), UUActivity.class);
            startActivity(intent);});
        llLp.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ListTahunActivity.class);
            intent.putExtra("jenis", LP);
            startActivity(intent);});
        llLimpah.setOnClickListener(view -> {Intent intent = new Intent(getContext(), ListTahunActivity.class);
            intent.putExtra("jenis", Limpah); startActivity(intent);});
        llAduan.setOnClickListener(view -> {Intent intent = new Intent(getContext(), ListTahunActivity.class);
            intent.putExtra("jenis", Aduan); startActivity(intent);});
        llkdrt.setOnClickListener(view -> {Intent intent = new Intent(getContext(), KdrtActivity.class);
            startActivity(intent);});
        llanak.setOnClickListener(view -> {Intent intent = new Intent(getContext(), PidanaAnakActivity.class);
            startActivity(intent);});
        getPenyidik();
    }

    private void getPenyidik(){
        loadingIndicatorView.show();
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","penyidik").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("penyidik");

                    assert jsonArray != null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrpx = jsonObject.optString("nrp");
                        String nama   = jsonObject.optString("nama");
                        String pangkat = jsonObject.optString("pangkat");
                        String foto = jsonObject.optString("foto");
                        loadingIndicatorView.hide();

                        if (nrp.equals(nrpx)){
                            tvnamaPenyidik.setText(nama);
                        }

                        Penyidik penyidik = new Penyidik();
                        penyidik.setNama(nama);
                        penyidik.setPangkat(pangkat);
                        penyidik.setFoto(foto);

                        penyidiks.add(penyidik);
                        AdapterHomePenyidik penyidik1 = new AdapterHomePenyidik(getContext(), penyidiks);
                        Rvpenyidik.setAdapter(penyidik1);

//                        if (recyclerView.getAdapter() != null) {
//                            count = recyclerView.getAdapter().getItemCount();
//                        }

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

}

