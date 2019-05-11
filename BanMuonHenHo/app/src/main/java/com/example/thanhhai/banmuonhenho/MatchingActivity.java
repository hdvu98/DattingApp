package com.example.thanhhai.banmuonhenho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MatchingActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        ImageButton back = (ImageButton) findViewById(R.id.btnBack);



        Intent sIntent = getIntent();
        id = sIntent.getStringExtra("id");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingActivity.this,UserInfomationActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });
    }
}
