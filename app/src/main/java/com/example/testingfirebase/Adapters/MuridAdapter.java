package com.example.testingfirebase.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testingfirebase.Models.Murid;
import com.example.testingfirebase.R;

import org.w3c.dom.Text;

import java.util.List;

public class MuridAdapter extends RecyclerView.Adapter<MuridAdapter.myViewHolder> {

    List<Murid> listMurid;
    Context mContext;

    public MuridAdapter(List<Murid> listMurid, Context mContext) {
        this.listMurid = listMurid;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Murid currentMurid = listMurid.get(position);
        holder.nama.setText(currentMurid.getName());
    }

    @Override
    public int getItemCount() {
        return listMurid.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder{
        TextView nama;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtNama);
        }
    }
}
