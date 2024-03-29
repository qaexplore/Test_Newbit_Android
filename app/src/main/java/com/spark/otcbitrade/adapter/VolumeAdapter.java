package com.spark.otcbitrade.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spark.otcbitrade.R;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.entity.VolumeInfo;
import com.spark.otcbitrade.utils.DateUtils;
import com.spark.otcbitrade.utils.GlobalConstant;
import com.spark.otcbitrade.utils.MathUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * 成交adapter
 * Created by daiyy on 2018/1/29.
 */

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<VolumeInfo> objList;
    private int baseCoinScale;
    private int coinScale;

    public void setBaseCoinScale(int baseCoinScale, int coinScale) {
        this.baseCoinScale = baseCoinScale;
        this.coinScale = coinScale;
    }

    public VolumeAdapter(Context context, ArrayList<VolumeInfo> objList) {
        this.context = context;
        this.objList = objList;
    }

    public void setObjList(ArrayList<VolumeInfo> objList) {
        this.objList = objList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_volume, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VolumeInfo volumeInfo = objList.get(position);
        if (volumeInfo.getTime() == -1) {
            holder.tvTime.setText("-- --");
        } else {
            holder.tvTime.setText(DateUtils.getFormatTime("HH:mm:ss", new Date(volumeInfo.getTime())));
        }
        int direct = volumeInfo.getSide();
        if (direct == -1) {
            holder.tvDirect.setText("-- --");
        } else {
            holder.tvDirect.setText(volumeInfo.getSide() == GlobalConstant.INT_SELL ? context.getString(R.string.text_sale_out) : context.getString(R.string.text_buy_in));
            holder.tvDirect.setTextColor(volumeInfo.getSide() == GlobalConstant.INT_SELL ? ContextCompat.getColor(MyApplication.getApp(), R.color.font_kline_depth_sell) : ContextCompat.getColor(MyApplication.getApp(), R.color.font_kline_depth_buy));
        }
        holder.tvPrice.setText(volumeInfo.getPrice() == -1 ? "-- --" : MathUtils.subZeroAndDot(MathUtils.getRundNumber(volumeInfo.getPrice(), baseCoinScale, null)));
        holder.tvNumber.setText(volumeInfo.getAmount() == -1 ? "-- --" : MathUtils.subZeroAndDot(MathUtils.getRundNumber(volumeInfo.getAmount(),
                coinScale, null)) + "");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvDirect;
        private TextView tvPrice;
        private TextView tvNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDirect = itemView.findViewById(R.id.tvDirect);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvNumber = itemView.findViewById(R.id.tvNumber);
        }


    }
}
