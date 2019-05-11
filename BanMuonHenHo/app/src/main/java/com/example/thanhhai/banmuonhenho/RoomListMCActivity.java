package com.example.thanhhai.banmuonhenho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class RoomListMCActivity extends AppCompatActivity {
    private ListView lvRoom;
    Context context;
    String idMc;
    Room chatRoom=new Room();

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;

//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list_mc);

        lvRoom=findViewById(R.id.lvSchedule);

        final ArrayList<Room> arrayList=new ArrayList<>();


        // kết nối database để lấy dữ liệu

        Intent intent=getIntent();
        idMc=intent.getStringExtra("id");

        //tạo custom listview
        customAdapter=new CustomAdapter(this,R.layout.target_item,arrayList);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("dsRoom").orderByChild("mc").equalTo(idMc);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            //lấy dữ liệu vào arrayList
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        chatRoom = data.getValue(Room.class);
                        arrayList.add(chatRoom);

                        lvRoom.setAdapter(customAdapter);


                    }

                }
                else {

                    Toast.makeText(RoomListMCActivity.this,"Không tồn tại user",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //xử lý sự kiện click khi click vào item
        lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(RoomListMCActivity.this,ChatRoomMCActivity.class);
                intent.putExtra("id",idMc);
                intent.putExtra("idRoom",arrayList.get(position).id);

                startActivity(intent);
                finish();
                //Toast.makeText(RoomListMCActivity.this,"Bạn đã chọn "+position,Toast.LENGTH_LONG).show();
            }
        });

    }

    private void chooseARoom() {
    }


}
