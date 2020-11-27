package com.example.lpppa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lpppa.menuHome.HomeFragment;
import com.example.lpppa.menuProfile.ProfileFragment;
import com.example.lpppa.menuSearch.SearchFragment;
import com.example.lpppa.menuTambahLP.TambahLPFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.lpppa.Login.LoginActivity.my_shared_preferences;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navigationView;
    private String nrp;
    private FragmentContainer fragmentContainer;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bnv);
//        navigationView.setOnNavigationItemSelectedListener;
        // kita set default nya Home Fragment
        loadFragment(new HomeFragment());
        // beri listener pada saat item/menu bottomnavigation terpilih
        navigationView.setOnNavigationItemSelectedListener(this);
        SharedPreferences sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nrp = (sharedpreferences.getString("nrp",""));

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan beck lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.item_home:
                fragment = new HomeFragment();
                break;
            case R.id.item_search:
                fragment = new SearchFragment();
                break;
            case R.id.item_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.item_tambahlp:
                fragment = new TambahLPFragment();
                break;
        }
        return loadFragment(fragment);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
