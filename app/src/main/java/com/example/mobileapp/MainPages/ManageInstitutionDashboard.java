package com.example.mobileapp.MainPages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;

public class ManageInstitutionDashboard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_institution_dashboard, container, false);

        Button accMembers = view.findViewById(R.id.manageMembers);
        Button accSendDoc = view.findViewById(R.id.manageDocSend);
        Button accRecDoc = view.findViewById(R.id.manageDocReceive);

        accMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstitutionMembers institutionMembers = new InstitutionMembers();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, institutionMembers).commit();
            }
        });

        accSendDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstitutionDocumentsSend institutionDocumentsSend = new InstitutionDocumentsSend();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, institutionDocumentsSend).commit();

            }
        });

        accRecDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstitutionDocumentsReceived institutionDocumentsReceived = new InstitutionDocumentsReceived();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, institutionDocumentsReceived).commit();
            }
        });

        return view;
    };
}
