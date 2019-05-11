package com.example.thanhhai.banmuonhenho;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Room>  {
    Context context;
    int resource;
    ArrayList<Room> arrRoom;

    public CustomAdapter( Context context, int resource,ArrayList<Room> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arrRoom=objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null) {
            viewHolder =new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.target_item,parent,false);

            viewHolder.txtTime = convertView.findViewById(R.id.txtTime);
            viewHolder.txtID = convertView.findViewById(R.id.txtID);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }


        Room room=arrRoom.get(position);


        //viewHolder.txtTime.setText("");
        viewHolder.txtID.setText("ID "+room.getId());
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        return convertView;
    }
    public class ViewHolder{
        TextView txtID;
        TextView txtTime;
    }
}
