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


    @POST("exec")
    Call<ResponseBody> hapusPenyidik(@Query("action") String action,
                                        @Query("sheetName") String sheetName,
                                        @Query("nrp") String nrp);
    @POST("exec")
    Call<ResponseBody> cariNrpPenyidik(@Query("action") String action,
                                     @Query("sheetName") String sheetName,
                                     @Query("nrp") String nrp);

    @POST("exec")
    Call<ResponseBody> cariNomor(@Query("action") String action,
                                       @Query("sheetName") String sheetName,
                                       @Query("Nomor") String nomor);

    @POST("exec")
    Call<ResponseBody> tambahLP(@Query("action") String action,
                                @Query("sheetName") String sheetName,
                                @Query("Kategori") String kategori,
                                @Query("Nomor") String nomor,
                                @Query("Pasal") String pasal,
                                @Query("NamaP") String namaP,
                                @Query("KelaminP") String kelaminP,
                                @Query("AlamatP") String alamatP,
                                @Query("NamaK") String namaK,
                                @Query("KelaminK") String kelaminK,
                                @Query("AlamatK") String alamatK,
                                @Query("NamaT") String namaT,
                                @Query("KelaminT") String kelaminT,
                                @Query("AlamatT") String alamatT,
                                @Query("Penyidik") String penyidik,
                                @Query("Nrp") String Nrp,
                                @Query("mo") String mo,
                                @Query("kerugian") String kerugian,
                                @Query("waktuKejadian") String waktukejadian,
                                @Query("lokasi") String lokasi);
}
