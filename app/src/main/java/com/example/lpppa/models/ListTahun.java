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

    public String getRekom() {
        return Rekom;
    }

    public void setRekom(String rekom) {
        Rekom = rekom;
    }

    private String JenisLaporan;
    private String Tahun;
    private String Rekom;

    int tahunx;
    int kdrt;

    public int getTahunx() {
        return tahunx;
    }

    public void setTahunx(int tahunx) {
        this.tahunx = tahunx;
    }

    public int getKdrt() {
        return kdrt;
    }

    public void setKdrt(int kdrt) {
        this.kdrt = kdrt;
    }
//
//    public ListTahun(int tahunx, int kdrt) {
//        this.tahunx = tahunx;
//        this.kdrt = kdrt;
//    }
}
