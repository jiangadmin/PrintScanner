package com.cntb.shopapp.model;

import com.cntb.shopapp.common.ConstantsTable;
import com.cntb.shopapp.common.HttpInterface;
import com.cntb.shopapp.common.ProgressHelper;
import com.cntb.shopapp.common.RetrofitHttp;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * author：Anumbrella
 * Date：16/6/24 下午8:08
 */
public class UpdateAppModel {

    /**
     * 获取最新版本app信息
     *
     * @param callback
     */
    public static void getUpdateAppInfo(Callback<ResponseBody> callback) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).getUpdateAppInfo("getUpdateAppInfo")
                .enqueue(callback);
    }


    public static void updateApp(Callback<ResponseBody> callback, String pathApp) {
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsTable.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();
        HttpInterface downInterface = retrofit.create(HttpInterface.class);
        downInterface.updateApp(pathApp)
                .enqueue(callback);
    }
}
