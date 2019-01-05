package com.work.app.ztea.ui.activity.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.MyInvoiceListEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;

import cz.msebera.android.httpclient.Header;

/**
 * 新增发票
 */
public class AddInvoiceActivity extends BaseActivity {

    @ViewInject(R.id.tv_gongsi)
    TextView tv_gongsi;
    @ViewInject(R.id.tv_geren)
    TextView tv_geren;

    @ViewInject(R.id.et_title)
    EditText et_title;
    @ViewInject(R.id.et_tax_number)
    EditText et_tax_number;

    @ViewInject(R.id.layout_address)
    LinearLayout layout_address;
    @ViewInject(R.id.et_address)
    EditText et_address;
    @ViewInject(R.id.layout_tel)
    LinearLayout layout_tel;
    @ViewInject(R.id.et_tel)
    EditText et_tel;
    @ViewInject(R.id.layout_bank_name)
    LinearLayout layout_bank_name;
    @ViewInject(R.id.et_bank_name)
    EditText et_bank_name;
    @ViewInject(R.id.layout_bank_card)
    LinearLayout layout_bank_card;
    @ViewInject(R.id.et_bank_card)
    EditText et_bank_card;

    @ViewInject(R.id.et_mobile)
    EditText et_mobile;
    @ViewInject(R.id.et_email)
    EditText et_email;

    private String types = "0";

    private MyInvoiceListEntity.Invoice item;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_add_invoice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        item = (MyInvoiceListEntity.Invoice) getIntent().getSerializableExtra("bean");
        setTopTitle(item != null?"编辑发票":"新增发票");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initUI();
    }

    private void initUI() {
        if (item != null) {
            et_title.setText(item.getTitle());
            et_tax_number.setText(item.getTax_number());
            et_address.setText(item.getAddress());
            et_tel.setText(item.getTel());
            et_bank_name.setText(item.getBank_name());
            et_bank_card.setText(item.getBank_card());
            et_mobile.setText(item.getMobile());
            et_email.setText(item.getEmail());
            checkGS(TextUtils.equals(item.getTypes(),"0"));
        }
    }

    @OnClick({R.id.tv_gongsi,R.id.tv_geren,R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gongsi://公司
                checkGS(true);
                break;
            case R.id.tv_geren://个人
                checkGS(false);
                break;
            case R.id.tv_save://保存
                if (!isEdit()){
                    return;
                }
                editInvoice();
                break;
        }
    }

    private void editInvoice() {
        String title = et_title.getText().toString().trim();
        String tax_number = et_tax_number.getText().toString().trim();

        String address = et_address.getText().toString().trim();
        String tel = et_tel.getText().toString().trim();
        String bank_name = et_bank_name.getText().toString().trim();
        String bank_card = et_bank_card.getText().toString().trim();

        String mobile = et_mobile.getText().toString().trim();
        String email = et_email.getText().toString().trim();

        showProgressDialog();
        Api.editInvoice(UserService.getUserInfo().getToken(), item != null?"2":"1",item != null?item.getId():"",
                types,title,tax_number,
                address,bank_name,bank_card,tel,mobile,email, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        hideProgressDialog();
                        BaseEntity mBaseEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                        showToast(mBaseEntity.msg);
                        if (mBaseEntity.isOk()) {
                            setResult(RESULT_OK,new Intent());
                            finish();
                        }
                    }
                });
    }

    private boolean isEdit(){
        String name = et_title.getText().toString().trim();
        String phone = et_tax_number.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast(et_title.getHint().toString().trim());
            return false;
        }
        if (TextUtils.isEmpty(phone)){
            showToast(et_tax_number.getHint().toString().trim());
            return false;
        }
        return true;
    }

    private void checkGS(boolean isCheck){
        types = isCheck?"0":"1";
        layout_address.setVisibility(isCheck?View.VISIBLE:View.GONE);
        layout_tel.setVisibility(isCheck?View.VISIBLE:View.GONE);
        layout_bank_name.setVisibility(isCheck?View.VISIBLE:View.GONE);
        layout_bank_card.setVisibility(isCheck?View.VISIBLE:View.GONE);

        Drawable xz=getResources().getDrawable(R.drawable.mrdz);
        Drawable wxz=getResources().getDrawable(R.drawable.wxzg);
        xz.setBounds(0, 0, xz.getMinimumWidth(), xz.getMinimumHeight());
        wxz.setBounds(0, 0, wxz.getMinimumWidth(), wxz.getMinimumHeight());
        tv_gongsi.setCompoundDrawables(isCheck?xz:wxz, null, null, null);
        tv_geren.setCompoundDrawables(isCheck?wxz:xz, null, null, null);
    }
}
