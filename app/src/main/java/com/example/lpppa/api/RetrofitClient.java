package com.example.lpppa.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL_PENYIDIK = "https://script.google.com/macros/s/AKfycbwMfrVS749iPRrLWuV6Cl50T3-yGU6os4rOkhYpHDLwfFlhpl4F/";
    private static final String BASE_URL_Data = "https://script.google.com/macros/s/AKfycbxzNZfDzHOyHIQqJj7EkLpZZYQUwvrzCFovqbN87Arosn7K8Fg/";
    private static final String BASE_URL_Upload_Photo = "https://script.google.com/macros/s/AKfycbw3W_o27AeRbJMGOEowHmP3VD3Aamj3vTYPnwZRmTKHg8rr03E/";
    private static final String BASE_URL_PencarianNRPNomor = "https://script.google.com/macros/s/AKfycbxp-hQZIh2V-LFWfAhWJ_1e8dsOao2Eyq54Zzc-xv1fAEJPz78/";
    public static Retrofit getPenyidik(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_PENYIDIK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService getRetroPenyidik(){
            return getPenyidik().create(ApiService.class);
    }

    public static Retrofit getPencarian(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_PencarianNRPNomor)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService getRetroPencarian(){
        return getPencarian().create(ApiService.class);
    }

    public static Retrofit getAllData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_Data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService getRetroData(){
        return getAllData().create(ApiService.class);
    }

    public static Retrofit updatePerkembangan(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_Data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService updateRetroPerkembangan(){
        return updatePerkembangan().create(ApiService.class);
    }

    public static Retrofit updatePenyidik(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_PENYIDIK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService updateRetroPenyidik(){
        return updatePenyidik().create(ApiService.class);
    }

    public static Retrofit editPhotoPenyidik(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_Upload_Photo)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService editRetroPhotoPenyidik(){
        return editPhotoPenyidik().create(ApiService.class);
    }

    public static Retrofit cariNrpPenyidik(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_PencarianNRPNomor)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService cariRetroNrpoPenyidik(){
        return cariNrpPenyidik().create(ApiService.class);
    }
}
