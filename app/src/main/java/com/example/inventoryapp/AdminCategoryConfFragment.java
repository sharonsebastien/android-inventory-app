package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminCategoryConfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminCategoryConfFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DataBaseHelper db;



    public AdminCategoryConfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminCategoryConfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminCategoryConfFragment newInstance(String param1, String param2) {
        AdminCategoryConfFragment fragment = new AdminCategoryConfFragment();
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


    String type = "NONE";
    int id = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null) {
            type = getArguments().getString("type");
            id = Integer.valueOf(getArguments().getString("id"));
        }

        View view =  inflater.inflate(R.layout.fragment_admin_category_conf, container, false);
        Button btn = (Button) view.findViewById(R.id.add_category);
        TextView nameField = (TextView) view.findViewById(R.id.nameField);
        TextView headText = (TextView) view.findViewById(R.id.headText);

        if(type.equals("EDIT")) {
            headText.setText("Edit Category");
            btn.setText("Edit Category");
            ModelCategory cat = db.getCategory(id);
            nameField.setText(cat.getName());
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("EDIT")) {
                    db.updateCategory(new ModelCategory(id,nameField.getText().toString()));
                } else {
                    db.addCategory(new ModelCategory(nameField.getText().toString()));
                }
                AdminCategoryList.getInstance().showRecords();
                ((AdminMain) getActivity()).loadFragment(new AdminCategoryList());
            }
        });
        return view;
    }


}