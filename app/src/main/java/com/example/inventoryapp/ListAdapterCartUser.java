package com.example.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapterCartUser extends  BaseAdapter{
    private ArrayList<ModelProduct> listData;
    private LayoutInflater layoutInflater;
    DataBaseHelper db;
    private Context mContext;

    public ListAdapterCartUser(Context aContext, ArrayList<ModelProduct> listData,Context mContext) {
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
        TextView itemName,qtyText;
        Button deleteBtn, incBtn, decBtn;
    }
    public View getView(final int position, View v, ViewGroup vg) {
        ListAdapterCartUser.ViewHolder holder;

        if(v == null) {
            v = layoutInflater.inflate(R.layout.listusercart, null);
            holder = new ListAdapterCartUser.ViewHolder();

            holder.itemName = (TextView) v.findViewById(R.id.itemName);
            holder.qtyText = (TextView) v.findViewById(R.id.qtyText);

            holder.incBtn = (Button) v.findViewById(R.id.incBtn);
            holder.decBtn = (Button) v.findViewById(R.id.decBtn);
            holder.deleteBtn = (Button) v.findViewById(R.id.deleteBtn);

            v.setTag(holder);
        } else {
            holder = (ListAdapterCartUser.ViewHolder) v.getTag();
        }
        holder.itemName.setText(listData.get(position).getName());
        holder.qtyText.setText(String.valueOf(listData.get(position).getQuantity()));

        holder.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(holder.qtyText.getText().toString()) + 1;
                int cartId = listData.get(position).getCartId();

                if(quantity > Integer.valueOf(listData.get(position).getStockLeft())) {
                    Toast.makeText(mContext, "Quantity Not Available", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateQuantity(
                        cartId,
                        quantity
                    );
                    holder.qtyText.setText(String.valueOf(quantity));
                }
            }
        });

        holder.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.valueOf(holder.qtyText.getText().toString()) - 1;
                int cartId = listData.get(position).getCartId();

                if(quantity == 0) {
                    Toast.makeText(mContext, "Click On Delete Button to delete the item", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateQuantity(
                            cartId,
                            quantity
                    );
                    holder.qtyText.setText(String.valueOf(quantity));
                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteCartItem( listData.get(position).getCartId());
                UserCartFragment.getInstance().showRecords();
            }
        });

        return v;
    }
}
