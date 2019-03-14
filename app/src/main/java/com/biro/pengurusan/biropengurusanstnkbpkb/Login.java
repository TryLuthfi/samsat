package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.KEY_EMP_ID_KOTA;

public class Login extends AppCompatActivity {

    public static final String LOGIN_URL = "http://myindosnack.com/samsat/api/login.php";
//    public static final String LOGIN_URL = "http://samsat109.000webhostapp.com/login.php";
    public static final String KEY_EMAIL = "username";
    public static final String KEY_PASSWORD = "password";

    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_PENDING = "pending";
    String id_pelanggan, nama_pelanggan, status_pelanggan, notife;
    private String JSON_STRING;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Context context;
    private Button buttonLogin;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = Login.this;
        getJSON();

        pDialog = new ProgressDialog(context);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        String id_pelanggan = getIdPelanggan();
        if (id_pelanggan != "null"){
            gotoCourseActivity();
        }
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login() {
        //Getting values from edit texts
        final String username = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        Log.e("idpelangan",response.toString());
//                        if(status_pelanggan.equals("aktif")) {
                            if (response.contains(LOGIN_SUCCESS)) {
                                hideDialog();
                                String id_pelanggan = response.toString().split(";")[1];
                                Log.e("iniidpelanggan", id_pelanggan);
                                setPreference(id_pelanggan);
                                gotoCourseActivity();

                            } else if (response.contains(LOGIN_PENDING)){
                                Toast.makeText(Login.this, "Tunggu Akun Anda Diaktifkan", Toast.LENGTH_SHORT).show();
                                hideDialog();
                            } else {
                                hideDialog();
                                Toast.makeText(context, "Username dan Password Anda Salah", Toast.LENGTH_LONG).show();
                                Log.e("response", response.toString());
                            }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "Koneksi Anda bermasalah", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_EMAIL, username);
                params.put(KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_pelanggan = jo.getString(konfigurasi.id_pelanggan);
                nama_pelanggan = jo.getString(nama_pelanggan);
                status_pelanggan = jo.getString(konfigurasi.status_pelanggan);
                notife = jo.getString(notife);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_pelanggan, id_pelanggan);
                data.put(nama_pelanggan, nama_pelanggan);
                data.put(konfigurasi.status_pelanggan, status_pelanggan);
                data.put(notife, notife);

                list.add(data);

                if (status_pelanggan.equals("aktif") && notife.equals("belum")) {
                    Intent resultIntent = new Intent(this, Home.class);
                    resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent piResult = PendingIntent.getActivity(this,(int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);
                    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                    inboxStyle.setBigContentTitle("" + id_pelanggan);
                    inboxStyle.addLine(""+ status_pelanggan);
                    inboxStyle.addLine("" + status_pelanggan);
                    inboxStyle.setSummaryText("(*9898");

                    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
                    String broadcastIntent = addData(id_pelanggan);
//                    notife();
                    PendingIntent piResult2 = PendingIntent.getBroadcast(this,
                            0, new Intent(String.valueOf(broadcastIntent)), PendingIntent.FLAG_UPDATE_CURRENT);


                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(Login.this)
                                    .setSmallIcon(R.drawable.icon)
                                    .setContentTitle("" + nama_pelanggan)
                                    .setAutoCancel(false)
                                    .setContentText("Status Anda Sudah" + status_pelanggan)
                                    .setContentIntent(contentIntent)
                                    .addAction(R.drawable.icon, "Confirm ", piResult2);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, mBuilder.build());


                }
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_LNG);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private String addData(final String id_pelanggan) {

        class AddData extends AsyncTask<Void, Void, String> {

//            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(Home.this, "Mengambil Data...", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(Home.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KEY_EMP_ID_KOTA, id_pelanggan);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_NORMAL, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
        return id_pelanggan;
    }
    private void gotoCourseActivity() {
        Intent intent = new Intent(context, Home.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void register(View view) {
            Intent intent = new Intent (this, Register.class);
            startActivity(intent);
    }
    void setPreference(String id_pelanggan){
        SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("id_pelanggan", id_pelanggan);
        editor.apply();
    }
    private String getIdPelanggan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_pelanggan = preferences.getString("id_pelanggan", "null");
        return id_pelanggan;
    }
}
