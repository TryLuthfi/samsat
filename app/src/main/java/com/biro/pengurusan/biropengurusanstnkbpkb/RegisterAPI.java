package com.biro.pengurusan.biropengurusanstnkbpkb;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {

    @FormUrlEncoded
    @POST("/samsat/api/insert.php")
    Call<Value> daftar(@Field("no_ktp") String no_ktp,
                       @Field("nama_pelanggan") String nama_pelanggan,
                       @Field("email") String email,
                       @Field("alamat_pelanggan") String alamat_pelanggan,
                       @Field("telp_pelanggan") String telp_pelanggan,
                       @Field("password") String password,
                       @Field("username") String usernama);
}