package com.work.app.ztea.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class MyVipProxyAdapter extends RecyclerView.Adapter<MyVipProxyAdapter.MyVipHolder> {

    private Context mContext;
    private List<String> mStrings;
    private View.OnClickListener mOnClickListener;

    public MyVipProxyAdapter(Context context, List<String> strings, View.OnClickListener onClickListener) {
        mContext = context;
        mStrings = strings;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyVipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_my_vip_proxy, parent, false);
        return new MyVipHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVipHolder holder, int position) {
        holder.mTvEditItem.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mStrings.size() > 0 ? mStrings.size() : 0;
    }

    class MyVipHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_head_proxy)
        ImageView mIvItemHeadProxy;
        @BindView(R.id.tv_item_title_proxy)
        TextView mTvItemTitleProxy;
        @BindView(R.id.tv_item_title_content)
        TextView mTvItemTitleContent;
        @BindView(R.id.view_gray)
        View mViewGray;
        @BindView(R.id.tv_delete_item)
        TextView mTvDeleteItem;
        @BindView(R.id.tv_edit_item)
        TextView mTvEditItem;

        public MyVipHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
