package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.alamat_pelangganK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.emailK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.id_pelangganK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.nama_pelangganK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.no_ktpK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.passwordK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.telp_pelangganK;
import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.usernameK;

public class ChangeProfile extends AppCompatActivity {
    EditText nama,username, password, ktp,alamat,telp, email;
    RelativeLayout linear, linear2;
    Button update;
    ImageView a;
    TextView b;
    JSONObject jo;
    private String JSON_STRING;
    String id_pelangganS, no_ktpS, nama_pelangganS, usernameS, passwordS, alamat_pelangganS,
            telp_pelangganS, tgl_bergabungS, status_pelangganS, emailS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        ktp = findViewById(R.id.ktp);
        alamat = findViewById(R.id.alamat);
        telp = findViewById(R.id.telp);

        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        linear = findViewById(R.id.linear);
        linear2 = findViewById(R.id.linear2);

        linear2.setVisibility(View.VISIBLE);
        linear.setVisibility(View.INVISIBLE);

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });


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
                jo = result.getJSONObject(i);
                id_pelangganS = jo.getString(konfigurasi.id_pelangganK);
                no_ktpS = jo.getString(konfigurasi.no_ktpK);
                nama_pelangganS = jo.getString(konfigurasi.nama_pelangganK);
                usernameS = jo.getString(konfigurasi.usernameK);
                emailS =jo.getString(konfigurasi.emailK);
                passwordS = jo.getString(konfigurasi.passwordK);
                alamat_pelangganS = jo.getString(konfigurasi.alamat_pelangganK);
                telp_pelangganS = jo.getString(konfigurasi.telp_pelangganK);
                tgl_bergabungS = jo.getString(konfigurasi.tgl_bergabungK);
                status_pelangganS = jo.getString(konfigurasi.status_pelangganK);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_pelangganK, id_pelangganS);
                data.put(konfigurasi.no_ktpK, no_ktpS);
                data.put(konfigurasi.nama_pelangganK, nama_pelangganS);
                data.put(konfigurasi.emailK,emailS);
                data.put(konfigurasi.usernameK, usernameS);
                data.put(konfigurasi.passwordK, passwordS);
                data.put(konfigurasi.alamat_pelangganK, alamat_pelangganS);
                data.put(konfigurasi.telp_pelangganK, telp_pelangganS);
                data.put(konfigurasi.tgl_bergabungK, tgl_bergabungS);
                data.put(konfigurasi.status_pelangganK, status_pelangganS);

                list.add(data);

                String id_pelanggan = getIdPelanggan();
                if (id_pelanggan.equals(jo.getString("id_pelanggan"))) {
                    nama.setText("" + nama_pelangganS);
                    email.setText(""+emailS);
                    username.setText("" + usernameS);
                    password.setText("" + passwordS);
                    ktp.setText("" + no_ktpS);
                    alamat.setText("" + alamat_pelangganS);
                    telp.setText("" + telp_pelangganS);
                }

                linear2.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.VISIBLE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progressDialog = ProgressDialog.show(ChangeProfile.this, "Mengambil Data ","sek", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressDialog.dismiss();
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




    private void addData() {

        class AddData extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChangeProfile.this, "Mengganti Data...", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                startActivity(new Intent(ChangeProfile.this, ProfilActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ));
                finish();
                Toast.makeText(ChangeProfile.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                final String id_pelanggan = getIdPelanggan();
                final String nama_pelanggan = nama.getText().toString().trim();
                final String emaill = email.getText().toString().trim();
                final String user = username.getText().toString().trim();
                final String pass = password.getText().toString().trim();
                final String no_ktp = ktp.getText().toString().trim();
                final String alamt = alamat.getText().toString().trim();
                final String telpon = telp.getText().toString().trim();

                HashMap<String, String> params = new HashMap<>();
                params.put(id_pelangganK, id_pelanggan);
                params.put(nama_pelangganK, nama_pelanggan);
                params.put(emailK, emaill);
                params.put(usernameK, user);
                params.put(passwordK, pass);
                params.put(no_ktpK, no_ktp);
                params.put(alamat_pelangganK, alamt);
                params.put(telp_pelangganK, telpon);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_UPDATE, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
    }

    private String getIdPelanggan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_pelanggan = preferences.getString("id_pelanggan", "null");
        return id_pelanggan;
    }
}
