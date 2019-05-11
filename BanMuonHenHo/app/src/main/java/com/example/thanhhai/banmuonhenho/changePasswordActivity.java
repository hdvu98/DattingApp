package com.example.thanhhai.banmuonhenho;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class changePasswordActivity extends AppCompatActivity {


    EditText currentPass,newPass,newPassAgain;

    String id;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);


        getId();


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        myRef = FirebaseDatabase.getInstance().getReference();
        Query query = myRef.child("User").orderByChild("id").equalTo(id).limitToFirst(1);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//             }
//
//             @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            }
//        );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_action_bar, menu);
        return super.onCreateOptionsMenu(menu);

    }

    void getId(){
        currentPass = (EditText) findViewById(R.id.currentPasswdText);
        newPass = (EditText) findViewById(R.id.newPasswordText);
        newPassAgain = (EditText) findViewById(R.id.confirmNewPasswdText);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(changePasswordActivity.this, UserInfomationActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
                break;
        }

        if(item.getItemId()==R.id.btnActionBar) {

            Query query = myRef.child("User").orderByChild("id").equalTo(id).limitToFirst(1);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Toast.makeText(RegisterActivity.this,dataSnapshot.getChildrenCount()+"",Toast.LENGTH_SHORT).show();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User user = data.getValue(User.class);

                            if(!currentPass.getText().toString().equals(user.userBasicInfo.account.password)){
                                Toast.makeText(changePasswordActivity.this, "Sai mật khẩu hiện tại", Toast.LENGTH_SHORT).show();
                            }
                            else if(!newPass.getText().toString().equals(newPassAgain.getText().toString())){
                                Toast.makeText(changePasswordActivity.this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                            }
                            else if(currentPass.getText().toString().equals(user.userBasicInfo.account.password)){
                                user.userBasicInfo.account.password = newPass.getText().toString();
                                myRef.child("User").child(id).setValue(user);
                                myRef.child("Account").child(id).setValue(user.userBasicInfo.account);
                                Toast.makeText(changePasswordActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }


}
