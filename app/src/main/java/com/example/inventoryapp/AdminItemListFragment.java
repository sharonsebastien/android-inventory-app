package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminItemListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DataBaseHelper db;
    private static AdminItemListFragment instance = null;

    public AdminItemListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminItemListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminItemListFragment newInstance(String param1, String param2) {
        AdminItemListFragment fragment = new AdminItemListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static AdminItemListFragment getInstance() {
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
        View view =  inflater.inflate(R.layout.fragment_admin_item_list, container, false);
        Button btn = (Button) view.findViewById(R.id.add_item);
        listView = (ListView) view.findViewById(R.id.listView);
        notFound = view.findViewById(R.id.notFound);
        showRecords();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminMain) getActivity()).loadFragment(new AdminConfItemFragment());
            }
        });
        return view;
    }

    public void showRecords() {
        ArrayList<ModelProduct> itemList = db.getAllProducts();
        if(itemList.size() == 0) {
            notFound.setVisibility(LinearLayout.VISIBLE);
        } else {
            notFound.setVisibility(LinearLayout.GONE);
            listView.setAdapter(new ListAdapterProductAdmin(getActivity().getApplicationContext() ,itemList, getActivity()));
        }
    }
}