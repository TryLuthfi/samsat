package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Tentang extends AppCompatActivity {

    TextView logout, tentangkami, carapengurusan;
    String tentangkamiS, caraPengurusanS;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        tentangkami = findViewById(R.id.tentangkami);
        carapengurusan = findViewById(R.id.carapengurusan);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                startActivity(new Intent(Tentang.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                editor.clear();
                editor.commit();
                finish();
            }
        });

        getJSON();
    }

    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                tentangkamiS = jo.getString(konfigurasi.tentangkamiK);
                caraPengurusanS = jo.getString(konfigurasi.carapengurusanK);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.tentangkamiK, tentangkamiS);
                data.put(konfigurasi.carapengurusanK, caraPengurusanS);

                list.add(data);
                tentangkami.setText("" + tentangkamiS);
                carapengurusan.setText("" + caraPengurusanS);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_TENTANG);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void back(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}
