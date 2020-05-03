package com.example.mobileapp.Login;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSlider extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private MyAdapter adapter;
    private static String[] array_welcome_title = {"login", "slide1", "slide2", "slide3"};
    private List<Integer> layouts = new ArrayList<Integer>();
    private final String apiPathLogin = "http://fiscaldocumentseditest.azurewebsites.net/Account/Login.php";
    EditText etEmail, etPassword;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layout_dots = (LinearLayout) findViewById(R.id.layout_dots);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        layouts.add(R.layout.login_page);
        layouts.add(R.layout.customers_page);
        layouts.add(R.layout.features_page);
        layouts.add(R.layout.qa_page);

        adapter = new MyAdapter(this, new ArrayList<ViewPage>());
        final List<ViewPage> items = new ArrayList<>();
        for (int i = 0; i < array_welcome_title.length; i++) {
            ViewPage obj = new ViewPage();
            items.add(obj);
        }
        adapter.setItems(items);
        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapter.getCount(), 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                //  ((TextView) findViewById(R.id.title)).setText(items.get(pos).name);
                addBottomDots(layout_dots, adapter.getCount(), pos);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {

        ImageView[] dots = new ImageView[size];
        layout_dots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }



    private class MyAdapter extends PagerAdapter {

        private Activity act;
        private List<ViewPage> items;

        // constructor
        public MyAdapter(Activity activity, List<ViewPage> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public ViewPage getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<ViewPage> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ViewPage o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layouts.get(position), container, false);

            if (position == 0 ) {

                Button Signup = view.findViewById(R.id.signup);
                Button Login = view.findViewById(R.id.login);
                etEmail = view.findViewById(R.id.email);
                etPassword = view.findViewById(R.id.password);

                Signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilsSharedPrefences.saveSharedSetting(LoginSlider.this, "Login", "false");
                        UtilsSharedPrefences.SharedPrefesSAVE(getApplicationContext(), "");
                        Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(register);
                        finish();
                    }
                });

                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String email = etEmail.getText().toString();
                        String hashedPassword = etPassword.getText().toString();

                        if(! email.isEmpty() && ! hashedPassword.isEmpty()) {
                            doLogin(email, hashedPassword);

                        }
                        else {
                            if (email.isEmpty()) etEmail.setError("Insert an username!");
                            if (hashedPassword.isEmpty()) etPassword.setError("Insert an password!");
                        }

                    }
                });
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }

    }

    public void doLogin(final String email, final String password){


        // Pentru a face un request http get/post trebuie creat un StringRequest cu method type-ul (get/post), url-ul la care facem request, ...
        // ... un nou response listener de tip string care sa ne dea statusul in ce forma vrem si un response errorlistener care sa afiseze cum vrem noi erorile intampinate in timpul http requestului
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiPathLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String success = response;
                        String split[] = success.split("\"");
                        if (split.length > 5 && split[5].equals("SUCCESS")) {
                            UtilsSharedPrefences.saveSharedSetting(LoginSlider.this, "Login", "false");
                            UtilsSharedPrefences.SharedPrefesSAVE(getApplicationContext(), etEmail.getText().toString());
                            Intent ImLoggedIn = new Intent(getApplicationContext(), LoggedinActivity.class);
                            startActivity(ImLoggedIn);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String string = error.toString();
                        String[] split = string.split("\"");
                        if (split.length > 5 && split[5].equals("WRONG_PASSWORD")) {
                            Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                        }
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("hashedPassword", password);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

