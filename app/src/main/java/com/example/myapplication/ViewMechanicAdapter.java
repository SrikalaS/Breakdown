package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewMechanicAdapter  extends RecyclerView.Adapter<ViewMechanicAdapter.ImageViewHolder> {
    private Context mContext;
    private List<User_details> mUploads;


    public ViewMechanicAdapter(Context context, List<User_details> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ViewMechanicAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.view_mechanic_item, parent, false);
        return new ViewMechanicAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        User_details uploadCurrent = mUploads.get(position);
        holder.textViewName.setText("username:"+uploadCurrent.getUsername());
        holder.phonenum.setText("phoenenum:"+uploadCurrent.getPhonenum());
        holder.vehicleno.setText("Email:"+uploadCurrent.getEmail());
        Picasso.get()
                .load(uploadCurrent.getImageuri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);





    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,location,phonenum,vehicleno,rating;
        public ImageView imageView;
        RelativeLayout relativeLayout;
        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.viewmechanicname);
            location=itemView.findViewById(R.id.viewmechaniclocation);
            phonenum=itemView.findViewById(R.id.viewmechanicphonenum);
            vehicleno=itemView.findViewById(R.id.viewvehcileno);
            relativeLayout=itemView.findViewById(R.id.relative1);
            imageView = itemView.findViewById(R.id.viewmechanicimg1);



        }
    }
}

