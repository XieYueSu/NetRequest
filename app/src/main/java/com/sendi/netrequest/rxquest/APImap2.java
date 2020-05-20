package com.sendi.netrequest.rxquest;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface APImap2 {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST ("build_monitor/map/select")
    Observable<ResponseBody> getMessage(@Body RequestBody info);   // 请求体味RequestBody 类型

}
