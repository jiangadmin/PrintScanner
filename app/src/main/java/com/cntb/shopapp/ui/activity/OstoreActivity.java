package com.cntb.shopapp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.cntb.shopapp.R;
import com.cntb.shopapp.common.CommonFun;
import com.cntb.shopapp.common.ConstantsTable;
import com.cntb.shopapp.common.HttpCallbackListener;
import com.cntb.shopapp.common.HttpUtil;
import com.cntb.shopapp.model.PrtTradeInfosModel;
import com.cntb.shopapp.servlet.Set_OutOfStock_Serlvet;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.HashMap;
import java.util.List;

public class OstoreActivity extends AppCompatActivity implements BarcodeReader.TriggerListener,
        BarcodeReader.BarcodeListener {

    private String LastGetBarcode = "";
    private String barcodeData;

    private TextView tvBarcode;
    private TextView tvProductname;
    private TextView tvSpecification;
    private TextView tvWeight;
    private TextView tvBoxnum;
    private TextView tvResult;
    private TextView tvRecid;

    //创建AidcManager和BarcodeReader对象
    AidcManager manager;
    BarcodeReader barcodeReader;

    List<PrtTradeInfosModel> listptim = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TRIM_MEMORY_COMPLETE) {
                PrtTradeInfosModel prtTradeInfosModel = listptim.get(0);
                tvBarcode.setText(prtTradeInfosModel.getPguid());
                tvRecid.setText(String.valueOf(prtTradeInfosModel.getRecid()));
                tvProductname.setText(prtTradeInfosModel.getProductname());
                tvSpecification.setText(prtTradeInfosModel.getSpecification());
                tvBoxnum.setText(prtTradeInfosModel.getBoxnum());
                tvWeight.setText(String.valueOf(prtTradeInfosModel.getWeight()));
                tvResult.setText("出库成功");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ostore);
        InitComps();
        AidcManager.create(this, new AidcManager.CreatedCallback() {
            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
                try {
                    //设置扫描参数
                    barcodeReader.setProperty(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);    //开启Code128码制
                    barcodeReader.setProperty(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);    //关闭EAN13码制
                    barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                            BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);                    //设置手动触发模式
                    barcodeReader.claim();        //开启扫描功能
                } catch (Exception e) {
                    Toast.makeText(OstoreActivity.this, "设置参数失败", Toast.LENGTH_SHORT).show();
                }

                //第二步：添加侧面扫描键事件监听和条码事件监听
                barcodeReader.addTriggerListener(OstoreActivity.this);
                barcodeReader.addBarcodeListener(OstoreActivity.this);
            }
        });

        new Set_OutOfStock_Serlvet().execute("18D26127B993430C376B0210701522F8");
//        GetPguidAmount("18D26127B993430C376B0210701522F8");
    }

    private void InitComps() {
        tvBarcode = findViewById(R.id.tvBarcode);
        tvBoxnum = findViewById(R.id.tvBOXNUM);
        tvProductname = findViewById(R.id.tvPRODUCTNAME);
        tvRecid = findViewById(R.id.tvRECID);
        tvResult = findViewById(R.id.tvRESULT);
        tvSpecification = findViewById(R.id.tvSPECIFICATION);
        tvWeight = findViewById(R.id.tvWEIGHT);
    }

    //第三步：实现条码事件和侧面扫描键触发事件
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        barcodeData = barcodeReadEvent.getBarcodeData();    //获取扫描数据
        //在UI线程中显示扫描数据
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GetPguidAmount(barcodeData);
            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        if (triggerStateChangeEvent.getState() == true) {
            try {
                barcodeReader.light(true);        //开启补光
                barcodeReader.aim(true);        //开启瞄准线
                barcodeReader.decode(true);        //开启解码
            } catch (Exception e) {
                Toast.makeText(this, "开启扫描失败", Toast.LENGTH_SHORT).show();
            }
        } else if (triggerStateChangeEvent.getState() == false) {
            try {
                barcodeReader.light(false);        //关闭补光
                barcodeReader.aim(false);        //关闭瞄准线
                barcodeReader.decode(false);    //关闭解码
            } catch (Exception e) {
                Toast.makeText(this, "关闭扫描失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //第四步：资源的释放与恢复
    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();        //开启扫描功能
            } catch (Exception e) {
                Toast.makeText(this, "开启扫描失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (barcodeReader != null) {
            barcodeReader.release();        //释放扫描资源
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (barcodeReader != null) {
            barcodeReader.removeBarcodeListener(this);
            barcodeReader.removeTriggerListener(this);
            barcodeReader.close();
        }

        if (manager != null) {
            manager.close();
        }
    }

    private void GetPguidAmount(final String apguid) {
        //构造HashMap
        HashMap<String, String> params = new HashMap<>();
        params.put("pguid", apguid);
        try {
            //构造完整URL
            String compeletedURL = HttpUtil.getURLWithParams(ConstantsTable.baseUrl + "prttradeinfosentity/SelectObjectsSumByProperty", params);
            //发送请求
            HttpUtil.sendHttpRequest(compeletedURL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    if (TextUtils.isEmpty(response)) {
                        response = "-1.0";
                    }
                    if (1.0f == Float.parseFloat(response)) {
                        LastGetBarcode = apguid;
                        doOutStore(apguid);
                    } else if (0.0f == Float.parseFloat(response)) {
                        tvResult.setText("库存不足");
                    } else if (-1.0f == Float.parseFloat(response)) {
                        tvResult.setText("该商品未入库");
                    } else {
                        tvResult.setText("该商品库存错误");
                    }
                }

                @Override
                public void onError(Exception e) {
                    tvResult.setText("查询失败！");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            tvResult.setText(e.getMessage());
        }
    }

    private void doOutStore(String spguid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pguid", spguid);
        try {
            //构造完整URL
            String compeletedURL = HttpUtil.getURLWithParams(ConstantsTable.baseUrl + "prttradeinfosentity/SelectObjectsByProperty", params);
            //发送请求
            HttpUtil.sendHttpRequest(compeletedURL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    listptim = CommonFun.stringToList(response, PrtTradeInfosModel.class);
                    Message msg = new Message();
                    msg.what = TRIM_MEMORY_COMPLETE;
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Exception e) {
                    tvResult.setText("出库失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            tvResult.setText(e.getMessage());
        }
        //先取原记录过来，然后产生一个新的记录写回去
    }

}
