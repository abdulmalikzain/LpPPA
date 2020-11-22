package com.example.lpppa.menuProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lpppa.Login.LoginActivity;
import com.example.lpppa.R;
import com.example.lpppa.activity.EditPenyidikActivity;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.lpppa.Login.LoginActivity.my_shared_preferences;

public class ProfileFragment extends Fragment {

    private TextView tvNama;
    private TextView tvPangkat;
    private TextView tvJabatan;
    private String nrp;
    private CircleImageView civFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvKeluar = view1.findViewById(R.id.tv_keluar);
        tvNama = view1.findViewById(R.id.tv_namaprofil);
        tvJabatan = view1.findViewById(R.id.tv_jabatanprofil);
        tvPangkat = view1.findViewById(R.id.tv_pangkatprofil);
        civFoto = view1.findViewById(R.id.civ_fotoprofil);
        civFoto = view1.findViewById(R.id.civ_fotoprofil);
        TextView tentang = view1.findViewById(R.id.tv_tentangprofil);
        TextView tveditProfile = view1.findViewById(R.id.tv_editprofile);

        SharedPreferences sharedpreferences = Objects.requireNonNull(getContext()).getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nrp = (sharedpreferences.getString("nrp", ""));

        getDataPenyidik();

        civFoto.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditPhotoActivity.class);
            intent.putExtra("nrp", nrp);
            intent.putExtra("nama", tvNama.getText().toString().trim());
            startActivity(intent);
        });

        tveditProfile.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditPenyidikActivity.class);
            intent.putExtra("nrp", nrp);
            startActivity(intent);
        });

        tvKeluar.setOnClickListener(view -> {DialogForm();});
        tentang.setOnClickListener(view -> {DialogTentang();});
        return view1;
    }

    private void keluar() {
                SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString("nrp", null);
                editor.clear();
                editor.apply();

                Intent intent1 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent1);
    }

    private void getDataPenyidik(){
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
                        String nrpx = jsonObject.optString("nrp");
                        String nama   = jsonObject.optString("nama");
                        String pangkat = jsonObject.optString("pangkat");
                        String jabatan = jsonObject.optString("jabatan");
                        String foto = jsonObject.optString("image");
                        String urlImagedefault = "https://drive.google.com/uc?export=view&id=1x2a7NJnvUZUFdXOeLb_jP0UM0GbdahIF";
                        if (nrp.equals(nrpx)){
                            tvNama.setText(nama);
                            tvJabatan.setText(jabatan);
                            tvPangkat.setText(pangkat);
                            if (foto.equals(urlImagedefault)){
                                Picasso.get().load(urlImagedefault).error(R.drawable.user_police).into(civFoto);
                            }else {
                                Picasso.get().load(foto).error(R.drawable.user_police).into(civFoto);
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

    private void DialogForm() {
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.user_police);
        dialog.setTitle("Keluar dari Akun?");


        dialog.setPositiveButton("Keluar", (dialog1, which) -> {
            keluar();
            dialog1.dismiss();
        });

        dialog.setNegativeButton("Batal", (dialog12, which) -> dialog12.dismiss());

        dialog.show();
    }

    private void DialogTentang() {
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_tentang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tentang Aplikasi");

        dialog.setNegativeButton("Kembali", (dialog12, which) -> dialog12.dismiss());

        dialog.show();
    }
}
