package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DataBaseHelper db;
    private static UserCartFragment instance = null;

    public UserCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserCartFragment newInstance(String param1, String param2) {
        UserCartFragment fragment = new UserCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static UserCartFragment getInstance() {
        return instance;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = new DataBaseHelper(getContext());
        instance = this;
    }

    ListView listView;
    Button placeOrder;
    LinearLayout notFound;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int userId = ((UserMain)getActivity()).getActiveUserId();
        View v =  inflater.inflate(R.layout.fragment_user_cart, container, false);
        listView = v.findViewById(R.id.listView);
        placeOrder = v.findViewById(R.id.placeOrder);
        notFound = v.findViewById(R.id.notFound);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.placeOrder(userId);
                Toast.makeText(getContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                ((UserMain)getActivity()).loadFragment(new UserOrderListFragment());
            }
        });
        showRecords();
        return v;
    }
    public void showRecords() {
        int userId = ((UserMain)getActivity()).getActiveUserId();
        int cartCount = db.getCartItemsCount(userId);
        if(cartCount == 0) {
            placeOrder.setVisibility(LinearLayout.GONE);
            notFound.setVisibility(LinearLayout.VISIBLE);
        } else {
            notFound.setVisibility(LinearLayout.GONE);
            placeOrder.setVisibility(LinearLayout.VISIBLE);
        }

        ArrayList<ModelProduct> cartList = db.getAllCartItems(userId);
        listView.setAdapter(new ListAdapterCartUser(getActivity().getApplicationContext() ,cartList, getActivity()));
    }
}