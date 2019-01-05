package com.work.app.ztea.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.library.base.BaseSwipeBackCompatActivity;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.netstatus.AbNetUtils;
import com.work.app.ztea.R;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.NetBroadcastReceiver;
import com.work.app.ztea.utils.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author:      Qiyunfeng
 * Date:        16/7/3 上午1:27
 * Description: TimelyRain
 */
public abstract class BaseActivity extends BaseSwipeBackCompatActivity implements EasyPermissions.PermissionCallbacks, NetBroadcastReceiver.NetEvevt {

    protected TextView tv_title;
    protected ImageButton im_back;
    protected ImageButton im_right;
    protected ImageButton ivRight1;
    protected ImageButton ivRight2;
    public TextView tv_right;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void setTopTitle(String s) {
        if (tv_title != null) {
            tv_title.setText(s);
        } else {
            tv_title = (TextView) findViewById(R.id.tv_title);
            if (tv_title != null) {
                tv_title.setText(s);
            }
        }
        if (tv_title != null) {
            tv_title.setVisibility(View.VISIBLE);
        }
    }

    public void setRightTitle(String s, View.OnClickListener listener) {
        setRightTitle(s);
        tv_right.setOnClickListener(listener);
    }

    public void setRightTitle(String s) {
        if (tv_right != null) {
            tv_right.setText(s);
        } else {
            tv_right = (TextView) findViewById(R.id.tv_right);
            if (tv_right != null) {
                tv_right.setText(s);
            }
        }
        tv_right.setVisibility(View.VISIBLE);
    }

    public void setVisibleLeft(boolean visible) {
        if (im_back == null) {
            im_back = (ImageButton) findViewById(R.id.im_back);
        }
        if (visible) {
            im_back.setVisibility(View.VISIBLE);
            im_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventCenter(Api.CLOSE_TRANSMIS, "0"));
                    finish();
                }
            });
        } else {
            im_back.setVisibility(View.GONE);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(AbNetUtils.NetType type) {
    }

    @Override
    protected void onNetworkDisConnected() {
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).setTitle("权限申请").setRationale("此应用程序可能无法正常工作，没有请求的权限。打开应用程序设置屏幕修改应用程序权限。").build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void showImageByGlideVertical(String url, final ImageView im) {
        if (mContext != null) {
//            Glide.with(mContext)
//                    .load(url)
////                    .asBitmap()
////                    .animate(R.anim.glide_zoom_in)
//                    //.error(R.drawable.ic_moren_vertical)
//                    //.placeholder(R.drawable.ic_moren_vertical)
//                    .thumbnail(0.5f)
//                    .into(new BitmapImageViewTarget(im) {
//                        @Override
//                        protected void setResource(Bitmap resource) {
//
//                        }
//                    });
        }
    }

    public void showImageByGlideHorizontal(String url, final ImageView im) {
        if (mContext != null) {
//            Glide.with(mContext)
//                    .load(url)
//                    .asBitmap()
//                    .animate(R.anim.glide_zoom_in)
//                    //.error(R.drawable.ic_moren_horizontal)
//                    //.placeholder(R.drawable.ic_moren_horizontal)
//                    .thumbnail(0.5f)
//                    .into(new BitmapImageViewTarget(im) {
//                        @Override
//                        protected void setResource(Bitmap resource) {
//                            im.setImageBitmap(resource);
//                        }
//                    });
        }
    }

    private NetBroadcastReceiver netBroadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        evevt = this;
        inspectNet();
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
            registerReceiver(netBroadcastReceiver, filter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netBroadcastReceiver);
    }


    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;

//    @Override
//    protected void onCreate(Bundle arg0) {
//        // TODO Auto-generated method stub
//        super.onCreate(arg0);
//
//    }


    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetworkUtil.getNetWorkState(BaseActivity.this);
        return isNetConnect();

        // if (netMobile == 1) {
        // System.out.println("inspectNet：连接wifi");
        // } else if (netMobile == 0) {
        // System.out.println("inspectNet:连接移动数据");
        // } else if (netMobile == -1) {
        // System.out.println("inspectNet:当前没有网络");
        //
        // }
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }


    public void startAct(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
