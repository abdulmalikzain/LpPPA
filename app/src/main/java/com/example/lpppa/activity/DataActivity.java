package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.lpppa.R;

public class DataActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private String tahun;
    private String jenisLaporan;

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

        Toast.makeText(this, "Tahun"+tahun, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "jenislaporannnn"+jenisLaporan, Toast.LENGTH_SHORT).show();
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getData(){

    }
}