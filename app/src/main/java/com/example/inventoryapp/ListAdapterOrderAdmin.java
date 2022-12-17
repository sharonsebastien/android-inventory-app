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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapterOrderAdmin extends BaseAdapter {
    private ArrayList<ModelProduct> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterOrderAdmin(Context aContext, ArrayList<ModelProduct> listData,Context mContext) {
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
        TextView uName, uQty,uCreatedAt,uStockLeft,uUsername,status;
        Button approveBtn;
        Button rejectBtn;
    }
    public View getView(final int position, View v, ViewGroup vg) {
        ListAdapterOrderAdmin.ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listadminorder, null);
            holder = new ListAdapterOrderAdmin.ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.itemTitle);
            holder.uQty = (TextView) v.findViewById(R.id.qtyText);
            holder.uStockLeft = (TextView) v.findViewById(R.id.stockLeftText);
            holder.uCreatedAt = (TextView) v.findViewById(R.id.dateText);
            holder.uUsername = (TextView) v.findViewById(R.id.userName);
            holder.status = (TextView) v.findViewById(R.id.status);

            holder.approveBtn = (Button) v.findViewById(R.id.approveBtn);
            holder.rejectBtn = (Button) v.findViewById(R.id.rejectBtn);

            v.setTag(holder);
        } else {
            holder = (ListAdapterOrderAdmin.ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getName());
        holder.uQty.setText("QTY: " + String.valueOf(listData.get(position).getQuantity()));
        holder.uStockLeft.setText("Stock Left: " + listData.get(position).getStockLeft());
        holder.uCreatedAt.setText("Created At: " +listData.get(position).getCreatedAt());
        holder.uUsername.setText("User Name: "+ listData.get(position).getUserName());
        holder.status.setText(listData.get(position).getStatus());

        if(listData.get(position).getStatus().equals("PENDING")) {
            holder.approveBtn.setVisibility(LinearLayout.VISIBLE);
            holder.rejectBtn.setVisibility(LinearLayout.VISIBLE);
            holder.status.setVisibility(LinearLayout.INVISIBLE);
            holder.uStockLeft.setVisibility(LinearLayout.VISIBLE);
        } else {
            holder.approveBtn.setVisibility(LinearLayout.GONE);
            holder.rejectBtn.setVisibility(LinearLayout.GONE);
            holder.uStockLeft.setVisibility(LinearLayout.GONE);
            holder.status.setVisibility(LinearLayout.VISIBLE);
            if(listData.get(position).getStatus().equals("APPROVED")) {
                holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }

        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int leftQuantity = Integer.parseInt(listData.get(position).getStockLeft()) - listData.get(position).getQuantity();
                if(leftQuantity < 0) {
                    Toast.makeText(mContext, "Quantity is not enough to approve", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateOrderStatus(
                            listData.get(position).getOrderId(),
                            listData.get(position).getId(),
                            "APPROVED",
                            leftQuantity
                    );
                    AdminOrderListFragment.getInstance().showRecords();   
                }
            }
        });
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateOrderStatus(
                        listData.get(position).getOrderId(),
                        listData.get(position).getId(),
                        "REJECTED",
                        Integer.parseInt(listData.get(position).getStockLeft()) - listData.get(position).getQuantity()
                );
                AdminOrderListFragment.getInstance().showRecords();
            }
        });

        return v;
    }
}
