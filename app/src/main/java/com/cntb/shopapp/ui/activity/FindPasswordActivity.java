package com.cntb.shopapp.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cntb.shopapp.R;
import com.cntb.shopapp.common.CommonFun;
import com.cntb.shopapp.model.FindPassDataModel;

import net.anumbrella.customedittext.FloatLabelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class FindPasswordActivity extends Activity {

    /**
     * 短信验证码等待时间
     */
    private int time = 60;

    private String phone;

    private String code;

    private String password;

    private boolean flag = true;

    private ProgressDialog mDialog;

    @BindView(R.id.btn_back)
    Button btn_back;


    @BindView(R.id.find_pass_phone)
    FloatLabelView passPhone;


    @BindView(R.id.find_pass_code)
    FloatLabelView passCode;


    @BindView(R.id.send_code)
    Button sendCode;


    @BindView(R.id.find_pass_new)
    FloatLabelView passNew;


    @BindView(R.id.find_pass_upadate_pass)
    Button updatePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        passNew.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //initSMS();
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("请稍等");
        mDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        //initSMS();
        super.onResume();
    }

    @OnClick({R.id.btn_back, R.id.find_pass_upadate_pass, R.id.send_code})
    public void clickBtn(View view) {
        phone = passPhone.getEditText().getText().toString().trim();
        code = passCode.getEditText().getText().toString().trim();
        password = passNew.getEditText().getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.find_pass_upadate_pass:
                if (!TextUtils.isEmpty(phone)) {
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        if (code.length() == 4) {
                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                //对验证码进行校验
                                //SMSSDK.submitVerificationCode("86", phone, code);
                                flag = false;
                            }
                        } else {
                            Toast.makeText(this, "验证码格式不正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.send_code:
                //发送验证码
                if (!TextUtils.isEmpty(phone)) {
                    if (CommonFun.checkPhoneNumber(phone)) {
                        //checkPhoneExist();
                    } else {
                        Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //SMSSDK.unregisterAllEventHandler();
    }

}
