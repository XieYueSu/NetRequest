package com.sendi.netrequest;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mBtn_post;
    private Button mBtn_get;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requets_permission();


        //post();123456

        mLogin = findViewById(R.id.login);
        mBtn_post = findViewById(R.id.btn_post);
        mBtn_get = findViewById(R.id.btn_get);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJson();
            }
        });
        mBtn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp_post_request();
            }
        });
        mBtn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp_request_get();
            }
        });




    }

    private void okhttp_post_request() {
        String url="http://103.215.44.51:9999/build_monitor/map/select";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("search","");
            json.put("pageNum","1");
            json.put("pageSize", "4");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //法2
        Map<String,String> map=new HashMap<>();
        map.put("search","");
        map.put("pageNum","1");
        map.put("pageSize", "4");
        Gson  gson=new Gson();
        String json2 = gson.toJson(map);

        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        //json为String类型的json数据
      //  RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
        RequestBody requestBody = RequestBody.create(JSON, json2);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("datae-token",mToken)
                .post(requestBody)
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("aaaddd","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("aaaddd",result);
                //错误原因: response.body().string()中的 (.string)只能使用一次
            }
        });


    }

    private void okhttp_request_get() {
        String url="http://103.215.44.51:9999/build_monitor/alarm/picture";
        String batchId="f983a073-960b-4baa-b8a6-5d009c20b040";
        //创建OkHttpClient对象
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();


        final Request request = new Request.Builder()
                .url(url+"?batchId="+batchId)//请求的url
                .addHeader("datae-token",mToken)
                .get()//设置请求方式，get()/post()  查看Builder()方法知，在构建时默认设置请求方式为GET
                .build(); //构建一个请求Request对象

        //创建/Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("aaaddd","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("aaaddd",result);
                //错误原因: response.body().string()中的 (.string)只能使用一次
            }
        });
    }

    private void postJson() {
        String name="admin";
        String password="1234";
        String url="http://103.215.44.51:8989/auth/user/signIn";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("username",name);
            json.put("password", MD5AndSHA.MD5_SHA(password, MD5AndSHA.SHA));
            json.put("codeIdentity", "");
            json.put("checkCode", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("aaaddd","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("aaaddd",result);
                try {
                    JSONObject  jsonObject=new JSONObject(result);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.body()!=null){
                    response.body().close();
                }
            }
        });
    }

    private void post() {
        String name="admin";
        String password="1234";
        String url="http://103.215.44.51:8989/auth/user/signIn";


        OkHttpClient client=new OkHttpClient();
        //构建表单参数
        FormBody.Builder requestBuild=new FormBody.Builder();
        //RequestBody中的MediaType指定为纯文本，编码方式是utf-8


        //添加请求体
        RequestBody requestBody=requestBuild
                .add("username",name)
                .add("password", MD5AndSHA.MD5_SHA(password, MD5AndSHA.SHA))
                .add("codeIdentity", "")
                .add("checkCode", "")
                .build();


        //创建网络请求
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        System.out.println(request.toString());


        //发送异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("aaaddd","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("aaaddd",result);
                if (response.body()!=null){
                    response.body().close();
                }
            }

        });
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
}
