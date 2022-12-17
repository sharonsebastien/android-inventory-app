package com.example.inventoryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DataBaseHelper db;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
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
        SharedPreferences sh = this.getActivity().getSharedPreferences("userId", Context.MODE_PRIVATE);
        int userId = sh.getInt("userId",-1);

        ModelUser user = db.getUser(userId);

        View v = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        TextView userIdV = v.findViewById(R.id.userId);
        EditText nameV = v.findViewById(R.id.nameField);
        EditText phoneNumberV = v.findViewById(R.id.phoneNumberField);
        EditText passwordV = v.findViewById(R.id.passwordField);

        userIdV.setText("ADMIN ID:" + String.valueOf(user.getId()));
        nameV.setText(user.getName());
        phoneNumberV.setText(user.getPhoneNumber());
        passwordV.setText(user.getPassword());

        Button btnUpdate = v.findViewById(R.id.updateBtn);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateUser(new ModelUser(
                    user.getId(),
                    nameV.getText().toString(),
                    phoneNumberV.getText().toString(),
                    passwordV.getText().toString(),
                    "ADMIN"
                ));

                Toast.makeText(getContext(), "Admin Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}