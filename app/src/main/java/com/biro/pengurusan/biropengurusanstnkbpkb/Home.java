package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.KEY_EMP_ID_TRANSAKSI;

public class Home extends AppCompatActivity {

//    private String url = "http://samsat109.000webhostapp.com/notife.php";
    private String url = "http://myindosnack.com/samsat/api/notife.php";
    String tag_json_obj = "json_obj_req";

    private static ViewPager mPager;
    private static int currentPage = 0;
    private ImageView tentang;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    String id_transaksi,id_pelanggan , tgl_transaksi, tanggal,
            jenis_surat, no_plat, tanggal_selesai, status, notife, notifesurat, notifeproses, notifesiap;
    int bulan;

    private String JSON_STRING;

    private int[] myImageList = new int[]{R.drawable.samsat, R.drawable.samsat,
            R.drawable.samsat};
    Button button;
    String tahun, date, bulan_tampil2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tentang = (ImageView) findViewById(R.id.about);
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getJSON();
                handler.postDelayed(this, 10000);
            }
        }, 5000);

        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tahun = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        getJSON();
        init();
    }

    private void Clicked() {
        Intent resultIntent = new Intent(this, Home.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent piResult = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < myImageList.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
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
                tanggal = jo.getString(konfigurasi.tanggal);
                bulan = jo.getInt(konfigurasi.bulan);
                jenis_surat = jo.getString(konfigurasi.jenis_surat);
                no_plat = jo.getString(konfigurasi.no_plat);
                tanggal_selesai = jo.getString(konfigurasi.tanggal_selesai);
                status = jo.getString(konfigurasi.status);
                notife = jo.getString(konfigurasi.notife);
                notifesurat = jo.getString(konfigurasi.notifesurat);
                notifeproses = jo.getString(konfigurasi.notifeprosesk);
                notifesiap = jo.getString(konfigurasi.notifesiapK);


                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_pelanggan, id_pelanggan);

                list.add(data);

                int bulan_tampil = bulan - 1;

                String bulanasi = ""+tanggal+"-0" + bulan+"-" + tahun;

                if (bulan_tampil == 11 || bulan_tampil == 12){
                    bulan_tampil2 = ""+tanggal+"-" + bulan_tampil+"-" + tahun;
                } else {
                    bulan_tampil2 = ""+tanggal+"-0" + bulan_tampil+"-" + tahun;
                }

                if (bulan_tampil2.equals("00")){
                    bulan_tampil2 = "12";
                }

                String id_pelanggann = getIdPelanggan();

                if (id_pelanggann.equals(jo.getString("id_pelanggan"))) {
                    if (bulan_tampil2.equals(date) && notifesurat.equals("belum")){
                        Intent resultIntent = new Intent(this, Status.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent piResult = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);
                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("" + jenis_surat);
                        inboxStyle.addLine("" + status);
                        inboxStyle.addLine("" + status);
                        inboxStyle.setSummaryText("(*9898");

                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
                        String broadcastIntent = addData2(id_transaksi);
                        PendingIntent piResult2 = PendingIntent.getBroadcast(this,
                                0, new Intent(String.valueOf(broadcastIntent)), PendingIntent.FLAG_UPDATE_CURRENT);


                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(Home.this)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("" + no_plat)
                                        .setAutoCancel(false)
                                        .setContentText("" + jenis_surat + " Mati tanggal " + bulanasi)
                                        .setContentIntent(contentIntent)
                                        .addAction(R.drawable.icon, "Confirm ", piResult2);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, mBuilder.build());
                    } else if (status.equals("Proses") && notifeproses.equals("belum")){
                        Intent resultIntent = new Intent(this, Status.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent piResult = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);
                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("" + jenis_surat);
                        inboxStyle.addLine("" + status);
                        inboxStyle.addLine("" + status);
                        inboxStyle.setSummaryText("(*9898");

                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
                        String broadcastIntent = addData3(id_transaksi);
                        PendingIntent piResult2 = PendingIntent.getBroadcast(this,
                                0, new Intent(String.valueOf(broadcastIntent)), PendingIntent.FLAG_UPDATE_CURRENT);


                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(Home.this)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("" + no_plat)
                                        .setAutoCancel(false)
                                        .setContentText("" + jenis_surat + " prosessssssss " + bulanasi)
                                        .setContentIntent(contentIntent)
                                        .setStyle(inboxStyle)
                                        .addAction(R.drawable.icon, "Confirm ", piResult2);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, mBuilder.build());
                    } else if (status.equals("Siap Diambil") && notifesiap.equals("belum")){
                        Intent resultIntent = new Intent(this, Status.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent piResult = PendingIntent.getActivity(this, (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);
                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("" + jenis_surat);
                        inboxStyle.addLine("" + status);
                        inboxStyle.addLine("" + status);
                        inboxStyle.setSummaryText("(*9898");

                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
                        String broadcastIntent = addData4(id_transaksi);
                        PendingIntent piResult2 = PendingIntent.getBroadcast(this,
                                0, new Intent(String.valueOf(broadcastIntent)), PendingIntent.FLAG_UPDATE_CURRENT);


                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(Home.this)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("" + no_plat)
                                        .setAutoCancel(false)
                                        .setContentText("" + jenis_surat + " siappppppp " + bulanasi)
                                        .setContentIntent(contentIntent)
                                        .setStyle(inboxStyle)
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
    private String addData2(final String id_transaksi) {

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
                String res = rh.sendPostRequest(konfigurasi.URL_GET_NORMALSURAT_TANGGAL, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
        return id_transaksi;
    }
    private String addData3(final String id_transaksi) {

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
                String res = rh.sendPostRequest(konfigurasi.URL_GET_NORMALPROSES, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
        return id_transaksi;
    }
    private String addData4(final String id_transaksi) {

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
                String res = rh.sendPostRequest(konfigurasi.URL_GET_NORMALSIAP, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
        return id_transaksi;
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(Home.this, imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


    public void regis(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    private String getIdPelanggan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_pelanggan = preferences.getString("id_pelanggan", "null");
        return id_pelanggan;
    }

    public void kontak(View view) {
        Intent intent = new Intent(this, Kontak.class);
        startActivity(intent);
    }

    public void status(View view) {
        Intent intent = new Intent(this, Status.class);
        startActivity(intent);
    }

    public void tentang(View view) {
        Intent intent = new Intent(this, Tentang.class);
        startActivity(intent);
    }

    public void profil(View view) {
        Intent intent = new Intent (this, ProfilActivity.class);
        startActivity(intent);
    }
}
