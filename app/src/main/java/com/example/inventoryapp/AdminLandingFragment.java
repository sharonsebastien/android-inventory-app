package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminLandingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminLandingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DataBaseHelper db;

    public AdminLandingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminLandingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminLandingFragment newInstance(String param1, String param2) {
        AdminLandingFragment fragment = new AdminLandingFragment();
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
        db = new DataBaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_admin_landing, container, false);

        TextView approved = v.findViewById(R.id.approved);
        TextView rejected = v.findViewById(R.id.rejected);
        TextView pending = v.findViewById(R.id.pending);

        ArrayList<Integer> arr = db.getAdminDashboard();
        pending.setText(String.valueOf(arr.get(0)));
        approved.setText(String.valueOf(arr.get(1)));
        rejected.setText(String.valueOf(arr.get(2)));

        return v;
    }
}