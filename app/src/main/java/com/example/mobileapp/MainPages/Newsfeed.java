package com.example.mobileapp.MainPages;

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

public class Newsfeed extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        CheckUser();

        SharedPreferences SP = getActivity().getApplicationContext().getSharedPreferences("NAME", 0);

        TextView text = (TextView)v.findViewById(R.id.myName);
        Button logout = (Button)v.findViewById(R.id.logout);

        text.setText(SP.getString("Name", null));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsSharedPrefences.saveSharedSetting(getActivity().getApplicationContext(), "Login", "true");
                UtilsSharedPrefences.SharedPrefesSAVE(getActivity().getApplicationContext(), "");
                Intent LogOut = new Intent(getActivity().getApplicationContext(), LoginSlider.class);
                startActivity(LogOut);
                getActivity().finish();
            }
        });
        return v;
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
