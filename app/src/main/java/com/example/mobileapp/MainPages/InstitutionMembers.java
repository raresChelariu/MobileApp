package com.example.mobileapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;

public class InstitutionMembers extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institution_members, container, false);

        String[] membersList={
                "membrul 1","membrul 2","membrul 3","membrul 4","membrul 5"
        };
        ListView listView=(ListView) view.findViewById(R.id.institutionMembersList);
        ArrayAdapter<String> listViewAdapter= new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                membersList
        );
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ManageInstitutionMember manageMem = new ManageInstitutionMember();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, manageMem).commit();
            }
        });

        return view;
    }
}
