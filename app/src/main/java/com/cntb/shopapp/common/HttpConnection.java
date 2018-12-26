package com.cntb.shopapp.common;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangjianping on 2017/12/11.
 */

public class HttpConnection {
    /**
     *
     * @param requestType   请求类型
     * @param requestContent    [类型，参数，类型，参数]
     * @return
     */
    public String requestService(String requestType,String... requestContent){
        String resultData = "";
        InputStreamReader in = null;
        HttpURLConnection urlConn = null;
        BufferedReader buffer = null;
        try{
            StringBuffer sBuffer = new StringBuffer();
            for(int n=0;n<requestContent.length;n++){
                if((n+1)%2==0){
                    if((n+1)==requestContent.length){
                        sBuffer.append("="+ requestContent[n]);
                    }else{
                        sBuffer.append("="+ requestContent[n] + "&");
                    }
                }else{
                    sBuffer.append(requestContent[n]);
                }
            }
            URL url = new URL(ConstantsTable.baseUrl + requestType + "?" + sBuffer);
            Log.e("url", String.valueOf(url));
            if(url != null){
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setConnectTimeout(ConstantsTable.TIMEOUT);
                //urlConn.setReadTimeout(ConstantsTable.READTIMEOUT);
                urlConn.setRequestMethod(ConstantsTable.REQUEST_TYPE);

                try{
                    in = new InputStreamReader(urlConn.getInputStream());
                }catch (ConnectException e){
                    Log.e(ConstantsTable.LOG_TAG,"服务器宕机了...");
                    return resultData;
                }
                buffer = new BufferedReader(in);
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null){
                    resultData += inputLine + "\n";
                }
                urlConn.disconnect();
                if(ConstantsTable.DEBUG_FLAG){
                    Log.d(ConstantsTable.LOG_TAG, resultData);
                }
            }
        }catch (MalformedURLException e){
            Log.e(ConstantsTable.LOG_TAG,"域名无法解析");
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
                if(buffer != null){
                    buffer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return resultData;
    }

    public static ArrayList<HashMap<String,Object>> JsonStrToArrayList(String jsonStr)
            throws JSONException {
        JSONArray jsonArray = null;
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            //找出所有的Key赋给对象
            map.put("xianJia", jsonObject.getString("xianJia"));
            map.put("id", jsonObject.getInt("id"));
            list.add(map);
        }
        return list;
    }

    /**
     * 实现把jsonstr转化成对象list数组
     * @param c
     * @param jsonStr
     * @param <T>
     * @return
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * 调用：List<User> l = JsonStrToList(User.class,jsonstr);
     */
    public static <T> List<T> JsonStrToList(Class<T> c,String jsonStr) throws JSONException,
            IllegalAccessException, InstantiationException {
         List<T> list = new ArrayList<T>();
        JSONArray jsonArray = null;
        jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            T t = c.newInstance();
            Field[] fields=t.getClass().getDeclaredFields();
            for(Field field:fields)
            {
                Object obj = jsonObject.get(field.getName());
                if(obj!=null && obj!=""){
                    if(!field.isAccessible())
                    {
                        field.setAccessible(true);
                    }
                    field.set(t,obj);
                }
            }
            list.add(t);
        }
        return  list;
    }

}
