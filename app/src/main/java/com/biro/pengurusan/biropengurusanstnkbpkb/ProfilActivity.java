package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {
    TextView namaT, usernameT, emailT,passwordT, noktpT, alamatT, notelpT, textLoading;
    String id_pelangganS, no_ktpS,emailS, nama_pelangganS, usernameS, passwordS, alamat_pelangganS,
            telp_pelangganS, tgl_bergabungS, status_pelangganS;
//    private ProgressBar progressBar;
    private String JSON_STRING;
    ImageView image;
    RelativeLayout linearr;

    LinearLayout icon , bawah, linear, atas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        namaT = findViewById(R.id.nama);
        icon = findViewById(R.id.icon);
        bawah = findViewById(R.id.bawah);
        linear = findViewById(R.id.linear);
        emailT = findViewById(R.id.email);
        atas = findViewById(R.id.atas);
        usernameT = findViewById(R.id.username2);
        passwordT = findViewById(R.id.password2);
        noktpT = findViewById(R.id.noktp);
        alamatT = findViewById(R.id.alamat);
        notelpT = findViewById(R.id.notelp);

//        progressBar = findViewById(R.id.progressBar);
        textLoading = findViewById(R.id.textLoading);
        linearr = findViewById(R.id.linearr);

//        progressBar.setVisibility(View.VISIBLE);

        image = findViewById(R.id.progressBar);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
//        image.startAnimation(animation);
        linearr.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        textLoading.setVisibility(View.VISIBLE);
        icon.setVisibility(View.INVISIBLE);
        bawah.setVisibility(View.INVISIBLE);
        linear.setVisibility(View.INVISIBLE);
        atas.setVisibility(View.INVISIBLE);

        getJSON();

    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_pelangganS = jo.getString(konfigurasi.id_pelangganK);
                no_ktpS = jo.getString(konfigurasi.no_ktpK);
                emailS = jo.getString(konfigurasi.emailK);
                nama_pelangganS = jo.getString(konfigurasi.nama_pelangganK);
                usernameS = jo.getString(konfigurasi.usernameK);
                passwordS = jo.getString(konfigurasi.passwordK);
                alamat_pelangganS = jo.getString(konfigurasi.alamat_pelangganK);
                telp_pelangganS = jo.getString(konfigurasi.telp_pelangganK);
                tgl_bergabungS = jo.getString(konfigurasi.tgl_bergabungK);
                status_pelangganS = jo.getString(konfigurasi.status_pelangganK);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_pelangganK, id_pelangganS);
                data.put(konfigurasi.no_ktpK, no_ktpS);
                data.put(konfigurasi.emailK, emailS);
                data.put(konfigurasi.nama_pelangganK, nama_pelangganS);
                data.put(konfigurasi.usernameK, usernameS);
                data.put(konfigurasi.passwordK, passwordS);
                data.put(konfigurasi.alamat_pelangganK, alamat_pelangganS);
                data.put(konfigurasi.telp_pelangganK, telp_pelangganS);
                data.put(konfigurasi.tgl_bergabungK, tgl_bergabungS);
                data.put(konfigurasi.status_pelangganK, status_pelangganS);

                list.add(data);

                String id_pelanggan = getIdPelanggan();
                if (id_pelanggan.equals(jo.getString("id_pelanggan"))) {
                    namaT.setText("" + nama_pelangganS);
                    usernameT.setText("" + usernameS);
                    emailT.setText(""+emailS);
                    passwordT.setText("" + passwordS);
                    noktpT.setText("" + no_ktpS);
                    alamatT.setText("" + alamat_pelangganS);
                    notelpT.setText("" + telp_pelangganS);
                }

//                progressBar.setVisibility(View.INVISIBLE);
                linearr.setVisibility(View.INVISIBLE);
                image.setVisibility(View.INVISIBLE);
                textLoading.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.VISIBLE);
                bawah.setVisibility(View.VISIBLE);
                linear.setVisibility(View.VISIBLE);
                atas.setVisibility(View.VISIBLE);


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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_PROFIL);
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
    private String getIdPelanggan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_pelanggan = preferences.getString("id_pelanggan", "null");
        return id_pelanggan;
    }

    public void change(View view) {
        Intent intent = new Intent (this, ChangeProfile.class);
        startActivity(intent);
    }
}
