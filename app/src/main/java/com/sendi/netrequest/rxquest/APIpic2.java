package com.sendi.netrequest.rxquest;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface APIpic2 {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("build_monitor/alarm/picture")
    Observable<ResponseBody> getMessage(@Query("batchId") String batchId);   // 请求体味RequestBody 类型

}
