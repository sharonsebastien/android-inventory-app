package com.example.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterUserAdmin extends BaseAdapter {
    private ArrayList<ModelUser> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterUserAdmin(Context aContext, ArrayList<ModelUser> listData,Context mContext) {
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
        TextView uName,uId,uPhoneNumber,uUserType;
        Button editBtn;
        Button deleteBtn;
    }
    public View getView(final int position, View v, ViewGroup vg) {
        ListAdapterUserAdmin.ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listuseradmin, null);
            holder = new ViewHolder();

            holder.uId = (TextView) v.findViewById(R.id.userId);
            holder.uName = (TextView) v.findViewById(R.id.nameField);
            holder.uPhoneNumber = (TextView) v.findViewById(R.id.phoneNumber);
            holder.uUserType = (TextView) v.findViewById(R.id.userType);


            holder.editBtn = (Button) v.findViewById(R.id.editBtn);
            holder.deleteBtn = (Button) v.findViewById(R.id.deleteBtn);

            v.setTag(holder);
        } else {
            holder = (ListAdapterUserAdmin.ViewHolder) v.getTag();
        }
        holder.uId.setText("User Id:" + String.valueOf(listData.get(position).getId()));
        holder.uUserType.setText(listData.get(position).getUserType());
        holder.uName.setText(listData.get(position).getName());
        holder.uPhoneNumber.setText(listData.get(position).getPhoneNumber());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdminMain)mContext).loadFragment(new AdminConfUserFragment(),"EDIT", String.valueOf(listData.get(position).getId()));
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelUser user = listData.get(position);
                db.deleteUser(user);
                AdminUserListFragment.getInstance().showRecords();
            }
        });

        return v;
    }
}
