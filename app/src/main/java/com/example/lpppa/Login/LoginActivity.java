package com.example.lpppa.Login;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lpppa.MainActivity;
import com.example.lpppa.R;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etNrp;
    private TextInputEditText etPassword;
    private Button btnMasuk;
    Boolean session = false;
    SharedPreferences sharedPreferences;
    private String nrpx;
    public static final String session_status = "session_status";
    public static final String my_shared_preferences = "signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNrp = findViewById(R.id.etNrp);
        etPassword = findViewById(R.id.etPassword);
        btnMasuk   = findViewById(R.id.btn_masuk);

        initComponent();
        if (session){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.putExtra(id,"id");
//            intent.putExtra(email,"email");
//            intent.putExtra(name, "name");
//            finish();
            startActivity(intent);
            finish();
        }
    }

    public void initComponent(){
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        nrpx = sharedPreferences.getString("nrpx", null);
        btnMasuk.setOnClickListener(view -> Login());
    }
    private void Login(){
        ApiService apiService  = RetrofitClient.getRetroPenyidik();
        apiService.getPenyidik("read","penyidik").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("penyidik");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nrp = jsonObject.optString("nrp");
                        String password   = jsonObject.optString("password");
                        Log.d("LoginActivity","coba="+nrp);

                        if (nrp.equals(etNrp.getText().toString()) && password.equals(etPassword.getText().toString())){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString("nrp", nrp);
                            editor.commit();

                            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.putExtra(nrp,"nrp");
                            startActivity(intent1);
                            finish();

                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "NRP atau password salah", Toast.LENGTH_SHORT).show();
                    finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}