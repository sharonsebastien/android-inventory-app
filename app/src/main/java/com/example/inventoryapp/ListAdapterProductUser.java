package com.example.inventoryapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterProductUser extends BaseAdapter {
    private ArrayList<ModelProduct> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterProductUser(Context aContext, ArrayList<ModelProduct> listData,Context mContext) {
        this.listData= listData;
        layoutInflater = LayoutInflater.from(aContext);
        db = new DataBaseHelper(layoutInflater.getContext());
        this.mContext = mContext;
    }
    public int getCount() {
        return listData.size();
    }
    public Object getItem(int position) {
        return listData.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView uName,uStockLeft;
        Button addToCart;
    }
    public View getView(final int position, View v, ViewGroup vg) {
        ListAdapterProductUser.ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listproductuser, null);
            holder = new ListAdapterProductUser.ViewHolder();

            holder.uName = (TextView) v.findViewById(R.id.itemTitle);
            holder.uStockLeft = (TextView) v.findViewById(R.id.stockStatus);
            holder.addToCart = (Button) v.findViewById(R.id.addToCart);

            v.setTag(holder);
        } else {
            holder = (ListAdapterProductUser.ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getName());
        if(Integer.valueOf(listData.get(position).getStockLeft()) > 0) {
            holder.uStockLeft.setText("IN STOCK");
            holder.uStockLeft.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.uStockLeft.setText("OUT OF STOCK");
            holder.uStockLeft.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        if(listData.get(position).getIsAddedToCart()) {
            holder.addToCart.setText("Added");
            holder.addToCart.setEnabled(false);
            holder.addToCart.getBackground().setAlpha((int) 50);
        }
        if(Integer.valueOf(listData.get(position).getStockLeft()) <= 0) {
            holder.addToCart.setVisibility(LinearLayout.INVISIBLE);
        }

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = ((UserMain)mContext).getActiveUserId();
                db.addToCart(listData.get(position).getId(), userId);
                UserItemList.getInstance().showRecords();
            }
        });

        return v;
    }
}
