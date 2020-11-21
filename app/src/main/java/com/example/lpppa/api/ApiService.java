package com.example.lpppa.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

//    @FormUrlEncoded
    @POST("exec")
    Call<ResponseBody> updatePerkembangan(@Query("action") String action,
                                          @Query("sheetName") String sheetName,
                                          @Query("Nomor") String noLP,
                                          @Query("Perkembangan") String perkembangan);

    @POST("exec")
    Call<ResponseBody> updatePenyidik(@Query("action") String action,
                                      @Query("sheetName") String sheetName,
                                      @Query("nrp") String nrp,
                                      @Query("nama") String nama,
                                      @Query("pangkat") String pangkat,
                                      @Query("jabatan") String jabatan,
                                      @Query("notelpon") String notelpon);
    @POST("exec")
    Call<ResponseBody> tambahPenyidik(@Query("action") String action,
                                      @Query("sheetName") String sheetName,
                                      @Query("nrp") String nrp,
                                      @Query("nama") String nama,
                                      @Query("password") String password,
                                      @Query("pangkat") String pangkat,
                                      @Query("jabatan") String jabatan,
                                      @Query("notelpon") String notelpon,
                                      @Query("image") String image);

    @FormUrlEncoded
    @POST("exec")
    Call<ResponseBody> editpotoPenyidik(@Query("action") String action,
                                      @Field("nrp") String id,
                                        @Field("nama") String nama,
                                        @Field("image") String image);
}
