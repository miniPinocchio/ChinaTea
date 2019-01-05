package com.work.app.ztea.ui.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.JsonBean;
import com.work.app.ztea.entity.MyAddressListEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.GetJsonDataUtil;
import com.work.app.ztea.utils.UserService;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * 新增或编辑地址
 */
public class AddAddressActivity extends BaseActivity {

    @ViewInject(R.id.et_name)
    EditText et_name;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.tv_city)
    TextView tv_city;
    @ViewInject(R.id.et_address)
    EditText et_address;
    @ViewInject(R.id.iv_is_default)
    ImageView iv_is_default;

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    private String province;
    private String city;
    private String area;

    private String isDefault = "0";

    private MyAddressListEntity.Address item;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        item = (MyAddressListEntity.Address) getIntent().getSerializableExtra("bean");
        setTopTitle(item != null?"编辑地址":"新增地址");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initUI();
    }

    private void initUI() {
        if (item != null) {
            et_name.setText(item.getName());
            et_phone.setText(item.getMobile());
            et_address.setText(item.getAddress());
            tv_city.setText(item.getProvince()+item.getCity()+item.getArea());
            iv_is_default.setImageResource(TextUtils.equals(item.getIs_default(),"1")?R.drawable.mrdz:R.drawable.wxzg);
            isDefault = item.getIs_default();
        }
    }

    @OnClick({R.id.tv_city,R.id.layout_set_default,R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_city://选择城市
                showPickerView();
                break;
            case R.id.layout_set_default://设置默认地址
                isDefault  = TextUtils.equals(isDefault,"1")?"0":"1";
                iv_is_default.setImageResource(TextUtils.equals(isDefault,"1")?R.drawable.mrdz:R.drawable.wxzg);
                break;
            case R.id.tv_save://保存
                if (!isEdit()){
                    return;
                }
                editAddress();
                break;
        }
    }

    private boolean isEdit(){
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String city = tv_city.getText().toString().trim();
        String address = et_address.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast(et_name.getHint().toString().trim());
            return false;
        }
        if (TextUtils.isEmpty(phone)){
            showToast(et_phone.getHint().toString().trim());
            return false;
        }
        if (TextUtils.isEmpty(city)){
            showToast(tv_city.getHint().toString().trim());
            return false;
        }
        if (TextUtils.isEmpty(address)){
            showToast(et_address.getHint().toString().trim());
            return false;
        }
        return true;
    }

    private void editAddress(){
        showProgressDialog();
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String address = et_address.getText().toString().trim();

        Api.editAddr(UserService.getUserInfo().getToken(), item == null?"1":"2",item == null?"":item.getId(),
                name,phone,isDefault,
                province,city,area,address, new TextHttpResponseHandler() {
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

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);
                tv_city.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

}
