package com.example.ppe_market;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nomET, prenomET, courrielET, usernameET, mot_de_passeET, confirmeMotDePasseET, telephoneET, date_naissanceET;
    private String nom, prenom, courriel, username, mot_de_passe, confirmeMotDePasse, telephone, date_naissance;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "http://192.168.1.20:8082/ppe_market/public/ws_inscription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        nomET = findViewById(R.id.nom);
        prenomET = findViewById(R.id.prenom);
        courrielET = findViewById(R.id.courriel);
        usernameET = findViewById(R.id.username);
        mot_de_passeET = findViewById(R.id.mot_de_passe);
        confirmeMotDePasseET = findViewById(R.id.confirmeMotDePasse);
        telephoneET = findViewById(R.id.telephone);
        date_naissanceET = findViewById(R.id.date_naissance);
        btn_regist = findViewById(R.id.btn_regist);




        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = nomET.getText().toString().trim();
                prenom = prenomET.getText().toString().trim();
                courriel = courrielET.getText().toString().trim();
                username = usernameET.getText().toString().trim();
                mot_de_passe = mot_de_passeET.getText().toString().trim();
                telephone = telephoneET.getText().toString().trim();
                date_naissance = date_naissanceET.getText().toString().trim();
                Regist();
            }
        });

    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);


        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("true")) {
                                Toast.makeText(RegisterActivity.this, "Register Success 1", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error 2" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error 3" + error.toString(), Toast.LENGTH_SHORT).show();
                        // error.printStackTrace();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("courriel", courriel);
                params.put("username", username);
                params.put("password", mot_de_passe);
                params.put("telephone", telephone);
                params.put("dateNaissance", date_naissance);
                return params;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

}
