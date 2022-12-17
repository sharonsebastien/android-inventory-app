package com.example.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterProductAdmin extends BaseAdapter {
    private ArrayList<ModelProduct> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterProductAdmin(Context aContext, ArrayList<ModelProduct> listData,Context mContext) {
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
        TextView uName,uStockLeft,productId;
        Button editBtn;
        Button deleteBtn;
    }
    public View getView(final int position, View v, ViewGroup vg) {
        ListAdapterProductAdmin.ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listproductadmin, null);
            holder = new ListAdapterProductAdmin.ViewHolder();

            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.productId = (TextView) v.findViewById(R.id.productId);
            holder.uStockLeft = (TextView) v.findViewById(R.id.stockLeft);


            holder.editBtn = (Button) v.findViewById(R.id.editBtn);
            holder.deleteBtn = (Button) v.findViewById(R.id.deleteBtn);

            v.setTag(holder);
        } else {
            holder = (ListAdapterProductAdmin.ViewHolder) v.getTag();
        }
        holder.productId.setText("Item Id:" + String.valueOf(listData.get(position).getId()));
        holder.uName.setText(listData.get(position).getName());
        holder.uStockLeft.setText(listData.get(position).getStockLeft());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdminMain)mContext).loadFragment(new AdminConfItemFragment(),"EDIT", String.valueOf(listData.get(position).getId()));
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelProduct product = listData.get(position);
                db.deleteProduct(product);
                AdminItemListFragment.getInstance().showRecords();
            }
        });

        return v;
    }
}
