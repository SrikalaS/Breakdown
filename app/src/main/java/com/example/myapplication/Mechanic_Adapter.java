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

import java.util.List;

public class Mechanic_Adapter extends RecyclerView.Adapter<Mechanic_Adapter.ImageViewHolder> {
    private Context mContext;
    private List<Problem_Details> mUploads;

    public Mechanic_Adapter(ViewRequest viewRequest, List<Problem_Details> mUploads) {
        mContext=viewRequest;
        this.mUploads=mUploads;
    }

    @NonNull
    @Override
    public Mechanic_Adapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.mechanic_image_item,parent, false);
        return new Mechanic_Adapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Problem_Details uploadCurrent = mUploads.get(position);
        holder.userName.setText("username:"+uploadCurrent.getUsername());
        holder.useremail.setText("email:"+uploadCurrent.getEmail());
        holder.userphonenum.setText("phonenum:"+uploadCurrent.getPhonenum());
        holder.userproblem.setText("problem:"+uploadCurrent.getProblem());
        holder.userlatiitude.setText("latitude:"+uploadCurrent.getLattitude());
        holder.userlongitude.setText("longitude:"+uploadCurrent.getLongitude());
        holder.uservehicleno.setText("vehilcleno:"+uploadCurrent.getVehicleno());
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mContext,Profile_Activity.class);
                it.putExtra("username",uploadCurrent.getUsername());
                it.putExtra("email",uploadCurrent.getEmail());
                it.putExtra("phonenum",uploadCurrent.getPhonenum());
                it.putExtra("problem",uploadCurrent.getProblem());
                it.putExtra("latitude",uploadCurrent.getLattitude());
                it.putExtra("longitude",uploadCurrent.getLongitude());
                it.putExtra("vehicleno",uploadCurrent.getVehicleno());
                mContext.startActivity(it);

            }
        });


    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, useremail, userphonenum, uservehicleno,userlatiitude,userlongitude,userproblem;
        public RelativeLayout relative;

        public ImageViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            useremail= itemView.findViewById(R.id.useremail);
            uservehicleno= itemView.findViewById(R.id.uservehcileno);
            userphonenum = itemView.findViewById(R.id.userphonenum);
            userlatiitude=itemView.findViewById(R.id.userlatitute);
            userlongitude=itemView.findViewById(R.id.userlongitude);
            userproblem=itemView.findViewById(R.id.userproblem);
            relative=itemView.findViewById(R.id.relative);




        }
    }
}
