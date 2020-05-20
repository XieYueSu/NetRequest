package com.sendi.netrequest.rxquest;

/**
 * Created by Administrator on 2020/5/19.
 */
public class Info {
    String username;
    String password;
    String codeIdentity;
    String checkCode;

    public Info(String username, String password, String codeIdentity, String checkCode) {
        this.username = username;
        this.password = password;
        this.codeIdentity = codeIdentity;
        this.checkCode = checkCode;
    }
}
