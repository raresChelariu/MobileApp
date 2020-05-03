package com.example.mobileapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;
    Button Login, Signup;

    String sv_url = "http://fiscaldocumentseditest.azurewebsites.net/EndPoints/Login/EndPoint.php";
    RequestQueue requestQueue;

    public void onCreate (Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_login);

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        Login = (Button)findViewById(R.id.login);
        Signup = (Button)findViewById(R.id.signup);



        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsSharedPrefences.saveSharedSetting(LoginActivity.this, "Login", "false");
                UtilsSharedPrefences.SharedPrefesSAVE(getApplicationContext(), "");
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = Email.getText().toString();
                String hashedPassword = Password.getText().toString();

                if(! email.isEmpty() && ! hashedPassword.isEmpty()) {
                    doLogin(email, hashedPassword);
                }
                else {
                    if (email.isEmpty()) Email.setError("Insert an username!");
                    if (hashedPassword.isEmpty()) Password.setError("Insert an password!");
                }

            }
        });

    }


    private void doLogin(final String email, final String password){


        // Pentru a face un request http get/post trebuie creat un StringRequest cu method type-ul (get/post), url-ul la care facem request, ...
        // ... un nou response listener de tip string care sa ne dea statusul in ce forma vrem si un response errorlistener care sa afiseze cum vrem noi erorile intampinate in timpul http requestului
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sv_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Aici eu pun raspunsul intr-o variabile pentru a afisa direct in aplicatie statusul (doar ptr teste)
                        String success = response;
                        //post.setText(response);
                        String split[] = success.split("\"");
                        if (split[3].equals("SUCCESS")) {
                            UtilsSharedPrefences.saveSharedSetting(LoginActivity.this, "Login", "false");
                            UtilsSharedPrefences.SharedPrefesSAVE(getApplicationContext(), Email.getText().toString());
                            Intent ImLoggedIn = new Intent(getApplicationContext(), LoggedinActivity.class);
                            startActivity(ImLoggedIn);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Same thing ca la onResponse
                        String string = error.toString();
                        String[] split = string.split("\"");
                        System.out.println(split[3]);
                        if (split[3].equals("WRONG_PASSWORD")) {
                            Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                        }
                        error.printStackTrace();
                    }
                })
        {
            // Mai sus a fost override la raspunsuri cu listeners, aici avem body de la StringRequest
            // Aici facem un Map de tip HashMap cu strings in care punem perechile: username - inputUsername, password - inputPassword
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("hashedPassword", password);

                return params;
            }
        };
        // Cream un nou request queue in contextul clasei actuale
        requestQueue = Volley.newRequestQueue(this);
        // Adaugam string requestul in queue
        requestQueue.add(stringRequest);

    }

}
