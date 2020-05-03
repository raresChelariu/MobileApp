package com.example.mobileapp.MainPages;

        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.mobileapp.R;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

public class CreateInstitution extends Fragment {
    private static final String LOG_TAG = "CREATE INSTITUTION";

    private EditText etEmail, etPassword, etInstitutionName, etInstitutionAddressFirst, etInstitutionAddressSecond, etCIF;
    private Button btnCreateInstitution;

    private final String apiPathInstitutionCreation = "https://fiscaldocumentseditest.azurewebsites.net/Account/Create.php";
    private RequestQueue httpRequestQueue;

    public CreateInstitution() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //<editor-fold desc="Initialize UI components">
        View view = inflater.inflate(R.layout.fragment_institution_creation, container, false);
        etEmail = view.findViewById(R.id.ic_email);
        etPassword = view.findViewById(R.id.ic_password);
        etInstitutionName = view.findViewById(R.id.ic_instName);
        etInstitutionAddressFirst = view.findViewById(R.id.ic_address1);
        etInstitutionAddressSecond = view.findViewById(R.id.ic_address2);
        etCIF = view.findViewById(R.id.ic_cif);
        btnCreateInstitution = view.findViewById(R.id.ic_buttonCreate);
        //</editor-fold>
        httpRequestQueue = Volley.newRequestQueue(view.getContext());

        btnCreateInstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickHandlerButtonCreateInstitution();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    private void onClickHandlerButtonCreateInstitution() throws JSONException {
        if (!validFormInputs()) {
            Toast.makeText(getContext(), "Data not valid! Check your form inputs!", Toast.LENGTH_LONG).show();
            return;
        }
        //<editor-fold desc="GET FORM INPUT VALUES">
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String institutionName = etInstitutionName.getText().toString().trim();
        String addressFirst = etInstitutionAddressFirst.getText().toString().trim();
        String addressSecond = etInstitutionAddressSecond.getText().toString().trim();
        String CIF = etCIF.getText().toString();
        //</editor-fold>

        //<editor-fold desc="CREATE BODY OF HTTP POST REQUEST">
        JSONObject addressJSONObject = getAddressJSONFromInputs(addressFirst, addressSecond);
        Map<String, String> requestBodyMap = new HashMap<>();

        requestBodyMap.put("email", email);
        requestBodyMap.put("hashedPassword", password);
        requestBodyMap.put("institutionName", institutionName);
        requestBodyMap.put("institutionAddress", addressJSONObject.toString());
        requestBodyMap.put("institutionCIF", CIF);
        JSONObject requestBodyJSONObject = new JSONObject(requestBodyMap);
        Log.v(LOG_TAG, requestBodyJSONObject.toString());
        //</editor-fold>
        makePostRequestCreateInstitution(apiPathInstitutionCreation, requestBodyMap);
    }

    // method to validate all form inputs
    // should get called before using the values from any of the form inputs
    private boolean validFormInputs() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String institutionName = etInstitutionName.getText().toString();
        String addressFirst = etInstitutionAddressFirst.getText().toString();
        String addressSecond = etInstitutionAddressSecond.getText().toString();
        String CIF = etCIF.getText().toString();

        String[] allInputs = {email, password, institutionName, addressFirst, addressSecond, CIF};
        for (String currentInput : allInputs) {
            if (currentInput.matches("/s+")) // Regex pt cazul cand stringul e gol sau contine doar spatii
                return false; // formular invalid - toate inputurile trebuie sa fie completate
        }
        String[] stringsAddressFirst = addressFirst.split(",");
        String[] stringsAddressSecond = addressSecond.split(",");
        if (stringsAddressFirst.length != 4 ||
                stringsAddressSecond.length != 3)
            return false;

        //<editor-fold desc="Checking if NUMBER and APARTMENT are numbers">
        try {
            Integer.parseInt(stringsAddressSecond[0]);
            Integer.parseInt(stringsAddressSecond[2]);
            return true; // parsed successfully without throwing any parsing exception from string to int
        }catch (Exception e) {
            Log.v(LOG_TAG, "Error on converting input to number : " + e);
            return false; // parsed unsuccessfully - given strings can not be converted to int
        }
        //</editor-fold>
    }

    // method to build JSONObject of the institution's address
    // to further be sent to the API POST Request
    private JSONObject getAddressJSONFromInputs(String addressFirst, String addressSecond) throws JSONException {
        JSONObject resultJSON = new JSONObject();
        String[] addressFieldNames = {"Country", "Region", "City",
                "Street", "Number", "Building", "Apartment"};
        String[] addressFieldValues = new String[7];
        String[] firstFieldValues = addressFirst.split(",");
        String[] secondFieldValues = addressSecond.split(",");


        int i;
        for (i = 0; i < 4; i++)
            addressFieldValues[i] = firstFieldValues[i];
        for (int j = 0; j < 3; j++, i++)
            addressFieldValues[i] = secondFieldValues[j];
        for (i = 0; i < 7; i++)
            if (i != 4 && i != 6) // 5th and 7th field must be json-numbers NOT json-strings
                resultJSON.put(addressFieldNames[i], addressFieldValues[i]);
            else
                addressFieldValues[i] = addressFieldValues[i].trim();

        //<editor-fold desc="Inserting in the JSON object the numeric 5th and 7th field">
        int addressNumber = Integer.parseInt(addressFieldValues[4]);
        int addressApartment = Integer.parseInt(addressFieldValues[6]);
        resultJSON.put(addressFieldNames[4], addressNumber);
        resultJSON.put(addressFieldNames[6], addressApartment);
        //</editor-fold>

        return resultJSON;
    }

    private void makePostRequestCreateInstitution(String url, final Map<String, String> bodyParameters) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d(LOG_TAG, "RESPONSE : " + response);
                        Toast.makeText(getContext(),
                                "Request sent",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG, "VOLLEY ERROR : " + error.toString());
                        Toast.makeText(getContext(),
                                "Error on sending the request",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                return bodyParameters;
            }
        };
        httpRequestQueue.add(postRequest);
    }
}
