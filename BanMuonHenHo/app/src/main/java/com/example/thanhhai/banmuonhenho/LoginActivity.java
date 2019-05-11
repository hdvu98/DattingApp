package com.example.thanhhai.banmuonhenho;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import android.provider.Settings.Secure;



public class LoginActivity extends AppCompatActivity {
    Button signup, signin;
    Context context;
    EditText username, password;




    public String getAndroidId(){
        String androidId =Secure.getString(getContentResolver(),
                Secure.ANDROID_ID);
        return  androidId;
    }


    public void hideKeyBoard(View view) {
//        View view = this.getCurrentFocus();
//        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        signup = (Button) findViewById(R.id.btSignUp);
        signin = (Button) findViewById(R.id.btSignIn);
        username = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtPassword);


        username.setInputType(0);
        username.setFocusable(true);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setInputType(1);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });

    }

    void autoLogin() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        Query query = myRef.child("SaveLogin").orderByChild("idPhone").equalTo(getAndroidId()).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        SaveLogin saveLogin = data.getValue(SaveLogin.class);
                        if(saveLogin.isOnLogin){
                            Intent intent = new Intent(LoginActivity.this,UserInfomationActivity.class);
                            intent.putExtra("id",saveLogin.idUser);
                            startActivity(intent);
                            finish();

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void login() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        Query query = myRef.child("Account").orderByChild("username").equalTo(username.getText().toString()).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Account account = data.getValue(Account.class);
                        if(account.password.equals(password.getText().toString()) && account.username.equals(username.getText().toString())){
//
                            SaveLogin saveLogin = new SaveLogin(getAndroidId(),account.id,true);
                            myRef.child("SaveLogin").child(saveLogin.idPhone).setValue(saveLogin);

                            if(account.isUser){
                                Intent intent = new Intent(LoginActivity.this,UserInfomationActivity.class);
                                intent.putExtra("id",account.id);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intenttt = new Intent(LoginActivity.this,RoomListMCActivity.class);
                                intenttt.putExtra("id",account.id);


                                startActivity(intenttt);
                                finish();

                            }


                            return;
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {

                    Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void openRegister()
    {
        Intent intent= new Intent(context,RegisterActivity.class);
        username.setText("");
        context.startActivity(intent);
        finish();
    }
    public void clickRigester(View view) {
    }
}
