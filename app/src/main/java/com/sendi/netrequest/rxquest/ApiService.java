package com.sendi.netrequest.rxquest;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("auth/user/signIn")
    Call<String> login(@Field("username") String username,
                       @Field("password") String password,
                       @Field("codeIdentity") String deviceId,
                       @Field("checkCode") String platform);

}
