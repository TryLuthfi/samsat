package com.biro.pengurusan.biropengurusanstnkbpkb;

public class Product {
    private String id_transaksi;
    private String id_pelanggan;
    private String tgl_transaksi;
    private String titipan;
    private String ket_titipan;
    private String rincian_surat;
    private String jenis_pengurusan;
    private String no_plat;
    private String tanggal_selesai;
    private String status;
    private String ket_status;
    private String statusBpkb;

    public Product(String id_transaksi,String id_pelanggan,String tgl_transaksi, String titipan, String ket_titipan,String rincian_surat ,String jenis_pengurusan,String no_plat,String tanggal_selesai, String status, String ket_status , String statusBpkb) {
        this.id_transaksi = id_transaksi;
        this.id_pelanggan = id_pelanggan;
        this.tgl_transaksi = tgl_transaksi;
        this.titipan = titipan;
        this.ket_titipan = ket_titipan;
        this.rincian_surat = rincian_surat;
        this.jenis_pengurusan = jenis_pengurusan;
        this.no_plat = no_plat;
        this.tanggal_selesai = tanggal_selesai;
        this.status = status;
        this.ket_status = ket_status;
        this.statusBpkb = statusBpkb;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }
    public String getId_pelanggan() {
        return id_pelanggan;
    }
    public String getTgl_transaksi() {
        return tgl_transaksi;
    }
    public String getTitipan(){
        return titipan;
    }
    public String getKet_titipan() {return  ket_titipan;}
    public String getRincian_surat() {
        return rincian_surat;
    }

    public String getJenis_pengurusan() {
        return jenis_pengurusan;
    }
    public String getNo_plat() {
        return no_plat;
    }
    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public String getStatus() {
        return status;
    }
    public String getKet_status(){
        return ket_status;
    }
    public String getStatusBpkb(){
        return statusBpkb;
    }
}
