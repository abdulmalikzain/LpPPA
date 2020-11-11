package com.example.lpppa.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL_PENYIDIK = "https://script.google.com/macros/s/AKfycbwMfrVS749iPRrLWuV6Cl50T3-yGU6os4rOkhYpHDLwfFlhpl4F/";
    private static final String BASE_URL_Data = "https://script.google.com/macros/s/AKfycbxzNZfDzHOyHIQqJj7EkLpZZYQUwvrzCFovqbN87Arosn7K8Fg/";
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_PENYIDIK)
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_Data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static ApiService updateRetroPenyidik(){
        return updatePenyidik().create(ApiService.class);
    }
}
