package com.example.myapplication;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Mechanic_Detials> mUploads;
    public ImageAdapter(Context context, List<Mechanic_Detials> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Mechanic_Detials uploadCurrent = mUploads.get(position);
        holder.textViewName.setText("username:"+uploadCurrent.getUsername());
        holder.phonenum.setText("phoenenum:"+uploadCurrent.getPhonenum());
        holder.vehicleno.setText("Email:"+uploadCurrent.getEmail());
        holder.rating.setText("Rating:"+uploadCurrent.getRating());
        Picasso.get()
                .load(uploadCurrent.getImageurl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mContext,Message.class);
                it.putExtra("username",uploadCurrent.getUsername());
                it.putExtra("email",uploadCurrent.getEmail());
                it.putExtra("phonenum",uploadCurrent.getPhonenum());
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
        public TextView textViewName,location,phonenum,vehicleno,rating;
        public ImageView imageView;
        RelativeLayout relativeLayout;
        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            //location=itemView.findViewById(R.id.mechaniclocation);
            phonenum=itemView.findViewById(R.id.phonenum);
            vehicleno=itemView.findViewById(R.id.vehcileno);
            relativeLayout=itemView.findViewById(R.id.relativelayout1);
            rating=itemView.findViewById(R.id.vehiclerating);


            imageView = itemView.findViewById(R.id.img1);
        }
    }
}
