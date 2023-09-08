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

public class stationdetails extends Fragment {

    private Spinner stationSpinner;
    private TextView stationDetailsTextView;

    private String[] stations = {"Mathara", "Kaluthara South", "Colombo Fort", "Kurunagala", "Anuradhapura","Jaffna"};
    private String[] stationDetails = {
            "Only Rajarata Rajina is going through \nthis station",
            "Only Rajarata Rajina is going through \nthis station",
            "Main Station of Sri Lanka \nRajarata Rajina is passing this station.\nAnd all other trains starting Here",
            "All the Trains going through this Station",
            "Longest Station of Sri Lanka \nAll the trains stop here for loading and unloading",
            "End point of All the Northern line trains"
    };

    public stationdetails() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stationdetails, container, false);

        stationSpinner = rootView.findViewById(R.id.item_spinner);
        stationDetailsTextView = rootView.findViewById(R.id.item_details_textview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, stations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpinner.setAdapter(adapter);

        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStation = stations[position];
                String selectedStationDetails = stationDetails[position];
                stationDetailsTextView.setText(selectedStationDetails);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return rootView;
    }
}
