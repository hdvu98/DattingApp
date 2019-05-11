package com.example.thanhhai.banmuonhenho;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;


public class ChatRoomActivity extends AppCompatActivity {

    int count;
    DatabaseReference myRef;
    String id;
    String idRoom;
    User user;
    String idUser1,idUser2;
    User user1,user2;

    TextView txtUser1,txtUser2;

    ImageButton btnAccept,btnBack;
    Uri femaleMp4,maleMp4,mcMp4,urlMp4;
    String maleMp3,femaleMp3,agreeDating,disagreeDating,urlMp3,mcMp3;
    MediaPlayer mediaPlayer;
    VideoView videoViewMale,videoViewFemale,videoViewMc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);



        getId();
        event();


        mcMp3 = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fbam_nut_MC.mp3?alt=media";

        maleMp3 = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fchat_voice.mp3?alt=media";
        maleMp4 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fuser_male.mp4?alt=media");
        femaleMp3 = maleMp3;
        femaleMp4 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fuser_female.mp4?alt=media");
        agreeDating = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fdong_y_MC.mp3?alt=media";
        disagreeDating = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fkhong_dong_y_MC.mp3?alt=media";

        mcMp4 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2F1.mp4?alt=media");

        videoViewFemale = (VideoView) findViewById(R.id.videoViewUser2);
        videoViewMale =(VideoView) findViewById(R.id.videoViewUser1);
        videoViewMc = (VideoView) findViewById(R.id.videoViewMC);
//        videoViewMc.setVideoURI();

        mediaPlayer = new MediaPlayer();





        user1=new User();
        user2=new User();
        user = new User();
        myRef =  FirebaseDatabase.getInstance().getReference();
//        myRef.child("dsRoom").push().setValue(new Room("id","matching","mc",1));
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        idRoom = intent.getStringExtra("idRoom");
        updateInfoUser();

        getData();

//
//        check();

    }


    void updateInfoUser(){
        count =0;
        myRef =FirebaseDatabase.getInstance().getReference();
        final Query query = myRef.child("User").orderByChild("id").equalTo(id).limitToFirst(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        user = data.getValue(User.class);
                        Query query1 = myRef.child("dsRoom").orderByChild("id")
                                .equalTo(user.datingStatus.currentRoom).limitToFirst(1);
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot edataSnapshot) {
                                if(edataSnapshot.exists()){
                                    for(DataSnapshot e : edataSnapshot.getChildren()){

                                        Room room = e.getValue(Room.class);
                                        if(room.status==0){

                                            Toast.makeText(ChatRoomActivity.this,"Phòng chưa sẵn sàng",Toast.LENGTH_LONG).show();
                                        }else if(room.status==1){
                                            Toast.makeText(ChatRoomActivity.this,
                                                    "Phòng đã sẵn sàng. Chuẩn bị bắt đầu",Toast.LENGTH_LONG).show();

                                        }else if(room.status==2){
                                            Toast.makeText(ChatRoomActivity.this,
                                                    "Bắt đầu trò chuyện",Toast.LENGTH_LONG).show();
                                            if(user.userBasicInfo.gender){
                                                urlMp3 = femaleMp3;
                                            }else {
                                                urlMp3 = maleMp3;
                                            }
                                            try {
                                                mediaPlayer = new MediaPlayer();
                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                mediaPlayer.setDataSource(urlMp3);
                                                mediaPlayer.prepareAsync();
                                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        mp.start();
                                                    }

                                                });
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }


                                        }else if(room.status==3){
                                            //                            Toast.makeText(ChatRoomMCActivity.this,
//                                    "video",Toast.LENGTH_LONG).show();
                                            if(mediaPlayer.isPlaying())
                                                mediaPlayer.stop();


                                            videoViewFemale.setVideoURI(femaleMp4);
                                            videoViewMc.setVideoURI(mcMp4);
                                            videoViewMale.setVideoURI(maleMp4);

;
                                                    videoViewMale.start();


                                                    videoViewFemale.start();

                                                    videoViewMc.start();




                                        }else if(room.status==4){
                                            btnAccept.setVisibility(View.VISIBLE);
                                            if(videoViewFemale.isPlaying())
                                                videoViewFemale.stopPlayback();

                                            if(videoViewMale.isPlaying())
                                                videoViewMale.stopPlayback();

                                            if(videoViewMc.isPlaying())
                                                videoViewMc.stopPlayback();
                                            urlMp3 = mcMp3;
                                            try {
                                                mediaPlayer = new MediaPlayer();
                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                mediaPlayer.setDataSource(urlMp3);
                                                mediaPlayer.prepareAsync();
                                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        mp.start();
                                                    }

                                                });
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }

                                            Toast.makeText(ChatRoomActivity.this,"Bắt đầu bấm nút hẹn hò",Toast.LENGTH_SHORT).show();

                                            CountDownTimer countDownTimer = new CountDownTimer(9000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
//                                                        Toast.makeText(ChatRoomActivity.this,count+"",Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFinish() {
                                                    check();
                                                    btnAccept.setVisibility(View.INVISIBLE);
                                                }
                                            }.start();

                                        }else if(room.status==5){

                                            Toast.makeText(ChatRoomActivity.this,"Các bạn đã đồng ý hẹn hò",Toast.LENGTH_LONG).show();
                                            urlMp3 = agreeDating;
                                            try {
                                                mediaPlayer = new MediaPlayer();
                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                mediaPlayer.setDataSource(urlMp3);
                                                mediaPlayer.prepareAsync();
                                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        mp.start();
                                                    }

                                                });
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }

                                        }else if(room.status==6){

                                            Toast.makeText(ChatRoomActivity.this,"Các bạn không đồng ý hẹn hò",Toast.LENGTH_LONG).show();
                                            urlMp3 = disagreeDating;
                                            try {
                                                mediaPlayer = new MediaPlayer();
                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                mediaPlayer.setDataSource(urlMp3);
                                                mediaPlayer.prepareAsync();
                                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        mp.start();
                                                    }

                                                });
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }else if(room.status==7){
                                            Toast.makeText(ChatRoomActivity.this,"Kết thúc",Toast.LENGTH_LONG).show();
                                        }


                                    }




                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void chatRoom(){

    }


    void check(){

        Query qr = myRef.child("dsRoom").orderByChild("id").equalTo(idRoom).limitToFirst(1);
        qr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Room room = data.getValue(Room.class);

                        if(room.isUser2Agree && room.isUser1Agree){
                            myRef.child("dsRoom").child(idRoom).child("status").setValue(5);
                            myRef.child("User").child(id).child("datingStatus").child("currentRoom").setValue("");
                            if(id.equals(room.user1)) {
                                myRef.child("User").child(id).child("datingStatus").child("currentLover").setValue(user2.id);
                                myRef.child("User").child(user2.id).child("datingStatus").child("currentLover").setValue(id);
                            }
                            else {
                                myRef.child("User").child(id).child("datingStatus").child("currentLover").setValue(user1.id);
                                myRef.child("User").child(user1.id).child("datingStatus").child("currentLover").setValue(id);

                            }
                        }
                        else{

                            myRef.child("dsRoom").child(idRoom).child("status").setValue(6);

                        }
                    }
                }}

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
    }


    void event(){
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent =new Intent(ChatRoomActivity.this,MatchingActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);*/
                //finish();

                Toast.makeText(ChatRoomActivity.this, "Ban đã đồng ý hẹn hò",
                        Toast.LENGTH_SHORT).show();

                Query qr = myRef.child("dsRoom").orderByChild("id").equalTo(idRoom).limitToFirst(1);
                qr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Room room = data.getValue(Room.class);

                                if(room.user1.equals(id)){
                                    myRef.child("dsRoom").child(idRoom).child("isUser1Agree").setValue(true);

//                                    myRef.child("dsRoom").child(idRoom).child("isUser2Agree").setValue(true);
                                }
                                if(room.user2.equals(id)){
                                    myRef.child("dsRoom").child(idRoom).child("isUser2Agree").setValue(true);

//                                    myRef.child("dsRoom").child(idRoom).child("isUser1Agree").setValue(true);
                                }
                            }
                        }}

                    @Override
                    public void onCancelled (DatabaseError databaseError){

                    }
                });
            }





        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent =new Intent(ChatRoomActivity.this,UserInfomationActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });

    }

    void getId(){

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnAccept = (ImageButton) findViewById(R.id.btnAgree);
        txtUser1 = (TextView) findViewById(R.id.txtUser1Info);

        txtUser2 = (TextView) findViewById(R.id.txtUser2Info);

    }


    void getData(){

        Query qr = myRef.child("dsRoom").orderByChild("id").equalTo(idRoom).limitToFirst(1);
        qr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Room room = data.getValue(Room.class);

                        idUser1 = room.user1;
                        idUser2 = room.user2;
                        Query query1 = myRef.child("User").orderByChild("id").equalTo(idUser1).limitToFirst(1);
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        user1 = data.getValue(User.class);
                                        txtUser1.setText(user1.userBasicInfo.name+", "+(Calendar.getInstance().get(Calendar.YEAR)
                                                -user1.userBasicInfo.birthday.year)+"");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Query query2 = myRef.child("User").orderByChild("id").equalTo(idUser2).limitToFirst(1);
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        user2 = data.getValue(User.class);
                                        txtUser2.setText(user2.userBasicInfo.name+", "+(Calendar.getInstance().get(Calendar.YEAR)
                                                -user2.userBasicInfo.birthday.year)+"");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

//

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
