package com.sendi.netrequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by xieys on 2020/7/31.
 */
public class DateUtils {
    /**
     * 时间戳转换成日期（被测试的方法）
     * @param timeStamp 要求是10位 单位为秒的时间戳
     * @return
     */
    public static String timeStampToString(int timeStamp){
        long temp = (long) timeStamp*1000;//将秒转换成毫秒
        Timestamp ts = new Timestamp(temp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=null;
        dateString = simpleDateFormat.format(ts);
        return dateString;
    }

}
