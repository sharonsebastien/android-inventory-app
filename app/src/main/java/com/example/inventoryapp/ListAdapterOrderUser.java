package com.example.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapterOrderUser extends BaseAdapter {
    private ArrayList<ModelProduct> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterOrderUser(Context aContext, ArrayList<ModelProduct> listData,Context mContext) {
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
        TextView uName, uQty,uCreatedAt,status;
    }
    public View getView(final int position, View v, ViewGroup vg) {
        ListAdapterOrderAdmin.ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listuserorder, null);
            holder = new ListAdapterOrderAdmin.ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.itemTitle);
            holder.uQty = (TextView) v.findViewById(R.id.qtyText);
            holder.uCreatedAt = (TextView) v.findViewById(R.id.dateText);
            holder.status = (TextView) v.findViewById(R.id.status);
            v.setTag(holder);
        } else {
            holder = (ListAdapterOrderAdmin.ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getName());
        holder.uQty.setText("QTY: " + String.valueOf(listData.get(position).getQuantity()));
        holder.uCreatedAt.setText("Created At: " +listData.get(position).getCreatedAt());
        holder.status.setText(listData.get(position).getStatus());


        if(listData.get(position).getStatus().equals("APPROVED")) {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if(listData.get(position).getStatus().equals("REJECTED")){
            holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
        } else {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        return v;
    }
}
