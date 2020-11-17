package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lpppa.R;

import java.util.Objects;

import static com.example.lpppa.Login.LoginActivity.my_shared_preferences;

public class EditPenyidikActivity extends AppCompatActivity {

    private String nrp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_penyidik);

        SharedPreferences sharedpreferences = this.getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nrp = (sharedpreferences.getString("nrp", ""));
    }
}