package com.work.app.ztea.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.work.app.ztea.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Created by huiliu on 2019/1/6.
 * @email liu594545591@126.com
 * @introduce
 */
public class CustomProxyAdapter extends RecyclerView.Adapter<CustomProxyAdapter.CustomHolder> {

    private Context mContext;
    private List<String> mStrings;
    private View.OnClickListener mOnClickListener;

    public CustomProxyAdapter(Context context, List<String> strings, View.OnClickListener onClickListener) {
        mContext = context;
        mStrings = strings;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_custom_proxy, parent, false);

        return new CustomHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.mRlItemCustom.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mStrings.size() > 0 ? mStrings.size() : 0;
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_custom_proxy_head)
        ImageView mIvCustomProxyHead;
        @BindView(R.id.tv_custom_proxy_title)
        TextView mTvCustomProxyTitle;
        @BindView(R.id.tv_custom_proxy_name)
        TextView mTvCustomProxyName;
        @BindView(R.id.rl_item_custom)
        RelativeLayout mRlItemCustom;

        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
