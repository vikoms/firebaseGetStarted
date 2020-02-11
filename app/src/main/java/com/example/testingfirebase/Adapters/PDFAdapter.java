package com.example.testingfirebase.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testingfirebase.Models.UploadPDF;
import com.example.testingfirebase.R;

import org.w3c.dom.Text;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.MyViewHolder> {

    List<UploadPDF> mList;
    Context mContext;

    public PDFAdapter(List<UploadPDF> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_pdf,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvName.setText(mList.get(position).getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPDF uploadPDF = mList.get(position);

                Toast.makeText(mContext, mList.get(position).getUrl(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent();
//                intent.setType(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(uploadPDF.getUrl()));
//                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        LinearLayout container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container_pdf);
            tvName = itemView.findViewById(R.id.txtBuku);
        }
    }
}
