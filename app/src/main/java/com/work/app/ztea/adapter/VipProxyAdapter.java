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
public class VipProxyAdapter extends RecyclerView.Adapter<VipProxyAdapter.ProxyHolder> {

    private Context mContext;
    private List<String> mStrings;
    private View.OnClickListener mOnClickListener;

    public VipProxyAdapter(Context context, List<String> strings, View.OnClickListener onClickListener) {
        mContext = context;
        mStrings = strings;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProxyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_proxy_order, parent, false);
        return new ProxyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProxyHolder holder, int position) {
        holder.mRlItemProxy.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mStrings.size() > 0 ? mStrings.size() : 0;
    }

    class ProxyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_head_proxy)
        ImageView mIvItemHeadProxy;
        @BindView(R.id.tv_item_title_proxy)
        TextView mTvItemTitleProxy;
        @BindView(R.id.iv_arrow_right)
        ImageView mIvArrowRight;
        @BindView(R.id.rl_item_proxy)
        RelativeLayout mRlItemProxy;

        public ProxyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
