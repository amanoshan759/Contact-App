package com.developtech.contactappwithsqlite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.developtech.contactappwithsqlite.bean.ContactBean;

import java.util.ArrayList;

class Contact_Adapter extends RecyclerView.Adapter<Contact_Adapter.MyViewHolder> {
    View itemview;
    private ArrayList al;
    private Context c;
    private Bundle b;

    Contact_Adapter(ArrayList al, Context c) {
        this.al = al;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactsearch_layout, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ContactBean objbean = (ContactBean) al.get(position);
        holder.txtName.setText(objbean.getName());
        holder.txtMobile.setText(objbean.getMobile());
        String s = objbean.getImage();
        Bitmap bm = DecodeEncode.StringToBitMap(s);
        holder.image.setImageBitmap(bm);
        if (objbean.isBirthdayReminder()) {
            holder.btnBday.setVisibility(View.VISIBLE);
        }
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, ViewContactActivity.class);
                b = new Bundle();
                b.putInt("id", objbean.getContactid());
                Log.e("comtavt", "" + objbean.getContactid());
                Log.e("comtavt", "" + objbean.getName());
                Log.e("comtavt11", "" + objbean.getImage());
                i.putExtras(b);
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtMobile;
        private ImageView image;
        private Button btnBday;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            image = (ImageView) itemView.findViewById(R.id.image);
            btnBday = (Button) itemView.findViewById(R.id.btnBday);
        }
    }
}

