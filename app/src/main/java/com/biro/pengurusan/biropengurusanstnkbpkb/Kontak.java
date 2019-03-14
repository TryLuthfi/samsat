package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Kontak extends AppCompatActivity {
    TextView notelp, alamat, email;
    String notelpS, alamatS, emailS;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak);

        notelp = findViewById(R.id.notelp);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);

        getJSON();
    }

    public void back(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){

    }

    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                notelpS = jo.getString(konfigurasi.notelpK);
                alamatS = jo.getString(konfigurasi.alamatK);
                emailS = jo.getString(konfigurasi.emailK);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.notelpK, notelpS);
                data.put(konfigurasi.alamatK, alamatS);
                data.put(konfigurasi.emailK, emailS);

                list.add(data);
                notelp.setText("" + notelpS);
                alamat.setText("" + alamatS);
                email.setText("" + emailS);

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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_KONTAK);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}