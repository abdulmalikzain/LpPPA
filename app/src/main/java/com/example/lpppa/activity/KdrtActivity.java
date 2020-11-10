package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.os.Bundle;

import com.example.lpppa.R;
import com.example.lpppa.adapter.AdapterListTahun;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ListTahun;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KdrtActivity extends AppCompatActivity {

    private BarChart barChart;
    public static List<ListTahun> dataIndikator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdrt);
        barChart = findViewById(R.id.chart_bar);
        dataIndikator = new ArrayList<>();

//        BarDataSet barDataSet = new BarDataSet(barEntries1(), "dataset1");
//        barDataSet.setColor(Color.BLUE);
//        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "dataset1");
//        barDataSet2.setColor(Color.CYAN);
//        BarDataSet barDataSet3 = new BarDataSet(barEntries3(), "dataset1");
//        barDataSet3.setColor(Color.RED);
//        BarDataSet barDataSet4 = new BarDataSet(barEntries4(), "dataset1");
//        barDataSet4.setColor(Color.YELLOW);
//
//        BarData barData = new BarData(barDataSet, barDataSet2, barDataSet3, barDataSet4);
//        barChart.setData(barData);
//
//        String[] strings = new String[]{"se","dua","tida","empat"};
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//
//        barChart.setDragEnabled(true);
//        barChart.setVisibleXRangeMaximum(3);
//
//        float barspace = 0.08f;
//        float group = 0.44f;
//        barData.setBarWidth(0.10f);
//
//        barChart.getXAxis().setAxisMaximum(0);
//        barChart.getXAxis().setAxisMinimum(0+barChart.getBarData().getGroupWidth(group, barspace)*7);
//        barChart.getAxisLeft().setAxisMinimum(0);
//
//        barChart.groupBars(0,group , barspace);
//        barChart.invalidate();
        getData();

    }

//    private ArrayList<BarEntry> barEntries1(){
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 900));
//        entries.add(new BarEntry(2, 100));
//        entries.add(new BarEntry(3, 600));
//        entries.add(new BarEntry(4, 400));
//        return barEntries1();
//    }
//
//    private ArrayList<BarEntry> barEntries2(){
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 900));
//        entries.add(new BarEntry(2, 800));
//        entries.add(new BarEntry(3, 650));
//        entries.add(new BarEntry(4, 300));
//        return barEntries2();
//    }
//    private ArrayList<BarEntry> barEntries3(){
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 900));
//        entries.add(new BarEntry(2, 130));
//        entries.add(new BarEntry(3, 650));
//        entries.add(new BarEntry(4, 770));
//        return barEntries3();
//    }
//    private ArrayList<BarEntry> barEntries4(){
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 900));
//        entries.add(new BarEntry(2, 100));
//        entries.add(new BarEntry(3, 200));
//        entries.add(new BarEntry(4, 600));
//        return barEntries4();
//    }


    private void getData(){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","indexsheet").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("indexsheet");

                    ArrayList<BarEntry> yVals = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String indexSheet = jsonObject.optString("sheetnames");
                        String kdrt = jsonObject.optString("kdrt");

                        ListTahun listTahun = new ListTahun();
                        listTahun.setTahun(indexSheet);
                        listTahun.setKdrt(kdrt);
                        dataIndikator.add(listTahun);
                        yVals.add(new BarEntry(Float.parseFloat(dataIndikator.get(i).getTahun()), i));



//                        for (int i = 0; i < dataIndikator.size(); i++) {
//                            DataItemJ554IndikatorByTipe x = dataIndikator.get(i);
//                            float a12 = Float.parseFloat(x.getJmlIndikator());
//                            int a22 = Integer.parseInt(x.getId());
//                            yVals.add(new BarEntry(Float.parseFloat(dataIndikator.get(i).getJmlIndikator()), i));
//                        }
//                        tahunList.add(listTahun);
//                        AdapterListTahun listTahun1 = new AdapterListTahun(ListTahunActivity.this, tahunList);
//                        recyclerView.setAdapter(listTahun1);
                    }
                    ArrayList<String> xVals = new ArrayList<String>();
//                        for (int i = 0; i < dataIndikator.size(); i++) {
//                            DataItemJ554IndikatorByTipe x = dataIndikator.get(i);
//                            float a12 = Float.parseFloat(x.getJmlIndikator());
//                            String a22 = x.getIdIndikator();
//                    xVals.add(kdrt);
//
//                        }
//
                    BarDataSet dataSet = new BarDataSet(yVals, dataIndikator.get(0).getKdrt());
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarData data = new BarData((IBarDataSet) xVals, dataSet);


                    LimitLine line = new LimitLine(12f, dataIndikator.get(0).getKdrt());
                    line.setTextSize(12f);
                    line.setLineWidth(4f);
                    YAxis leftAxis = barChart.getAxisLeft();
                    leftAxis.addLimitLine(line);

                    barChart.setData(data);
//                    barChart.setDescription(dataIndikator.get(0).setKdr);
                    barChart.animateY(2000);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void addData(){

//        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
//
//        for (int i = 0; i < mExpenseDB.queryYData().size(); i++)
//            yVals.add(new BarEntry(mExpenseDB.queryYData().get(i), i));
//
//        ArrayList<String> xVals = new ArrayList<String>();
//        for(int i = 0; i < mExpenseDB.queryXData().size(); i++)
//            xVals.add(mExpenseDB.queryXData().get(i));
//
//        BarDataSet dataSet = new BarDataSet(yVals, "expense values");
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        BarData data = new BarData(xVals, dataSet);
//
//
//        LimitLine line = new LimitLine(12f, "average daily expense");
//        line.setTextSize(12f);
//        line.setLineWidth(4f);
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.addLimitLine(line);
//
//        barChart.setData(data);
//        barChart.setDescription("The expenses chart.");
//        barChart.animateY(2000);

    }
}