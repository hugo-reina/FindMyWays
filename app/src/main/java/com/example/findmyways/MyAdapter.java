package com.example.findmyways;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewHolder> {


    Context context;
    Piece[] p;
    String strPiece;

    public MyAdapter(Context context, Piece[] p, String strPiece) {
        this.context = context;
        this.p = p;
        this.strPiece = strPiece;
    }

    @NonNull
    @Override
    public MyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_piece, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.viewHolder holder, int position) {

        holder.textView.setText(p[position].getNom());
        String nom = p[position].getNom();
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, visualiser.class);
                Bundle bundle = new Bundle();
                bundle.putString("nomP",nom);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return p.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button btn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            btn = itemView.findViewById(R.id.btn_visual);

        }
    }
}
