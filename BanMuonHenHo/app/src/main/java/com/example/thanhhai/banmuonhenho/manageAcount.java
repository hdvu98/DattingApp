package com.example.thanhhai.banmuonhenho;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class manageAcount extends AppCompatActivity {

    EditText accountName,email,phone,fb;
    RadioButton male,female;

    Button birthday;
    Birthday myBirthday;
    String id;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_account);
        getId();
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

      myRef = FirebaseDatabase.getInstance().getReference();
        Query query = myRef.child("User").orderByChild("id").equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User user = data.getValue(User.class);

                            accountName.setText(user.userBasicInfo.name);
                            if(user.userBasicInfo.gender){
                                female.setChecked(true);

                            }else{

                                male.setChecked(true);
                            }
                            birthday.setText(user.userBasicInfo.birthday.day+"/"+user.userBasicInfo.birthday.month+
                                    "/"+user.userBasicInfo.birthday.year+"");
                            email.setText(user.userBasicInfo.email);
                            phone.setText(user.userBasicInfo.phone);
                            fb.setText(user.userBasicInfo.facebook);
                            myBirthday= user.userBasicInfo.birthday;




                    }

                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }







    void getId(){
        accountName = (EditText) findViewById(R.id.getAcountName);
        birthday = (Button) findViewById(R.id.getBirthday);
        email = (EditText) findViewById(R.id.getEmail);
        phone = (EditText) findViewById(R.id.getPhone);
        fb = (EditText) findViewById(R.id.getFacebook);
        male = (RadioButton)findViewById(R.id.radioButton);
        female = (RadioButton)findViewById(R.id.radioButton2);
    }

    public void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");

                int d, m, y;
                d = calendar.get(Calendar.DAY_OF_MONTH);
                m = calendar.get(Calendar.MONTH) + 1;
                y = calendar.get(Calendar.YEAR);
                birthday.setText(d + "/" + m + "/" + y);
                myBirthday = new Birthday(d, m, y);
            }
        }, yy, mm, dd);
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_action_bar, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:


                Intent intent = new Intent(manageAcount.this, UserInfomationActivity.class);
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
                            user.userBasicInfo.birthday = myBirthday;
                            user.userBasicInfo.name = accountName.getText().toString();
                            user.userBasicInfo.email = email.getText().toString();
                            user.userBasicInfo.phone = phone.getText().toString();
                            user.userBasicInfo.facebook = fb.getText().toString();
                            if(male.isChecked()){
                                user.userBasicInfo.gender = false;
                            }else {
                                user.userBasicInfo.gender=true;
                            }

                            myRef.child("User").child(id).setValue(user);
                            Toast.makeText(manageAcount.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
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
