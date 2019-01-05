package com.work.app.ztea.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.work.app.ztea.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Created by huiliu on 2019/1/6.
 * @email liu594545591@126.com
 * @introduce
 */
public class OrderStatisAdapter extends RecyclerView.Adapter<OrderStatisAdapter.OrderStatisHolder> {
    private Context mContext;
    private List<String> mStrings;
    private View.OnClickListener mOnClickListener;

    public OrderStatisAdapter(Context context, List<String> strings, View.OnClickListener onClickListener) {
        mContext = context;
        mStrings = strings;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public OrderStatisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_order_statis, parent, false);
        return new OrderStatisHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatisHolder holder, int position) {

        holder.mRlOrderStatis.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mStrings.size() > 0 ? mStrings.size() : 0;
    }

    class OrderStatisHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_head_statis)
        ImageView mIvItemHeadStatis;
        @BindView(R.id.view_gray)
        View mViewGray;
        @BindView(R.id.rl_order_statis)
        RelativeLayout mRlOrderStatis;

        public OrderStatisHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
