package com.work.app.ztea.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BankListEntity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.IdentifyEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MineHomeEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.work.app.ztea.utils.StringUtil.bankReplaceWithStar;
import static com.work.app.ztea.utils.StringUtil.idCardReplaceWithStar;

/**
 * 银行卡绑定
 */
public class BindBankActivity extends BaseActivity {

    @ViewInject(R.id.et_name)
    EditText et_name;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_bank_code)
    EditText et_bank_code;
    @ViewInject(R.id.tv_bank_name)
    TextView tv_bank_name;

    @ViewInject(R.id.tv_save)
    TextView tv_save;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_bind_bank;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("银行卡账号");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getUserInfo();
    }

    /**
     * 个人中心首页
     */
    private void getUserInfo(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.getMineInfo(userInfo.getToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","UserInfo = "+responseString);
                MineHomeEntity detailsEntity = AbGsonUtil.json2Bean(responseString, MineHomeEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    MineHomeEntity.Mine mine = detailsEntity.data;
                    if (!TextUtils.isEmpty(mine.getBank())){
                        et_name.setText(mine.getCardholder());
                        et_phone.setText(mine.getHoldermobile());
                        et_bank_code.setText(bankReplaceWithStar(mine.getCardno()));
                        tv_bank_name.setText(mine.getBank());
                        et_name.setEnabled(false);
                        et_phone.setEnabled(false);
                        et_bank_code.setEnabled(false);
                        tv_bank_name.setEnabled(false);
                        tv_save.setText("解除绑定");
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_bank_name,R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_name:
                bankList();
                break;
            case R.id.tv_save://保存
                String name = tv_save.getText().toString().trim();
                editCards(TextUtils.equals(name,"添加绑定")?"1":"2");
                break;
        }
    }

    /**
     * 银行卡
     */
    private void bankList(){
        showProgressDialog();
        Api.bankList(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
                showToast("请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("params", "银行卡"+responseString);
                hideProgressDialog();
                BankListEntity entity = AbGsonUtil.json2Bean(responseString, BankListEntity.class);
                if (entity.isOk()) {
                    stringList.clear();
                    for (BankListEntity.Bank bankBeam:entity.data) {
                        stringList.add(bankBeam.getTitle());
                    }
                    selectSex();
                } else {
                    showToast(entity.msg);
                }
            }
        });
    }

    private List<String> stringList = new ArrayList<>();

    private void selectSex(){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = stringList.get(options1);
                tv_bank_name.setText(tx);
            }
        }).build();
        pvOptions.setPicker(stringList);
        pvOptions.show();
    }

    /**
     * 变更银行卡
     */
    private void editCards(final String type){
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String bankCode = et_bank_code.getText().toString().trim();
        String bankName = tv_bank_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast(et_name.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(phone)){
            showToast(et_phone.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(bankCode)){
            showToast(et_bank_code.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(bankName)){
            showToast(tv_bank_name.getHint().toString());
            return;
        }
        String token = UserService.getUserInfo().getToken();
        showProgressDialog();
        Api.editCards(token,type,name,bankCode,phone,bankName,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
                showToast("请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("params", "绑定银行卡"+responseString);
                hideProgressDialog();
                BaseEntity entity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (entity.isOk()) {
                    EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
                    showToast(TextUtils.equals(type,"1")?"银行卡绑定成功!":"银行卡解绑成功!");
                    finish();
                } else {
                    showToast(entity.msg);
                }
            }
        });
    }
}
