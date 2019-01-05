package com.work.app.ztea.ui.activity.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vondear.rxtool.RxPhotoTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxui.view.dialog.RxDialogChooseImage;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.BaseInfoEntity;
import com.work.app.ztea.entity.JsonBean;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MineHomeEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;
import com.work.app.ztea.utils.DateUtils;
import com.work.app.ztea.utils.GetJsonDataUtil;
import com.work.app.ztea.utils.UserService;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.vondear.rxui.view.dialog.RxDialogChooseImage.LayoutType.TITLE;
import static com.work.app.ztea.utils.StringUtil.bankReplaceWithStar;
import static com.work.app.ztea.utils.StringUtil.idCardReplaceWithStar;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseActivity {

    @ViewInject(R.id.iv_head)
    CircleImageView iv_head;

    @ViewInject(R.id.et_name)
    EditText et_name;
    @ViewInject(R.id.tv_sex)
    TextView tv_sex;
    @ViewInject(R.id.et_age)
    EditText et_age;
    @ViewInject(R.id.tv_birthday)
    TextView tv_birthday;
    @ViewInject(R.id.tv_address)
    TextView tv_address;
    @ViewInject(R.id.et_dianpu)
    EditText et_dianpu;

    @ViewInject(R.id.tv_phone)
    TextView tv_phone;
    @ViewInject(R.id.tv_id_card)
    TextView tv_id_card;
    @ViewInject(R.id.tv_bank_code)
    TextView tv_bank_code;

    @ViewInject(R.id.tv_receipt_address)
    TextView tv_receipt_address;

    //选择性别
    private List<String> stringList = new ArrayList<>();
    //城市选择
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
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

                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };
    private String province = "";
    private String city = "";
    private String area = "";
    private File headImg;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("我的资料");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        stringList.add("男");
        stringList.add("女");
        getUserInfo();
    }

    /**
     * 个人中心首页
     */
    private void getUserInfo(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.getBaseInfo(userInfo.getToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","BaseInfo = "+responseString);
                BaseInfoEntity detailsEntity = AbGsonUtil.json2Bean(responseString, BaseInfoEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    BaseInfoEntity.BaseInfo baseInfo = detailsEntity.data;
                    Glide.with(mContext)
                            .load(baseInfo.getImage())
//                            .error(R.drawable.banner)
                            .into(iv_head);
                    et_name.setText(TextUtils.isEmpty(baseInfo.getName())?"":baseInfo.getName());
                    tv_sex.setText(TextUtils.isEmpty(baseInfo.getGender())?"":TextUtils.equals(baseInfo.getGender(),"1")?"男":"女");
                    et_age.setText(TextUtils.isEmpty(baseInfo.getAge())?"":baseInfo.getAge());
                    tv_birthday.setText(TextUtils.isEmpty(baseInfo.getBirthday())?"":DateUtils.formatTimeToStringYearMonthDay(new Date(Long.parseLong(baseInfo.getBirthday())*1000L)));
                    tv_address.setText(TextUtils.isEmpty(baseInfo.getProvince())?"":baseInfo.getProvince()+baseInfo.getCity()+baseInfo.getArea());
                    et_dianpu.setText(TextUtils.isEmpty(baseInfo.getStore_address())?"":baseInfo.getStore_address());
                    province = TextUtils.isEmpty(baseInfo.getProvince())?"":baseInfo.getProvince();
                    city = TextUtils.isEmpty(baseInfo.getCity())?"":baseInfo.getCity();
                    area = TextUtils.isEmpty(baseInfo.getArea())?"":baseInfo.getArea();
                    tv_phone.setText(TextUtils.isEmpty(baseInfo.getMobile())?"":baseInfo.getMobile());
                    tv_id_card.setText(TextUtils.isEmpty(baseInfo.getIdcard())?"":idCardReplaceWithStar(baseInfo.getIdcard()));
                    tv_bank_code.setText(TextUtils.isEmpty(baseInfo.getCardno())?"":bankReplaceWithStar(baseInfo.getCardno()));

                    tv_receipt_address.setText(TextUtils.isEmpty(baseInfo.getProvince())?"":baseInfo.getAddr_province()+baseInfo.getAddr_city()+baseInfo.getAddr_area());
                }
            }
        });
    }

    @OnClick({R.id.layout_head,R.id.layout_sex,R.id.layout_birthday,R.id.layout_address,R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_head://头像
                initDialogChooseImage();
                break;
            case R.id.layout_sex://性别
                selectSex();
                break;
            case R.id.layout_birthday://生日
                selectBirthDay();
                break;
            case R.id.layout_address://地址
                showPickerView();
                break;
            case R.id.tv_save://保存
                editUserInfo();
                break;
        }
    }

    /**
     * 编辑基础信息
     */
    private void editUserInfo(){
        String name = et_name.getText().toString().trim();
        String sex = tv_sex.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String birthday = tv_birthday.getText().toString().trim();
        String dianpu = et_dianpu.getText().toString().trim();
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.editBaseInfo(userInfo.getToken(),name,age,TextUtils.isEmpty(sex)?"":TextUtils.equals(sex,"男")?"1":"2",birthday,province,city,area,dianpu,headImg, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","BaseInfo = "+responseString);
                BaseEntity detailsEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    showToast("保存成功");
                    EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
                    finish();
                }else {
                    showToast(detailsEntity.msg);
                }
            }
        });
    }

    private static final int CAMERA_REQUEST_CODE = 2;

    private void initDialogChooseImage() {
        /**
         * 6.0系统动态权限申请需要
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                AlertDialog.Builder build = new AlertDialog.Builder(UserInfoActivity.this);
                build.setTitle("权限申请");
                build.setMessage("权限将影响app的运行，请手动打开应用权限");
                build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED ){
                            ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.CAMERA},
                                    CAMERA_REQUEST_CODE);
                        }
                    }
                });
                build.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);
            }
        } else {
            RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(this, TITLE);
            dialogChooseImage.show();
        }
    }

    private void selectSex(){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = stringList.get(options1);
                tv_sex.setText(tx);
            }
        }).build();
        pvOptions.setPicker(stringList);
        pvOptions.show();
    }

    private void selectBirthDay(){
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = DateUtils.formatTimeToStringYearMonthDay(date);
                tv_birthday.setText(time);
            }
        }).setType(new boolean[]{true,true,true,false,false,false}).build();
        pvTime.show();
    }

    //选择城市
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
                tv_address.setText(tx);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                RequestOptions options = new RequestOptions()
                        //禁止Glide硬盘缓存缓存
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(mContext).
                        load(RxPhotoTool.cropImageUri)
                        .apply(options).
                        thumbnail(0.5f).
                        into(iv_head);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    headImg = roadImageView(resultUri, iv_head);
                    RxSPTool.putContent(mContext, "AVATAR", resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.circle_elves_ball)
//                //异常占位图(当加载异常的时候出现的图片)
//                .error(R.drawable.circle_elves_ball)
                .transform(new CircleCrop())
                //禁止Glide硬盘缓存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(mContext).
                load(uri).
                apply(options).
                thumbnail(0.5f).
                into(imageView);

        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.red));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.red));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setCircleDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

}
