package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import static com.biro.pengurusan.biropengurusanstnkbpkb.konfigurasi.KEY_EMP_ID_TRANSAKSI;

public class StatusPreview extends AppCompatActivity {

    private String mPostKeyPlat = null;
    private String mPostKeyJenisPengurusan = null;
    private String mPostKeyTGLTransksi = null;
    private String mPostKeyKetStatus = null;
    private String mPostKeyTitipan = null;
    private String mPostKeyTotalBiaya = null;
    private String mPostKeyStatus = null;
    private String mPostKeyID = null;
    private String mPostKeyRincianSurat = null;
    private String mPostKeyStatusBpkb = null;
    private TextView plat, pengurusan, tanggal, status, ket_status, rincian_surat, titipan, ket_titipan, hasill;
    private ImageView gambar;
    private Button oke;

    private Button sudahDiambil, ambilBpkb;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_preview);

        sudahDiambil = findViewById(R.id.custom_dialog_btn_gone);
        ambilBpkb = findViewById(R.id.ambilBpkb);
        plat = findViewById(R.id.plat);
        titipan = findViewById(R.id.titipan);
        hasill = findViewById(R.id.hasilnya);
        ket_titipan = findViewById(R.id.ket_titipan);
        pengurusan = findViewById(R.id.pengurusan);
        tanggal = findViewById(R.id.tanggal);
        status = findViewById(R.id.status);
        ket_status = findViewById(R.id.ket_status);
        rincian_surat = findViewById(R.id.rincian_surat);

        mPostKeyPlat = getIntent().getExtras().getString("plat");
        mPostKeyTitipan = getIntent().getExtras().getString("titipan");
        mPostKeyTotalBiaya = getIntent().getExtras().getString("ket_titipan");
        mPostKeyJenisPengurusan = getIntent().getExtras().getString("pengurusan");
        mPostKeyTGLTransksi = getIntent().getExtras().getString("tanggal");
        mPostKeyStatus = getIntent().getExtras().getString("status");
        mPostKeyKetStatus = getIntent().getExtras().getString("ket_status");
        mPostKeyID = getIntent().getExtras().getString("ID");
        mPostKeyRincianSurat = getIntent().getExtras().getString("rincian_surat");
        mPostKeyStatusBpkb = getIntent().getExtras().getString("statusBpkb");

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        int anutitipan = Integer.parseInt(mPostKeyTitipan);
        final int anuTotalBiaya = Integer.parseInt(mPostKeyTotalBiaya);

        final int hasil = anutitipan - anuTotalBiaya;

        plat.setText("" + mPostKeyPlat);
        pengurusan.setText("" + mPostKeyJenisPengurusan);
        tanggal.setText("" + mPostKeyTGLTransksi);
        titipan.setText(""+ formatRupiah.format((double)anutitipan));
        ket_titipan.setText(""+ formatRupiah.format((double)anuTotalBiaya));
        hasill.setText(""+ formatRupiah.format((double)hasil));
        rincian_surat.setText("" + mPostKeyRincianSurat);
        status.setText("" + mPostKeyStatus);
        ket_status.setText("" + mPostKeyKetStatus);

        anu();


        CekStatus();
        CekBpkb();

        sudahDiambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasil < 0 ) {
                    anu();
                } else if (hasil > 0){
                    addData(mPostKeyID);
                } else if (hasil == 0){
                    addData(mPostKeyID);
                }
            }
        });

        ambilBpkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData2(mPostKeyID);
            }
        });
    }

    private void anu() {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        int anutitipan = Integer.parseInt(mPostKeyTitipan);
        final int anuket_titipan = Integer.parseInt(mPostKeyTotalBiaya);
        final int hasil = anutitipan - anuket_titipan;
        if (hasil < 0 ){
            hasill.setTextColor(this.getResources().getColor(R.color.redText));
            final Dialog alertDialog = new Dialog(StatusPreview.this);
            alertDialog.setCancelable(true);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setContentView(R.layout.custom_dialog);
            Button oke = alertDialog.findViewById(R.id.oke);
            ImageView gambar = alertDialog.findViewById(R.id.gambar);
            TextView bawahegambar = alertDialog.findViewById(R.id.bawahegambar);
            TextView text = alertDialog.findViewById(R.id.jjj);


            gambar.setImageDrawable(getResources().getDrawable(R.drawable.seru));
            oke.setBackgroundColor(getResources().getColor(R.color.red));
            text.setText("Biaya pekerjaan anda kurang "+formatRupiah.format((double)hasil)+ " harap segera melunasi");
            bawahegambar.setText("Kekurangan Biaya");
            oke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        } else if (hasil > 0){
            hasill.setTextColor(this.getResources().getColor(R.color.greenText));
            final Dialog alertDialog = new Dialog(StatusPreview.this);
            alertDialog.setCancelable(true);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setContentView(R.layout.custom_dialog);
            Button oke = alertDialog.findViewById(R.id.oke);
            ImageView gambar = alertDialog.findViewById(R.id.gambar);
            TextView bawahegambar = alertDialog.findViewById(R.id.bawahegambar);
            TextView text = alertDialog.findViewById(R.id.jjj);

            gambar.setImageDrawable(getResources().getDrawable(R.drawable.centang));
            oke.setBackgroundColor(getResources().getColor(R.color.greenText));
            text.setText("Kembalian Biaya pekerjaan anda  "+formatRupiah.format((double)hasil)+ " Terima Kasih!");
            bawahegambar.setText("Kelebihan Biaya");
            oke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    public void CekStatus(){
        if (mPostKeyStatus.equals("Selesai") || mPostKeyStatus.equals("Proses") || mPostKeyStatus.equals("Pending")){
            sudahDiambil.setVisibility(View.GONE);
        }
    }
    public void CekBpkb(){
        if (mPostKeyStatusBpkb.equals("Belum Diambil")){
            ambilBpkb.setVisibility(View.VISIBLE);
        } else{
            ambilBpkb.setVisibility(View.INVISIBLE);
        }
    }


    private void addData(final String id_transaksi) {
        class AddData extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(StatusPreview.this, "Updating...", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(StatusPreview.this, s, Toast.LENGTH_LONG).show();

                if (s.equals("Berhasil Merubah Data")){
                    Intent intent = new Intent(StatusPreview.this, com.biro.pengurusan.biropengurusanstnkbpkb.Status.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(StatusPreview.this, "gagal merubah data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KEY_EMP_ID_TRANSAKSI, id_transaksi);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_CHANGESTATUS, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
    }
    private void addData2(final String id_transaksi) {
        class AddData extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(StatusPreview.this, "Updating...", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(StatusPreview.this, s, Toast.LENGTH_LONG).show();

                if (s.equals("Berhasil Merubah Data")){
                    Intent intent = new Intent(StatusPreview.this, com.biro.pengurusan.biropengurusanstnkbpkb.Status.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(StatusPreview.this, "gagal merubah data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KEY_EMP_ID_TRANSAKSI, id_transaksi);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_GET_CHANGEBPKB, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
    }

    public void back(View view) {
        Intent intent = new Intent (this, Status.class);
        startActivity(intent);
    }
}
