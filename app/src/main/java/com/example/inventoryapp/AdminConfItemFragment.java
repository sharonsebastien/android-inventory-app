package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminConfItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminConfItemFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DataBaseHelper db;

    public AdminConfItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminConfItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminConfItemFragment newInstance(String param1, String param2) {
        AdminConfItemFragment fragment = new AdminConfItemFragment();
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
    String[] categories;
    int arrIds[];
    int selectedCategory;
    int defaultCatIndex=0;
    int counter = 0;

    ModelProduct product;
    String type = "NONE";
    int id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<ModelCategory> catList = db.getAllCategory();

        if(getArguments() != null) {
            type = getArguments().getString("type");
            id = Integer.valueOf(getArguments().getString("id"));
            product = db.getProduct(id);
        }

        categories = new String[catList.size()];
        arrIds = new int[catList.size()];

        for (int i = 0; i < catList.size(); i++) {
            categories[i] = catList.get(i).getName();
            arrIds[i] = catList.get(i).getId();

            if(type.equals("EDIT") && Integer.valueOf(product.getCategory()) == catList.get(i).getId()) {
                defaultCatIndex = i;
            }
        }

        View view = inflater.inflate(R.layout.fragment_admin_conf_item, container, false);

        TextView headText = view.findViewById(R.id.headText);
        Spinner spinElement = view.findViewById(R.id.spinner);
        EditText nameField = view.findViewById(R.id.nameField);
        EditText stockLeft = view.findViewById(R.id.stockLeft);
        Button btn = view.findViewById(R.id.add_item);

        ArrayAdapter arrayAdap = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinElement.setAdapter(arrayAdap);
        spinElement.setOnItemSelectedListener(this);


        if(type.equals("EDIT")) {
            headText.setText("Edit Product");
            btn.setText("Edit Product");

            nameField.setText(product.getName());
            stockLeft.setText(product.getStockLeft());
            spinElement.setSelection(defaultCatIndex);
            selectedCategory = defaultCatIndex;

            Log.d("DTAG", "onCreateView: " + defaultCatIndex + "-" + arrIds[defaultCatIndex] + "-" + categories[defaultCatIndex]);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("EDIT")) {
                    db.updateProduct(new ModelProduct(
                            id,
                            nameField.getText().toString(),
                            stockLeft.getText().toString(),
                            String.valueOf(selectedCategory)
                    ));
                } else {
                    db.addProduct(new ModelProduct(
                            nameField.getText().toString(),
                            stockLeft.getText().toString(),
                            String.valueOf(selectedCategory)
                    ));
                }
                AdminItemListFragment.getInstance().showRecords();
                ((AdminMain) getActivity()).loadFragment(new AdminItemListFragment());
            }
        });

        return view;
    }
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        if(type.equals("NONE") || counter > 0) {
            Log.d("DTAG", "onItemSelected: " + arrIds[i]);
            selectedCategory = arrIds[i];
        }
        counter++;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}