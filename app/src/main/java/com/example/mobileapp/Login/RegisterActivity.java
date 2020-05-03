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

public class RegisterActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    EditText Email, Password, ConfirmedPassword, FirstName, SecondName;
    String url = "http://fiscaldocumentseditest.azurewebsites.net/Account/Create.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText FirstName = findViewById(R.id.fname);
        final EditText SecondName =findViewById(R.id.lname);
        final EditText Password =findViewById(R.id.password);
        final EditText ConfirmedPassword = findViewById(R.id.cpassword);
        final EditText Email =findViewById(R.id.email);

        Button buttonSignup = findViewById(R.id.signup);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hashedPassword = Password.getText().toString();
                String email = Email.getText().toString();
                String firstName = FirstName.getText().toString();
                String lastName = SecondName.getText().toString();
                String confirmHashedPassword = ConfirmedPassword.getText().toString();

                if (!email.isEmpty() && !hashedPassword.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
                    if (hashedPassword.equals(confirmHashedPassword)) {
                        doRegister(email, hashedPassword, firstName, lastName);
                    }
                    else {
                        ConfirmedPassword.setError("Passwords doesn't match!");
                    }

                }
                else {
                    if (hashedPassword.isEmpty()) Password.setError("Insert a password!");
                    if (email.isEmpty()) Email.setError("Insert an email!");
                    if (firstName.isEmpty()) FirstName.setError("Insert your first name!");
                    if (lastName.isEmpty()) SecondName.setError("Insert your last name!");
                    if (confirmHashedPassword.isEmpty()) ConfirmedPassword.setError("Confirm your password!");
                }

            }
        });
    }

    private void doRegister(final String email, final String hashedPassword, final String firstName, final String lastName){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String success = response;
                String split[] = success.split("\"");
                if(split[3].equals("SUCCESS")) {
                    UtilsSharedPrefences.saveSharedSetting(RegisterActivity.this, "Login", "false");
                    UtilsSharedPrefences.SharedPrefesSAVE(getApplicationContext(), Email.getText().toString());
                    Intent justSignUp = new Intent(getApplicationContext(), LoginSlider.class);
                    startActivity(justSignUp);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Error "+ error.toString(), Toast.LENGTH_LONG );
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("hashedPassword", hashedPassword);
                params.put("firstName", firstName);
                params.put("lastName", lastName);

                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

}
