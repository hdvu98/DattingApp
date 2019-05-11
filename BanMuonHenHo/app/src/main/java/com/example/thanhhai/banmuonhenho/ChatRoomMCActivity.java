package com.example.thanhhai.banmuonhenho;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

import java.io.IOException;
import java.util.Calendar;

public class ChatRoomMCActivity extends AppCompatActivity {

    int count;
    DatabaseReference myRef;
    String idMc;
//    User user;
    String idRoom;
    User user1,user2;
    String idUser1,idUser2;

    ImageButton btnChatVoice,btnVideoCall,btnHelp,btnClose;

    Dialog myDialog;

    ImageButton btnBack;
    Button btnAccept;

    Uri femaleMp4,maleMp4,mcMp4,urlMp4;
    String maleMp3,femaleMp3,agreeDating,disagreeDating,urlMp3,mcMp3;
    MediaPlayer mediaPlayer;
    VideoView videoViewMale,videoViewFemale,videoViewMc;



    TextView txtUser1,txtUser2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_mc);


        myRef = FirebaseDatabase.getInstance().getReference();

//        String key= myRef.push().getKey();
//        Room room = new Room(key,"mc",0,"user1","user2");
//        myRef.child("dsRoom").child(key).setValue(room);

        user1=new User();
        user2=new User();

        getId();

        Event();

        initData();
        getData();
        update();
//        check();


//        String key = myRef.push().getKey();
//        Matching matching = new Matching(key,"user1","user2",0);
//        myRef.child("dsMatching").child(key).setValue(matching);

//        Matching matching = new Matching(myRef.push().getKey(),"user1","user2",0);
//        myRef.child("dsMatching").setValue(matching);


    }

    void initData(){


        mcMp3 = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fbam_nut_MC.mp3?alt=media";

        maleMp3 = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fchat_voice.mp3?alt=media";
        maleMp4 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fuser_male.mp4?alt=media");
        femaleMp3 = maleMp3;
        femaleMp4 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fuser_female.mp4?alt=media");
        agreeDating = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fdong_y_MC.mp3?alt=media";
        disagreeDating = "https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2Fkhong_dong_y_MC.mp3?alt=media";

        mcMp4 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dataandroid-32968.appspot.com/o/data%2F1.mp4?alt=media");

        Intent intent = getIntent();
        idMc = intent.getStringExtra("id");
        idRoom = intent.getStringExtra("idRoom");

        mediaPlayer = new MediaPlayer();




    }

    void getId(){

        videoViewFemale = (VideoView) findViewById(R.id.videoFemale);
        videoViewMale =(VideoView) findViewById(R.id.videoMale);
        videoViewMc = (VideoView) findViewById(R.id.videoMC);
//        videoViewMc.setVideoURI();

        txtUser1 = (TextView) findViewById(R.id.txtMale);

        txtUser2 = (TextView) findViewById(R.id.txtFemale);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnChatVoice = (ImageButton) findViewById(R.id.btnChatVoice);
        btnVideoCall = (ImageButton) findViewById(R.id.btnVideoCall);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);
        btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnAccept = (Button) findViewById(R.id.btnStart);

    }

    void Event(){
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatRoomMCActivity.this,"Bấm nút hẹn hò", Toast.LENGTH_SHORT).show();
                myRef.child("dsRoom").child(idRoom).child("status").setValue(4);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.child("dsRoom").child(idRoom).child("status").setValue(7);
                Toast.makeText(ChatRoomMCActivity.this,"Kết thúc", Toast.LENGTH_SHORT).show();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCustomQuestionDialog();
//
//                Toast.makeText(ChatRoomMCActivity.this,"Câu hỏi gợi ý", Toast.LENGTH_SHORT).show();
            }
        });
        btnChatVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("dsRoom").child(idRoom).child("status").setValue(2);

                Toast.makeText(ChatRoomMCActivity.this,"Trò chuyên", Toast.LENGTH_SHORT).show();
            }
        });
        btnVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.child("dsRoom").child(idRoom).child("status").setValue(3);
                Toast.makeText(ChatRoomMCActivity.this,"Mở rèm", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatRoomMCActivity.this,RoomListMCActivity.class);
                intent.putExtra("id",idMc);
                startActivity(intent);
                finish();
            }
        });

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


    void update(){

        count =0;

        Query query1 = myRef.child("dsRoom").orderByChild("id")
                .equalTo(idRoom);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot edataSnapshot) {
                if(edataSnapshot.exists()){
                    for(DataSnapshot e : edataSnapshot.getChildren()){

                        Room room = e.getValue(Room.class);
                        if(room.status==0){

                        }else if(room.status==1){


                        }else if(room.status==2){
//                            Toast.makeText(ChatRoomMCActivity.this,
//                                    "âm thanh",Toast.LENGTH_LONG).show();
                            urlMp3 = maleMp3;
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


                            videoViewMale.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.setVolume(0,0);
                                    videoViewMale.start();
                                    mp.setLooping(true);

                                }
                            });
                            videoViewFemale.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    //mp.setVolume(0,0);
                                    videoViewFemale.start();
                                    mp.setLooping(true);
                                }
                            });
                            videoViewMc.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    //mp.setVolume(0,0);
                                    videoViewMc.start();
                                    mp.setLooping(true);
                                }
                            });


                        }else if(room.status==4){
                            if(videoViewFemale.isPlaying())
                                videoViewFemale.stopPlayback();

                            if(videoViewMale.isPlaying())
                                videoViewMale.stopPlayback();

                            if(videoViewMc.isPlaying())
                                videoViewMc.stopPlayback();
//                            Toast.makeText(ChatRoomMCActivity.this,"ngưng video",Toast.LENGTH_LONG).show();
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



                            CountDownTimer countDownTimer = new CountDownTimer(8000,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    count+=1;
                                    Toast.makeText(ChatRoomMCActivity.this,count+"",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFinish() {
//                                    check();
                                }
                            };



                        }else if(room.status==5){

//                            Toast.makeText(ChatRoomMCActivity.this,"Đồng ý",Toast.LENGTH_LONG).show();
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

//                            Toast.makeText(ChatRoomMCActivity.this,"Không đồng ý",Toast.LENGTH_LONG).show();
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
//                            Toast.makeText(ChatRoomMCActivity.this,"Kết thúc",Toast.LENGTH_LONG).show();
                        }


                    }




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void myCustomQuestionDialog(){
        myDialog=new Dialog(ChatRoomMCActivity.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.help_dialog);

        TextView tit=(TextView)myDialog.findViewById(R.id.txtDiaLogTit);
        TextView txtQuestions=(TextView)myDialog.findViewById(R.id.txtHelp);

        txtQuestions.setText(R.string.mc_question);
        txtQuestions.setMovementMethod(new ScrollingMovementMethod());
//
//        btnClose=(Button)myDialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


    void check(){

        Query qr = myRef.child("dsRoom").orderByChild("id").equalTo(idRoom).limitToFirst(1);
        qr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Room room = data.getValue(Room.class);

                        if(room.isUser2Agree && room.isUser1Agree){
                            myRef.child("dsRoom").child(idRoom).child("status").setValue(5);
                        }
                        if(!room.isUser1Agree && !room.isUser2Agree){
                            myRef.child("dsRoom").child(idRoom).child("status").setValue(6);

                        }
                    }
                }}

                @Override
                public void onCancelled (DatabaseError databaseError){

                }
            });
    }
}