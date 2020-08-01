package com.sendi.netrequest.rxquest;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.sendi.netrequest.LogUtil;
import com.sendi.netrequest.MD5AndSHA;
import com.sendi.netrequest.PermissionUtils;
import com.sendi.netrequest.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SecondActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mBtn_post;
    private Button mBtn_get;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        requets_permission();
        Log.d("cccfff","bonCreate");
        //post();
        mLogin = findViewById(R.id.login);
        mBtn_post = findViewById(R.id.btn_post);
        mBtn_get = findViewById(R.id.btn_get);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();


            }
        });
        mBtn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrofit_post();
                rxjava_post();


            }




        });
        mBtn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // retrofit_get();
                rxjava_get();


            }

        });





    }

    private void login() {
        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.215.44.51:8989/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Info info=new Info("admin", MD5AndSHA.MD5_SHA("1234", MD5AndSHA.SHA),"","");
        Gson gson=new Gson();
        String obj=gson.toJson(info);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        final APIStore login = retrofit.create(APIStore.class);
        Call<ResponseBody> data = login.getMessage(body);
        data.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                try {
                    String  result = response.body().string();
                    Log.d("aaaddd",result);
                    JSONObject jsonObject=new JSONObject(result);
                    String success = jsonObject.getString("success");
                    LogUtil.Log("nnnsss", "success=" + success);
                    String msg = jsonObject.getString("msg");
                    LogUtil.Log("nnnsss", "msg=" + msg);
                    if (success.equals("true")){
                        // DialogUtil.hideLoadDialog();
                        String data = jsonObject.getString("data");
                        LogUtil.Log("nnnsss", "data=" + data);
                        JSONObject dataObject = new JSONObject(data);
                        LogUtil.Log("nnnsss", "dataObject=" + dataObject);
                        String roleId = dataObject.getString("roleId");
                        LogUtil.Log("nnnsss", "roleId=" + roleId);
                        String roleName = dataObject.getString("roleName");
                        LogUtil.Log("nnnsss", "roleName=" + roleName);
                        mToken = dataObject.getString("token");
                        LogUtil.Log("nnnsss", "token=" + mToken);
                        String userId = dataObject.getString("userId");
                        LogUtil.Log("nnnsss", "userId=" + userId);
                        String userName2 = dataObject.getString("userName");
                        LogUtil.Log("nnnsss", "userName2=" + userName2);
                        //   Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        boolean  is_main_thread=isMainThread();
                        LogUtil.Log("nnnsss", "is_main_thread=" + is_main_thread);
                        LogUtil.Log("nnnsss", "is_main_process=" + isMainProcess());



                    }else {
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("aaasss", "onResponse: --err--"+t.toString());
            } });
    }

    private void rxjava_post() {
        //添加请求头
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                //key的话以后台给的为准，我这边是叫token
                Request updateRequest = originalRequest.newBuilder().header("datae-token", mToken).build();
                return chain.proceed(updateRequest);

            }
        });

        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.215.44.51:9999/")
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();

        MapInfo info=new MapInfo();
        info.setSearch("");
        info.setPageNum("1");
        info.setPageSize("4");

        Gson gson=new Gson();
        String obj=gson.toJson(info);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        final APImap2 apImap = retrofit.create(APImap2.class);
        apImap.getMessage(body)
                //设置call()执行的线程为新起一个线程
                .subscribeOn(Schedulers.newThread())
                //设置onNext()执行的线程在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.d("aaaddd", responseBody.string());
                            //Log.d("aaaddd", responseBody.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void rxjava_get() {
        //添加请求头
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                //key的话以后台给的为准，我这边是叫token
                Request updateRequest = originalRequest.newBuilder().header("datae-token", mToken).build();
                return chain.proceed(updateRequest);

            }
        });

        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.215.44.51:9999/")
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        String batchId="f983a073-960b-4baa-b8a6-5d009c20b040";
        final APIpic2 apIpic = retrofit.create(APIpic2.class);
        //结合rxjava
        apIpic.getMessage(batchId)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.d("aaaddd", responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void retrofit_get() {
        //添加请求头
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                //key的话以后台给的为准，我这边是叫token
                Request updateRequest = originalRequest.newBuilder().header("datae-token", mToken).build();
                return chain.proceed(updateRequest);

            }
        });

        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.215.44.51:9999/")
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String batchId="f983a073-960b-4baa-b8a6-5d009c20b040";
        final APIpic apIpic = retrofit.create(APIpic.class);
        Call<ResponseBody> data = apIpic.getMessage(batchId);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String   result = response.body().string();
                    Log.d("aaaddd",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("aaaddd", "onResponse: --err--"+t.toString());
            }
        });


    }
    private void retrofit_post() {
        //添加请求头
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                //key的话以后台给的为准，我这边是叫token
                Request updateRequest = originalRequest.newBuilder().header("datae-token", mToken).build();
                return chain.proceed(updateRequest);

            }
        });

        /**
         * 初始化
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.215.44.51:9999/")
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //MapInfo  info=new MapInfo("","1","4");
        MapInfo info=new MapInfo();
        info.setSearch("");
        info.setPageNum("1");
        info.setPageSize("4");

        Gson gson=new Gson();
        String obj=gson.toJson(info);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        final APImap apImap = retrofit.create(APImap.class);
        Call<ResponseBody> data = apImap.getMessage(body);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String   result = response.body().string();
                    Log.d("aaaddd",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("aaaddd", "onResponse: --err--"+t.toString());
            }
        });
    }
    private void rxtrofit_public() {
        //1开启日志
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //启用Log日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        //2开启Gson转换
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        //配置转化库，采用Gson
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());

        //3采用Rxjava
        //配置回调库，采用RxJava
        retrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        //4 设置基础请求路径BaseUrl
        //服务器地址，基础请求路径，最好以"/"结尾
        retrofitBuilder.baseUrl("http://103.215.44.51:8989/");
        //5设置请求超时
        //设置请求超时时长为15秒
        okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        //6 设置缓存
        Interceptor cacheIntercepter=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //对request的设置用来指定有网/无网下所走的方式
                //对response的设置用来指定有网/无网下的缓存时长

                Request request = chain.request();
                if (!NetworkUtil.isNetWorkEnable(SecondActivity.this)) {
                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
                    //有网络时则根据缓存时长来决定是否发出请求
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE).build();
                }

                Response response = chain.proceed(request);
                if (NetworkUtil.isNetWorkEnable(SecondActivity.this)) {
                    //有网络情况下，超过1分钟，则重新请求，否则直接使用缓存数据
                    int maxAge = 60; //缓存一分钟
                    String cacheControl = "public,max-age=" + maxAge;
                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
                    //将其超时时间maxAge设为0即可
                    return response.newBuilder()
                            .header("Cache-Control",cacheControl)
                            .removeHeader("Pragma").build();
                } else {
                    //无网络时直接取缓存数据，该缓存数据保存1周
                    int maxStale = 60 * 60 * 24 * 7 * 1;  //1周
                    return response.newBuilder()
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                            .removeHeader("Pragma").build();
                }

            }
        };

        File cacheFile = new File(SecondActivity.this.getExternalCacheDir(), "HttpCache");//缓存地址
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //大小50Mb
        okHttpClientBuilder.addNetworkInterceptor(cacheIntercepter);
        okHttpClientBuilder.addInterceptor(cacheIntercepter);
        okHttpClientBuilder.cache(cache);
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder();
                //设置具体的header内容
                builder.header("timestamp", System.currentTimeMillis() + "");

                Request.Builder requestBuilder =
                        builder.method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        //设置统一的header
        okHttpClientBuilder.addInterceptor(headerInterceptor);


    }

    public void requets_permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            PermissionUtils.requestPermissions(this, new String[]{
                            android.Manifest.permission.INTERNET

                    },
                    1000,
                    "5.0之后申请权限",
                    new PermissionUtils.OnPermissionResult() {
                        @Override
                        public void granted(int requestCode) {

                        }

                        @Override
                        public void denied(int requestCode) {

                        }
                    });
        }
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    //判断是否主进程
    private boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();

        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("cccfff","bonRestart");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("cccfff","bonStart");
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d("cccfff","bonResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("cccfff","bonPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("cccfff","bonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("cccfff","bonDestroy");
    }
}
