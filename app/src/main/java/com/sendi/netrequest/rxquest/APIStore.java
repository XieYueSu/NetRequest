package com.sendi.netrequest.rxquest;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIStore {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST ("auth/user/signIn")
    Call<ResponseBody>getMessage(@Body RequestBody info);   // 请求体味RequestBody 类型

}
