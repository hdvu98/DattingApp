package com.example.thanhhai.banmuonhenho;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


//
//        Matching matching = new Matching();
//        for (int i = 0; i < 2; i++) {
//            matching.id = myRef.push().getKey();
//            matching.status=0;
//            matching.isUser1Agree =false;
//            matching.isUser2Agree=false;
//
//            matching.user1="";
//            matching.user2="";
//            myRef.child("dsMaching").child(matching.id).setValue(matching);
//        }



        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
         //    autoLogin();

                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    void autoLogin() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        Query query = myRef.child("SaveLogin").orderByChild("idPhone").equalTo(getAndroidId()).limitToFirst(1);
        if (query == null) {
            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        SaveLogin saveLogin = data.getValue(SaveLogin.class);
                        if(saveLogin.isOnLogin){
//                            Intent intent = new Intent(WelcomeActivity.this,UserInfomationActivity.class);
                            Intent intent2 = new Intent(WelcomeActivity.this,ChatRoomMCActivity.class);
//                            intent.putExtra("id",saveLogin.idUser);
//                            intent2.putExtra("id",saveLogin.idUser);

                            startActivity(intent2);
                            finish();

                        }
                        else if(!saveLogin.isOnLogin){
                            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
                else{
                    Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public String getAndroidId(){
        String androidId =Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return  androidId;
    }
}
