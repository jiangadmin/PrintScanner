package com.cntb.shopapp.servlet;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.cntb.shopapp.common.ConstantsTable;
import com.cntb.shopapp.model.OutOfStock_Model;
import com.cntb.shopapp.utils.HttpUtil;
import com.cntb.shopapp.utils.LogUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangyao
 * @date: 2018/12/26
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 出库请求
 */
public class Set_OutOfStock_Serlvet extends AsyncTask<String, Integer, OutOfStock_Model> {
    private static final String TAG = "Set_OutOfStock_Serlvet";

    @Override
    protected OutOfStock_Model doInBackground(String... strings) {

        Map map = new HashMap();

        map.put("pguid", strings[0]);

        String res = HttpUtil.request(HttpUtil.GET, ConstantsTable.baseUrl + "prttradeinfosentity/SelectObjectsByProperty", map);

        OutOfStock_Model model;

        if (TextUtils.isEmpty(res)) {
            return null;
        } else {
            try {
                model = new Gson().fromJson(res,OutOfStock_Model.class);

            }catch (Exception e){
                return null;
            }
        }

        LogUtil.e(TAG, res);

        return model;
    }

    @Override
    protected void onPostExecute(OutOfStock_Model model) {
        super.onPostExecute(model);
    }
}
