package com.example.lpppa.models;

public class ListTahun {
    public String getTahun() {
        return Tahun;
    }

    public void setTahun(String tahun) {
        Tahun = tahun;
    }

    public String getJenisLaporan() {
        return JenisLaporan;
    }

    public void setJenisLaporan(String jenis) {
        JenisLaporan = jenis;
    }


    private String JenisLaporan;
    private String Tahun;
}
