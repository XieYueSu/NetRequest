package com.sendi.netrequest;

import android.util.Log;


/**
 * Created by xieys on 2020/5/20.
 */

public class LogUtil {

    public static String TAG = "TEST ";
    private static boolean isLog = BuildConfig.DEBUG;

    public static void Log(Class clazz, String msg) {
        if (isLog) {
            Log.i( TAG + clazz.getCanonicalName(), msg);
        }
    }

    public static void Log(String msg) {
        if (isLog) {
            Log.i( TAG , msg);
        }
    }

    public static void Log(String tag, String msg) {
        if (isLog) {
            Log.i( tag , msg);
        }
    }

}
