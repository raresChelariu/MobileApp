package com.example.mobileapp.MainPages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;
import com.github.barteksc.pdfviewer.util.ArrayUtils;

public class InstitutionDashboard extends Fragment {

    String[] institutions = { "empty" };
    Button createInst;

    public InstitutionDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_institution_dashboard, container, false);

        createInst = view.findViewById(R.id.buttonCreateInstitution);
        createInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateInstitution createInstitution = new CreateInstitution();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, createInstitution).commit();
            }
        });


        if (institutions[0].compareTo("empty") == 0) {
            ListView institutionsView = view.findViewById(R.id.institutions);
            TextView emptyText = (TextView)view.findViewById(R.id.empty);
            emptyText.setText("You are not member of a institution.");
            institutionsView.setEmptyView(emptyText);
        }
        else {
            ListView institutionsView = view.findViewById(R.id.institutions);
            ArrayAdapter<String> institutionListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, institutions);

            institutionsView.setAdapter(institutionListAdapter);

            institutionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ManageInstitutionDashboard manageInst = new ManageInstitutionDashboard();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, manageInst).commit();
                }
            });
        }

        // Inflate the layout for this fragment
        return view;
    }
}
