package com.example.slr2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.slr2.R;


public class Traindetails extends Fragment {


    private Spinner trainSpinner;
    private TextView trainDetailsTextView;

    private String[] train = {"Yaal Dewi", "Rajarata Rajina", "Night Mail", "Intercity Express", "Udaya Dewi"};
    private String[] trainDetails = {
            "Third class and Second class only" +
                    "\nColombo Fort to Jaffna",
            "Third class and Second class only" +
                    "\nBeliaththa To Vavniya",
            "Third class and Second class only" +
                    "\nColombo Fort To Anuradhapura",
            "First Class Only" +
                    "\nJaffna to Colombo Fort",
            "First class,Second class,Third class Available" +
                    "\nJaffna to colombo fort"
    };

    public Traindetails() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_traindetails, container, false);

        trainSpinner = rootView.findViewById(R.id.train_spinner);
        trainDetailsTextView = rootView.findViewById(R.id.train_details_textview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, train);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainSpinner.setAdapter(adapter);

        trainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStation = train[position];
                String selectedStationDetails = trainDetails[position];
                trainDetailsTextView.setText(selectedStationDetails);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return rootView;
    }
}