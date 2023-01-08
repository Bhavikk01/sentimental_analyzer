package com.example.sentimental_analyzer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ML_MODEL {


    public String prediction;

    Context context;
    private String getMeMax(Double positive, Double negative, Double neutral) {

        if (Objects.equals(positive, negative) && Objects.equals(negative, neutral)) {
            return "neu";
        }

        if (positive > negative && positive > neutral) {
            return "happy";
        }

        if (negative > positive && negative > neutral) {
            return "sad";
        }
        if (neutral > positive && neutral > negative) {
            return "neu";
        }
        return "okays";

    }


    public ML_MODEL(Context context) {
        this.context = context;
    }

    public void predict(String data) {

//        Log.d("PREDICT_UPDATE", "aur kya bhdve" + data);


        String url = "http://192.168.1.6:5000/predict";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Double positive = jsonObject.getDouble("pos");
                            Double negative = jsonObject.getDouble("neg");
                            Double neutral = jsonObject.getDouble("neu");

                            prediction = getMeMax(positive, negative, neutral);

                            Log.d("PREDICT_UPDATE", prediction);


//                            Object d = jsonObject.get("neg");

//                           Long data = jsonObject.getLong("neg");

//                            textView.setText(""+data);


                        } catch (Exception e) {

//                            textView.setText(e.getLocalizedMessage());
                            Log.d("PREDICT_UPDATE", e.getLocalizedMessage());

                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name", data);

                return map;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);



    }





    }



