package com.example.lpppa.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("exec")
    Call<ResponseBody> getPenyidik(@Query("action") String action,
                                   @Query("sheetName") String sheetName);

    @GET("exec")
    Call<ResponseBody> getData(@Query("action") String action,
                                   @Query("sheetName") String sheetName);

    @POST("exec")
    Call<ResponseBody> updatePerkembangan(@Query("action") String action,
                                          @Query("sheetName") String sheetName,
                                          @Query("noLp") String noLP,
                                          @Query("perkembangan") String perkembangan);
}
