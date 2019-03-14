package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    public static final String URL = "http://myindosnack.com/";
    private ProgressDialog progress;
    private Button button;

    @BindView(R.id.ktp) EditText ktp;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText passwrd;
    @BindView(R.id.nama) EditText nama;
    @BindView(R.id.alamat) EditText alamat;
    @BindView(R.id.hp) EditText hp;

    @OnClick(R.id.buttonDaftar) void daftar() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light_Dialog));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View v = inflater.inflate(R.layout.activity_register, null);

        String no_ktp = ktp.getText().toString();
        String emaill = email.getText().toString();
        String password = passwrd.getText().toString();
        String nama_pelanggan = nama.getText().toString();
        String alamat_pelanggan = alamat.getText().toString();
        String telp_pelanggan = hp.getText().toString();
        String usernama = username.getText().toString();

        if (!no_ktp.isEmpty() && !emaill.isEmpty() && !nama_pelanggan.isEmpty() && !alamat_pelanggan.isEmpty() && !telp_pelanggan.isEmpty()){
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RegisterAPI api = retrofit.create(RegisterAPI.class);
            Call<Value> call = api.daftar(no_ktp, nama_pelanggan, emaill,alamat_pelanggan, telp_pelanggan, password, usernama);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(Register.this, Login.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ));
                    finish();
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }
        else if (no_ktp.isEmpty()){
            ktp.setError("No Ktp tidak boleh kosong");
        } else if (nama_pelanggan.isEmpty()){
            nama.setError("nama pelanggan tidak boleh kosong");
        } else if (alamat_pelanggan.isEmpty()){
            alamat.setError("alamat pelanggan tidak boleh kosong");
        } else if (telp_pelanggan.isEmpty()){
            hp.setError("telp pelanggan tidak boleh kosong");
        } else if (password.isEmpty()){
            passwrd.setError("password tidak boleh kosong");
        } else if (usernama.isEmpty()){
            username.setError("username tidak boleh kosong");
        } else if (emaill.isEmpty()){
            email.setError("Email tidak boleh kosong");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        button = findViewById(R.id.buttonDaftar);

    }

    public void back(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        Intent intent = new Intent (this, Login.class);
        startActivity(intent);
        finish();
    }
}
