package com.cntb.shopapp.common;

/**
 * Created by huangjianping on 2018/12/13.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
