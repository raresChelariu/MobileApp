package com.example.mobileapp.MainPages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapp.R;

import java.util.HashMap;
import java.util.Map;


public class ModifyAccount extends Fragment {
    private EditText etOldPassword, etNewPassword, etEmail, etFirstName, etLastName;
    private Button etChange;

    private String sv_url = "http://fiscaldocumentseditest.azurewebsites.net/EndPoints/Login/EndPoint.php";
    RequestQueue requestQueue;

    public ModifyAccount(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_modify_account, container, false);
        etOldPassword = view.findViewById(R.id.old_password);
        etNewPassword = view.findViewById(R.id.new_password);
        etEmail = view.findViewById(R.id.modify_email);
        etFirstName = view.findViewById(R.id.new_firstname);
        etLastName = view.findViewById(R.id.new_lastname);
        etChange = view.findViewById(R.id.change_account);

        etChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, sv_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(view.getContext().getApplicationContext(),"Response: "+response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String, String> params= new HashMap<>();
                        params.put("email", etEmail.getText().toString());
                        params.put("currentHashedPassword", etOldPassword.getText().toString());
                        params.put("newHashedPassword", etNewPassword.getText().toString());
                        params.put("newFirstName", etFirstName.getText().toString());
                        params.put("newLastName", etLastName.getText().toString());
                        return params;
                    }
                };
                requestQueue= Volley.newRequestQueue(view.getContext());
                requestQueue.add(stringRequest);
            }
        });

        return view;
    }
}
