package com.example.thanhhai.banmuonhenho;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    ImageButton back;
    Button editTextDatePicker;

    Boolean kt;

    Button signup;
    EditText username, password, passwordAgain, name,email;
    Birthday birthday;
    boolean gender;
    RadioButton isMale, isFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getId();
        event();
        username.setInputType(0);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setInputType(1);
            }
        });


    }
    //mở Sigin activity

    public void hideKeyBoard(View view) {
//        View view = this.getCurrentFocus();
//        if (view != null) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
    }

    void getId() {
        back = (ImageButton) findViewById(R.id.btnBack);
        signup = (Button) findViewById(R.id.btSignUp);
        username = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtPassword);
        passwordAgain = (EditText) findViewById(R.id.edtPassword2);
        name = (EditText) findViewById(R.id.edtFirstName);
        isMale = (RadioButton) findViewById(R.id.male);
        isFemale = (RadioButton) findViewById(R.id.female);
        editTextDatePicker = (Button) findViewById(R.id.edtDate);
        email = (EditText) findViewById(R.id.edtEmail);

    }

    void event() {
        editTextDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                chooseDate();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openSigin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            @Override
            public void onClick(View v) {

                if (isFemale.isChecked() && isMale.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Bạn chưa chọn giới tính", Toast.LENGTH_SHORT).show();
                    return;
                } else if (username.getText().toString().length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Tài khoản phải có từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().toString().length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (email.getText().toString().length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password.getText().toString().equals(passwordAgain.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                } else if (name.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Họ và tên không được bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextDatePicker.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Bạn chưa chọn ngày sinh", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Query query = myRef.child("Account").orderByChild("username").equalTo(username.getText().toString()).limitToFirst(1);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Toast.makeText(RegisterActivity.this,dataSnapshot.getChildrenCount()+"",Toast.LENGTH_SHORT).show();
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    Account acc = data.getValue(Account.class);
                                    if(acc.username.equals(username.getText().toString())){
                                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                            } else {
                                gender = false;
                                if(isFemale.isChecked()){
                                    gender = true;
                                }
                                Account account = new Account(myRef.push().getKey(),username.getText().toString(),password.getText().toString()
                                ,true);
                                myRef.child("Account").child(account.id).setValue(account);
                                UserBasicInfo userBasicInfo = new UserBasicInfo(name.getText().toString(),email.getText().toString()
                                        ,birthday,gender,account);
                                User user = new User(account.id,userBasicInfo,new Images(),new UserIntro()
                                ,new UserLooking());
                                myRef.child("User").child(account.id).setValue(user);

                                Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                                return;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });
                }
            }



        });
    }

    public void openSigin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //tạo dialog chọn ngày
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
                editTextDatePicker.setText(d + "/" + m + "/" + y);
                birthday = new Birthday(d, m, y);
            }
        }, yy, mm, dd);
        datePickerDialog.show();
    }

}


