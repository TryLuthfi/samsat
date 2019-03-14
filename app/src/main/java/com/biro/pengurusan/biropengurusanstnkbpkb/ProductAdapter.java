package com.biro.pengurusan.biropengurusanstnkbpkb;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.json.JSONObject;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;
    private String JSON_STRING;
    String id_transaksi, id_pelanggan, tgl_transaksi, tanggal, jenis_surat, titipan, ket_titipan,no_plat, tanggal_selesai, status, notife, notifesurat, id_pelanggann;
    JSONObject jo;
    private Context mContext;
    ContextWrapper contextWrapper;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, parent, false);



        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.textViewTitle.setText(product.getNo_plat());
        holder.textViewShortDesc.setText(product.getJenis_pengurusan());
        holder.textViewPrice.setText(product.getStatus());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx.getApplicationContext(), StatusPreview.class);
                intent.putExtra("plat", product.getNo_plat());
                intent.putExtra("ID", product.getId_transaksi());
                intent.putExtra("pengurusan", product.getJenis_pengurusan());
                intent.putExtra("tanggal", product.getTgl_transaksi());
                intent.putExtra("titipan", product.getTitipan());
                intent.putExtra("ket_titipan", product.getKet_titipan());
                intent.putExtra("status", product.getStatus());
                intent.putExtra("ket_status", product.getKet_status());
                intent.putExtra("rincian_surat", product.getRincian_surat());
                intent.putExtra("statusBpkb", product.getStatusBpkb());
                mCtx.startActivity(intent);

                /*final Dialog alertDialog = new Dialog(mCtx);
                alertDialog.setCancelable(true);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.setContentView(R.layout.custom_dialog);
//                TextView bpkb = alertDialog.findViewById(R.id.bpkb);
                TextView plat = alertDialog.findViewById(R.id.plat);
                TextView pengurusan = alertDialog.findViewById(R.id.pengurusan);
                TextView tanggal = alertDialog.findViewById(R.id.tanggal);
                TextView status = alertDialog.findViewById(R.id.status);
                final TextView ket_status = alertDialog.findViewById(R.id.ket_status);
                TextView rincian_surat = alertDialog.findViewById(R.id.rincian_surat);
//                bpkb.setText(""+product.getJenis_surat());
                plat.setText("" + product.getNo_plat());
                pengurusan.setText("" + product.getJenis_pengurusan());
                tanggal.setText("" + product.getTgl_transaksi());
                rincian_surat.setText("" + product.getRincian_surat());
                status.setText("" + product.getStatus());
                ket_status.setText("" + product.getId_pelanggan());
                Button btnDone = (Button) alertDialog.findViewById(R.id.custom_dialog_btn_done);
                Button btnGone = (Button) alertDialog.findViewById(R.id.custom_dialog_btn_gone);

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btnGone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addData(product.getId_transaksi());
                    }
                });
                alertDialog.show();*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        CardView da;
        View view;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            da = itemView.findViewById(R.id.da);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}