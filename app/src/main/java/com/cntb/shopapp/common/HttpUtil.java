package com.cntb.shopapp.common;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjianping on 2018/12/13.
 */

public class HttpUtil {
    //封装的发送请求函数
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    //使用HttpURLConnection
                    connection = (HttpURLConnection) url.openConnection();
                    //设置方法和参数
                    /**
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(ConstantsTable.TIMEOUT);
                    connection.setReadTimeout(ConstantsTable.TIMEOUT);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    //获取返回结果
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    //成功则回调onFinish
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                     */
                    if(200 == connection.getResponseCode()){
                        //得到输入流
                        InputStream is =connection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[10240];
                        int len = 0;
                        while(-1 != (len = is.read(buffer))){
                            baos.write(buffer,0,len);
                            baos.flush();
                        }
                        if (listener != null){
                            listener.onFinish(baos.toString("utf-8"));
                        }
                    }else
                    {
                        if (listener != null){
                            listener.onError(new Exception("返回错误代码"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //出现异常则回调onError
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendHttpRequestPost(final String address,final String spost,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    //使用HttpURLConnection
                    connection = (HttpURLConnection) url.openConnection();
                    //设置方法和参数
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(ConstantsTable.TIMEOUT);
                    connection.setReadTimeout(ConstantsTable.TIMEOUT);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    // 获取URLConnection对象对应的输出流
                    PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                    // 发送请求参数
                    printWriter.write(spost);//post的参数 xx=xx&yy=yy
                    // flush输出流的缓冲
                    printWriter.flush();
                    //获取返回结果
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    //成功则回调onFinish
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //出现异常则回调onError
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    //组装出带参数的完整URL
    public static String getURLWithParams(String address,Map<String,String> params) throws UnsupportedEncodingException {
        //设置编码
        final String encode = "UTF-8";
        StringBuilder url = new StringBuilder(address);
        url.append("?");
        //将map中的key，value构造进入URL中
        for(Map.Entry<String, String> entry:params.entrySet())
        {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encode));
            url.append("&");
        }
        //删掉最后一个&
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

}
