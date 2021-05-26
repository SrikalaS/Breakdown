package com.example.myapplication;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.location.setText("location:"+uploadCurrent.getLocation());
        holder.phonenum.setText("phoenenum:"+uploadCurrent.getPhonenum());
        holder.vehicleno.setText("Email:"+uploadCurrent.getEmail());
        Picasso.get()
                .load(uploadCurrent.getImageurl())
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
        public TextView textViewName,location,phonenum,vehicleno;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            location=itemView.findViewById(R.id.mechaniclocation);
            phonenum=itemView.findViewById(R.id.phonenum);
            vehicleno=itemView.findViewById(R.id.vehcileno);

            imageView = itemView.findViewById(R.id.img1);
        }
    }
}
