package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.KEY_EMP_ID_TRANSAKSI;

public class Status extends AppCompatActivity {

//    private static final String URL_PRODUCTS = "http://samsat109.000webhostapp.com/insert2.php";
    private static final String URL_PRODUCTS = "http://myindosnack.com/samsat/api/insert2.php";
    List<Product> productList;
    String id_transaksi,id_pelanggan , tgl_transaksi, tanggall,
            jenis_surat, no_plat, tanggal_selesai, statuss, notife, notifesurat;
    RecyclerView recyclerView;
    private TextView textLoading;
    GifImageView progressBar;
    RelativeLayout linear;
    LinearLayout a,b,c;
    private String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        linear = findViewById(R.id.linear);

        productList = new ArrayList<>();
        textLoading = findViewById(R.id.textLoading);
        progressBar = findViewById(R.id.progressBar);
        linear.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        textLoading.setVisibility(View.VISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);


        loadProducts();
        getJSON();

    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                String id_pelanggan = getIdPelanggan();
                                Log.e("ini 1 "+id_pelanggan,"ini 2 "+product.getString("id_pelanggan"));
                                if (id_pelanggan.equals(product.getString("id_pelanggan"))){
                                    productList.add(new Product(
                                            product.getString("id_transaksi"),
                                            product.getString("id_pelanggan"),
                                            product.getString("tgl_transaksi"),
                                            product.getString("titipan"),
                                            product.getString("ket_titipan"),
                                            product.getString("rincian_surat"),
                                            product.getString("jenis_pengurusan"),
                                            product.getString("no_plat"),
                                            product.getString("tanggal_selesai"),
                                            product.getString("status"),
                                            product.getString("ket_status"),
                                            product.getString("statusBpkb")
                                    ));
                                }
                            }

                            ProductAdapter adapter = new ProductAdapter(Status.this, productList);

                            if (adapter != null){
                                recyclerView.setAdapter(adapter);
                                linear.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                textLoading.setVisibility(View.INVISIBLE);
                                a.setVisibility(View.VISIBLE);
                                b.setVisibility(View.VISIBLE);
                                c.setVisibility(View.VISIBLE);
                            }else {
                                Toast.makeText(Status.this, "null", Toast.LENGTH_SHORT).show();
                            }

//                            loading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void back(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_transaksi = jo.getString(konfigurasi.id_transaksiK);
                id_pelanggan = jo.getString(konfigurasi.id_pelanggan);
                tgl_transaksi = jo.getString(konfigurasi.tgl_transaksi);
                tanggall = jo.getString(konfigurasi.tanggal);
                jenis_surat = jo.getString(konfigurasi.jenis_surat);
                no_plat = jo.getString(konfigurasi.no_plat);
                tanggal_selesai = jo.getString(konfigurasi.tanggal_selesai);
                statuss = jo.getString(konfigurasi.status);
                notife = jo.getString(konfigurasi.notife);
                notifesurat = jo.getString(konfigurasi.notifesurat);


                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_pelanggan, id_pelanggan);

                list.add(data);

                String id_pelanggann = getIdPelanggan();

                if (id_pelanggann.equals(jo.getString("id_pelanggan"))) {
                    if (statuss.equals("Selesai") && notife.equals("belum")) {
                        Intent resultIntent = new Intent(this, Status.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent piResult = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);
                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("" + jenis_surat);
                        inboxStyle.addLine("" + statuss);
                        inboxStyle.addLine("" + statuss);
                        inboxStyle.setSummaryText("(*9898");

                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
                        String broadcastIntent = addData(id_transaksi);
                        PendingIntent piResult2 = PendingIntent.getBroadcast(this,
                                0, new Intent(String.valueOf(broadcastIntent)), PendingIntent.FLAG_UPDATE_CURRENT);


                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(Status.this)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("" + id_transaksi)
                                        .setAutoCancel(false)
                                        .setContentText("Proses Anda Sudah " + statuss)
                                        .setContentIntent(contentIntent)
                                        .addAction(R.drawable.icon, "Confirm ", piResult2);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, mBuilder.build());
                    }
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_LNG2);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private String addData(final String id_transaksi) {

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
                params.put(KEY_EMP_ID_TRANSAKSI, id_transaksi);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_NORMALSURAT, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
        return id_transaksi;
    }

    private String getIdPelanggan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_pelanggan = preferences.getString("id_pelanggan", "null");
        return id_pelanggan;
    }
}
