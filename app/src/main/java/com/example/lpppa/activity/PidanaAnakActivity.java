package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.lpppa.R;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PidanaAnakActivity extends AppCompatActivity {
    private LineChart kekerasanChart;
    private LineChart persetubuhanChart;
    private LineChart cabulChart;
    private Toolbar toolbar;
    private AVLoadingIndicatorView aviKekerasan;
    private AVLoadingIndicatorView aviPersetubuhan;
    private AVLoadingIndicatorView aviCabul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pidana_anak);
        toolbar = findViewById(R.id.toolbar_kekerasananak);
        kekerasanChart = findViewById(R.id.linechart_kekerasananak);
        persetubuhanChart = findViewById(R.id.linechart_persetubuhananak);
        cabulChart = findViewById(R.id.linechart_cabulanak);
        aviKekerasan = findViewById(R.id.av_kekerasananak);
        aviPersetubuhan = findViewById(R.id.av_persetubuhananak);
        aviCabul = findViewById(R.id.av_cabulanak);

        kekerasanChart.setVisibility(View.INVISIBLE);
        persetubuhanChart.setVisibility(View.INVISIBLE);
        cabulChart.setVisibility(View.INVISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grafik");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getData();
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","indexsheet").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("indexsheet");

                    ArrayList<Entry> yValues = new ArrayList<>();
                    ArrayList<Entry> persetubuhanValues = new ArrayList<>();
                    ArrayList<Entry> cabulValues = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        float indexSheet = Float.parseFloat(jsonObject.optString("sheetnames"));
                        float kekerasanAnak = Float.parseFloat(jsonObject.optString("KekerasanAnak"));
                        float persetubuhanAnak = Float.parseFloat(jsonObject.optString("PersetubuhanAnak"));
                        float cabulAnak = Float.parseFloat(jsonObject.optString("CabulAnak"));

                        yValues.add(new Entry(indexSheet,kekerasanAnak));
                        persetubuhanValues.add(new Entry(indexSheet,persetubuhanAnak));
                        cabulValues.add(new Entry(indexSheet,cabulAnak));
                    }
                    chartKekerasanAnak(yValues);
                    chartPersetubuhanAnak(persetubuhanValues);
                    chartCabulAnak(cabulValues);

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void chartKekerasanAnak(ArrayList<Entry> yValues){
        kekerasanChart.setDragEnabled(true);
        kekerasanChart.setScaleEnabled(false);
        kekerasanChart.setVisibility(View.VISIBLE);
        aviKekerasan.hide();
        LineDataSet dataSet = new LineDataSet(yValues, "Pasal 76c Jo80 UU PA");
        dataSet.setFillAlpha(110);
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.BLUE);

        kekerasanChart.getAxisRight().setEnabled(false);
        kekerasanChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);

        LineData lineData = new LineData(iLineDataSets);
        kekerasanChart.setData(lineData);
    }
    private void chartPersetubuhanAnak(ArrayList<Entry> yValues){
        persetubuhanChart.setDragEnabled(true);
        persetubuhanChart.setScaleEnabled(false);
        persetubuhanChart.setVisibility(View.VISIBLE);
        aviPersetubuhan.hide();
        LineDataSet dataSet = new LineDataSet(yValues, "Pasal 76c Jo80 UU PA");
        dataSet.setFillAlpha(110);
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.BLUE);

        persetubuhanChart.getAxisRight().setEnabled(false);
        persetubuhanChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);

        LineData lineData = new LineData(iLineDataSets);
        persetubuhanChart.setData(lineData);
    }
    private void chartCabulAnak(ArrayList<Entry> yValues){
        cabulChart.setDragEnabled(true);
        cabulChart.setScaleEnabled(false);
        cabulChart.setVisibility(View.VISIBLE);
        aviCabul.hide();
        LineDataSet dataSet = new LineDataSet(yValues, "Pasal 76c Jo80 UU PA");
        dataSet.setFillAlpha(110);
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.BLUE);

        cabulChart.getAxisRight().setEnabled(false);
        cabulChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);

        LineData lineData = new LineData(iLineDataSets);
        cabulChart.setData(lineData);
    }
}