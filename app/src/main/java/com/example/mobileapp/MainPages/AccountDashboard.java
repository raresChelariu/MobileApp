package com.example.mobileapp.MainPages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.Login.LoginSlider;
import com.example.mobileapp.Login.UtilsSharedPrefences;
import com.example.mobileapp.R;

public class AccountDashboard extends Fragment {

    private Activity view;
    Button modify;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_dashboard, container, false);
        modify = view.findViewById(R.id.buttonModify);

        CheckUser();

        SharedPreferences SP = getActivity().getApplicationContext().getSharedPreferences("NAME", 0);

        TextView user = view.findViewById(R.id.insertUsername);
        user.setText(SP.getString("Name", null));

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyAccount modifyAccount= new ModifyAccount();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, modifyAccount).commit();
            }
        });

        return view;
    }

    public void CheckUser () {

        Boolean Check = Boolean.valueOf(UtilsSharedPrefences.readSharedSetting(getActivity().getApplicationContext(), "Login", "true"));

        Intent introIntent = new Intent(getActivity().getApplicationContext(), LoginSlider.class);
        introIntent.putExtra("Login", Check);

        if (Check) {
            startActivity(introIntent);
        }
    }
}
