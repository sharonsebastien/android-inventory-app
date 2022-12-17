package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrderListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DataBaseHelper db;
    private static UserOrderListFragment instance = null;

    public UserOrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserOrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserOrderListFragment newInstance(String param1, String param2) {
        UserOrderListFragment fragment = new UserOrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static UserOrderListFragment getInstance() {
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
    LinearLayout notFound;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_order_list, container, false);
        listView = v.findViewById(R.id.listView);
        notFound = v.findViewById(R.id.notFound);

        showRecords();
        return v;
    }
    public void showRecords() {
        int userId = ((UserMain)getActivity()).getActiveUserId();
        ArrayList<ModelProduct> itemList = db.getAllUserOrders(userId);
        if(itemList.size() == 0) {
            notFound.setVisibility(LinearLayout.VISIBLE);
        } else {
            notFound.setVisibility(LinearLayout.GONE);
            listView.setAdapter(new ListAdapterOrderUser(getActivity().getApplicationContext(), itemList, getActivity()));
        }
    }
}