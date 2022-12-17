package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminUserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminUserListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static AdminUserListFragment instance = null;
    DataBaseHelper db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminUserListFragment() {
        // Required empty public constructor
    }

    public static AdminUserListFragment getInstance() {
        return instance;
    }
    public static AdminUserListFragment newInstance(String param1, String param2) {
        AdminUserListFragment fragment = new AdminUserListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = new DataBaseHelper(getContext().getApplicationContext());
        instance = this;
    }
    ListView listView;
    LinearLayout notFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_user_list, container, false);
        Button btn = (Button) view.findViewById(R.id.add_user);

        listView = (ListView) view.findViewById(R.id.listView);
        notFound = view.findViewById(R.id.notFound);

        showRecords();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdminMain) getActivity()).loadFragment(new AdminConfUserFragment());
            }
        });
        return view;
    }
    public void showRecords() {
        ArrayList<ModelUser> userList = db.getAllUsers();
        if(userList.size() == 0) {
            notFound.setVisibility(LinearLayout.VISIBLE);
        } else {
            notFound.setVisibility(LinearLayout.GONE);
            listView.setAdapter(new ListAdapterUserAdmin(getActivity().getApplicationContext() ,userList, getActivity()));
        }
    }
}