package com.project.cardata.bean;

import org.springframework.stereotype.Component;

@Component
public class ALiPay {
    private final String APP_ID = "应用的APPID";
    private final String APP_PRIVATE_KEY = "生成的应用私钥";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "支付宝公钥";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://公网地址/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://公网地址/returnUrl";
}
