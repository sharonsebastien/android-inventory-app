package com.example.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterCategoryAdmin extends BaseAdapter {
    private ArrayList<ModelCategory> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterCategoryAdmin(Context aContext, ArrayList<ModelCategory> listData,Context mContext) {
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
        TextView uName;
        Button editBtn;
        Button deleteBtn;
    }

    public View getView(final int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listcategoryadmin, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.textView);
            holder.editBtn = (Button) v.findViewById(R.id.editBtn);
            holder.deleteBtn = (Button) v.findViewById(R.id.deleteBtn);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getName());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdminMain)mContext).loadFragment(new AdminCategoryConfFragment(),"EDIT", String.valueOf(listData.get(position).getId()));
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCategory cat = listData.get(position);
                db.deleteRecord(cat);
                AdminCategoryList.getInstance().showRecords();
            }
        });

        return v;
    }
}
