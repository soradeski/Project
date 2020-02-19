package com.project.cardata.Controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.project.cardata.Mapper.OrderFinalMapper;
import com.project.cardata.bean.OrderFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Controller
public class ALiPayController {
    @Autowired
    OrderFinalMapper orderfinalmapper;

    OrderFinal orderfinal=new OrderFinal();


    private final String APP_ID = "2016102100730622";
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVP1jv5fejZQelFYlIUS5kys7vu2qHmhFVocnld64xOtYqzToj3sNRn3y5Y/2AhMpWisdKGp9FpAZUgN8FtMzTbPEai4JNpZeOPvzeDHiOKY8cAvAOfXBBGH+IFWtnBKjN/Mq0uyXxXdr84cX/QtbrraNFLs1MnBh9sdZ5k6VAqwakvn40q5xrmOGv5RzSL3iiZdswoQDod+vtRBwX7amP8OIjj9JOh/A4ju0DLPY8nzQg6GJmqYx2Lx0ThZ4Cs2BITQFqK76kqdmy1bnRRSO9+ag6G9Ds6+hyZ9GcjI85g+3BL12oLXL50RWf0nhfj65lKCCEFGXMvUNNPuET8hLLAgMBAAECggEAMXjaJkt4lvT6X8KPyyT91/NSPS1G1sQ/Ur9aiXHuj+sM6ws0ZoeE/x163olCksNxvtYp0WqZuR/x6Tj+tImGC99k3IO6IbruTfybHY/MtnMYORTQNj1mZG2WeKmA/JkSy5UsLFSM80FFQeYlF8Q0MdNQ97HX47kqdR/qFErY0GJetYwzTdonviFIkiw3WH83aNmyUBgNeDqi0yBzKWp12K2JcOgedpcbcnDyAZSsJgW2AX5Cb1NeVL6XaRg8/tBx9imX+/X1d4h0vzTDxQZNd5X55Dt+dp+D48Khvgo2YND1lOFQSta4tF7j7nsOOqxA4VYE0BEMFd4+LQtkzZHqoQKBgQDU7CPz9CUqqkkW52gp3AXQisata5LOuu8ECXd57Am+TNWdyRuKTJ6QkQf+UBl61U2wdyeEx2l/aI5mW/cKYnlxGDKybkDSe8q5tpNbENF0DV7vEhYvnXfksWKbicluoNHykoAfo0qNBkbxXcQt4vUcjLxAjSaxXtC1JzSGkAhqfwKBgQCzcUxsoD4qMrXz8KOteThRN+XAoH8eDBEAPSHUccZriBMugA0PIOi8oxdy12MTY1ZGWPUwO/V2BovgoBZCR2dRY+lO3xgcIJmYSFH8BrVdmjiRKIYwBALPQIr3wCHF6qyIPbv0lAFaoCjM8cs0+NPAffqUbURjObdWWNXeWHe5tQKBgQC9DzCdXRvjVnCvtan4ATXohlEDi5E/nxy8KOu9hqjgILlM8VVS59Wj6COA3P7C4sOo/SRJ8gbq6pwGp4Ij5myOzz200dEdFG5dmJAfPD+/HGA/R4ALp3bdyeQU17PUV1gUW6EIdqWrsvcrOmcHl46tWwmNB8hjlh0bvUQWd64BYQKBgGdM8hIWrbLdPqIQBin3ftRCuaEKxCMoDyF0D+WdGf0NeP1r+hibeWAfA4C5PfALX79ftsbtvYEUg7HsduPw9ymDuuwMwnRuz/06pPfRZNdd0PK/wYAizvtAzL+Te93UGz7TS0Chc6YYTWSuTa1REZXe8EJCJPx6XXD38ybtBv+BAoGBAL7thQZObpJlLw5bbD8qJ4YDhV/ozVFYEJNrfUj/fVgHOJB38btvogOac3IPqR99v1HAS1Wbz2+NkPYqpR4Ao2AT4jS42iEVKLjhLlJsUY/vsEtJCSaoh7k8lmpWOnMEVBbl9TxGNFCRESLhUH7E70iqptC6K4MoWdIJ2L2l7jc2";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://qiankushop.com";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://localhost:8080/checkpay";

    @RequestMapping("/alipay/{order_id}")
    public void alipay(HttpServletResponse httpResponse, @PathVariable("order_id") Integer order_id) throws IOException {
        orderfinal.setOrder_id(order_id);
        System.out.println(orderfinal.getOrder_id());
       //Random r=new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        orderfinal=orderfinalmapper.getOrderFinalByOrder_id(orderfinal);
        System.out.println(orderfinal.getOrder_id()+"  "+orderfinal.getDate());
        String total_amount =orderfinal.getSum().toString();
        //订单名称，必填
        String subject =orderfinal.getGoods_id();
        //商品描述，可空
        String body = "千库订单"+orderfinal.getOrder_id();
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();

        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping(value = "/checkpay")
    public Object returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            //支付成功，修复支付状态
            System.out.println("开始修改支付状态");
            orderfinalmapper.updateStateByOrder_id(orderfinal);
            System.out.println("修改支付状态成功");
            return "ok";//跳转付款成功页面
        }else{
            System.out.println("验签未通过");
            return "no";//跳转付款失败页面
        }

    }

}
