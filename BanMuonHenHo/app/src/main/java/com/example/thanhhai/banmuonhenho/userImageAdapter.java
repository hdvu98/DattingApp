package com.example.thanhhai.banmuonhenho;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class userImageAdapter extends BaseAdapter {

    public Context context;
    public int layout;
    public List<userImageCell> imageList;

    public userImageAdapter(Context context, int layout, List<userImageCell> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.image = (ImageView) convertView.findViewById(R.id.imgUserInCell);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }

        userImageCell imgCell = imageList.get(position);
        holder.image.setImageResource(imgCell.image);




        return convertView;
    }
}
