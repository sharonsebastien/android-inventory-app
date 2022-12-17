package com.example.inventoryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserItemList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserItemList extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DataBaseHelper db;
    private static UserItemList instance = null;
    String[] categories;
    int[] catIndex;
    int defaultCatId;


    public UserItemList() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserItemList newInstance(String param1, String param2) {
        UserItemList fragment = new UserItemList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static UserItemList getInstance() {
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
    LinearLayout bottomLayout;
    TextView cartCountView;
    Button goToCart;
    LinearLayout notFound;
    Spinner spinElement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_item_list, container, false);
        int count=0;
        ArrayList<ModelCategory> arrList = db.getAllCategory();
        categories = new String[arrList.size()];
        catIndex = new int[arrList.size()];

        for (ModelCategory cat : arrList) {
            categories[count] = cat.getName();
            catIndex[count++] = cat.getId();
            defaultCatId = cat.getId();
        }

        listView = v.findViewById(R.id.listView);
        spinElement = v.findViewById(R.id.spinner);
        bottomLayout = v.findViewById(R.id.bottomLayout);
        cartCountView = v.findViewById(R.id.cartCount);
        goToCart = v.findViewById(R.id.goToCart);
        notFound = v.findViewById(R.id.notFound);

        ArrayAdapter arrayAdap = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinElement.setAdapter(arrayAdap);
        spinElement.setOnItemSelectedListener(this);

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UserMain)getActivity()).loadFragment(new UserCartFragment());
            }
        });

        return v;
    }

    public int dpToPx(int dp) {
        float scale =  getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        showRecords(catIndex[i]);
        defaultCatId = catIndex[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showRecords(int...arr) {
        int userId = ((UserMain)getActivity()).getActiveUserId();
        int categoryId = defaultCatId;
        if(arr.length > 0) {
            categoryId = arr[0];
        }

        int cartCount = db.getCartItemsCount(userId);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
        if(cartCount > 0) {
            cartCountView.setText( cartCount + " ITEM");
            bottomLayout.setVisibility(LinearLayout.VISIBLE);
            layoutParams.setMargins(0,dpToPx(60),0,dpToPx(80));
        } else {
            bottomLayout.setVisibility(LinearLayout.INVISIBLE);
            layoutParams.setMargins(0,dpToPx(60),0,dpToPx(0));
        }

        listView.setLayoutParams(layoutParams);

        ArrayList<ModelProduct> itemList = db.getAllProductForUser(userId,categoryId);
        if(itemList.size() == 0) {
            notFound.setVisibility(LinearLayout.VISIBLE);
            listView.setAdapter(new ListAdapterProductUser(getActivity().getApplicationContext() ,itemList, getActivity()));
        } else {
            notFound.setVisibility(LinearLayout.GONE);
            listView.setAdapter(new ListAdapterProductUser(getActivity().getApplicationContext() ,itemList, getActivity()));
        }
    }
}