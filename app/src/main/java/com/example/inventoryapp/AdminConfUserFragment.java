package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminConfUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminConfUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DataBaseHelper db;

    public AdminConfUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminConfUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminConfUserFragment newInstance(String param1, String param2) {
        AdminConfUserFragment fragment = new AdminConfUserFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_conf_user, container, false);
        Button btn = (Button) view.findViewById(R.id.add_user);

        TextView headText = (TextView) view.findViewById(R.id.headText);

        EditText nameF = (EditText) view.findViewById(R.id.nameField);
        EditText phoneF = (EditText) view.findViewById(R.id.phoneNumberField);
        EditText passwordF = (EditText) view.findViewById(R.id.passwordField);

        if(type.equals("EDIT")) {
            headText.setText("Edit User");
            btn.setText("Edit User");
            ModelUser user = db.getUser(id);
            nameF.setText(user.getName());
            phoneF.setText(user.getPhoneNumber());
            passwordF.setText(user.getPassword());
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("EDIT")) {
                    db.updateUser(new ModelUser(
                            id,
                            nameF.getText().toString(),
                            phoneF.getText().toString(),
                            passwordF.getText().toString(),
                            "USER"
                    ));
                } else {
                    db.addUser(new ModelUser(
                            nameF.getText().toString(),
                            phoneF.getText().toString(),
                            passwordF.getText().toString(),
                            "USER"
                    ));
                }
                AdminUserListFragment.getInstance().showRecords();
                ((AdminMain) getActivity()).loadFragment(new AdminUserListFragment());
            }
        });
        return view;
    }
}