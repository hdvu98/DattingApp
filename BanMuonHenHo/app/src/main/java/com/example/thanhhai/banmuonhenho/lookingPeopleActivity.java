package com.example.thanhhai.banmuonhenho;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class lookingPeopleActivity extends AppCompatActivity {

    Button btnRelationship,btnSexuality,btnHeight,btnWeight,btnBodyType,btnEyeColor,btnHairColor,btnLiving,btnKid,btnSmoking, btnDrinking;
    ImageButton btnRelationship2,btnSexuality2,btnHeight2,btnWeight2,btnBodyType2,btnEyeColor2,btnHairColor2,btnLiving2,btnKid2,btnSmoking2, btnDrinking2;
    ImageButton btnSaveActionBar;


    String id;
    UserLooking userLooking;

    User user;

    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_people);
        getId();
        event();
        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        userLooking = new UserLooking();
        myRef = FirebaseDatabase.getInstance().getReference();

        Query query = myRef.child("User").orderByChild("id").equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Toast.makeText(RegisterActivity.this,dataSnapshot.getChildrenCount()+"",Toast.LENGTH_SHORT).show();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        user = data.getValue(User.class);
                        userLooking = user.userLooking;
                        DataDemo dataDemo = new DataDemo();

                       
                        btnRelationship.setText(dataDemo.relationshipString[userLooking.relationship]);
                        btnSexuality.setText(dataDemo.sexualityString[userLooking.sexuality]);
                        btnHeight.setText(dataDemo.heightString[userLooking.height]);
                        btnWeight.setText(dataDemo.weightString[userLooking.weight]);
                        btnBodyType.setText(dataDemo.bodyImageString[userLooking.bodyImage]);
                        btnEyeColor.setText(dataDemo.eydColorString[userLooking.eyeColor]);
                        btnHairColor.setText(dataDemo.hairColorString[userLooking.hairColor]);
                        btnLiving.setText(dataDemo.livingString[userLooking.living]);
                        btnKid.setText(dataDemo.kidString[userLooking.kid]);
                        btnSmoking.setText(dataDemo.smokingString[userLooking.smoking]);
                        btnDrinking.setText(dataDemo.drinkingString[userLooking.drinking]);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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


                Intent intent = new Intent(lookingPeopleActivity.this, UserInfomationActivity.class);
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
                            user = data.getValue(User.class);
                            user.userLooking = userLooking;
                            
                            myRef.child("User").child(id).setValue(user);
                            Toast.makeText(lookingPeopleActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
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

    void getId(){

       // btnSaveActionBar = (ImageButton) findViewById(R.id.btnActionBar);

        btnRelationship = (Button) findViewById(R.id.getRelationship);
        btnRelationship2 = (ImageButton) findViewById(R.id.getRelationship2);

        btnSexuality = (Button) findViewById(R.id.getSexuality);
        btnSexuality2 = (ImageButton) findViewById(R.id.getSexuality2);

        btnHeight = (Button) findViewById(R.id.getHeight);
        btnHeight2 = (ImageButton) findViewById(R.id.getHeight2);

        btnWeight = (Button) findViewById(R.id.getWeight);
        btnWeight2 = (ImageButton) findViewById(R.id.getWeight2);

        btnBodyType = (Button) findViewById(R.id.getBodyImage);
        btnBodyType2 = (ImageButton) findViewById(R.id.getBodyImage2);

        btnEyeColor = (Button) findViewById(R.id.getEyeColor);
        btnEyeColor2 = (ImageButton) findViewById(R.id.getEyeColor2);

        btnHairColor = (Button) findViewById(R.id.getHairColor);
        btnHairColor2 = (ImageButton) findViewById(R.id.getHairColor2);

        btnLiving = (Button) findViewById(R.id.getLiving);
        btnLiving2 = (ImageButton) findViewById(R.id.getLiving2);

        btnKid = (Button) findViewById(R.id.getKid);
        btnKid2 = (ImageButton) findViewById(R.id.getKid2);

        btnSmoking = (Button) findViewById(R.id.getSmoking);
        btnSmoking2 = (ImageButton) findViewById(R.id.getSmoking2);


        btnDrinking = (Button) findViewById(R.id.getDrinking);
        btnDrinking2 = (ImageButton) findViewById(R.id.getDrinking2);

    }


    void event(){





        btnRelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuRelationship();
                hideKeyBoard(v);
            }
        });
        btnRelationship2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRelationship.callOnClick();
            }
        });

        btnSexuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuSexuality();
                hideKeyBoard(v);

            }
        });
        btnSexuality2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSexuality.callOnClick();
            }
        });

        btnHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuHeight();

            }
        });
        btnHeight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHeight.callOnClick();
            }
        });

        btnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuWeight();

            }
        });
        btnWeight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnWeight.callOnClick();
            }
        });


        btnBodyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuBodyType();

            }
        });
        btnBodyType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBodyType.callOnClick();
            }
        });

        btnEyeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuEyeColor();
                hideKeyBoard(v);

            }
        });
        btnEyeColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEyeColor.callOnClick();
            }
        });

        btnHairColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuHairColor();

            }
        });
        btnHairColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHairColor.callOnClick();
            }
        });


        btnLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuLiving();
                hideKeyBoard(v);


            }
        });
        btnLiving2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLiving.callOnClick();
            }
        });

        btnKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuKid();

            }
        });
        btnKid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKid.callOnClick();
            }
        });

        btnSmoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuSmoking();

            }
        });
        btnSmoking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSmoking.callOnClick();
            }
        });

        btnDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(v);
                showMenuDrinking();

            }
        });
        btnDrinking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDrinking.callOnClick();
            }
        });

    }


    void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    void showMenuLiving(){
        PopupMenu popupMenu = new PopupMenu(this, btnLiving2);
        popupMenu.getMenuInflater().inflate(R.menu.living, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                btnLiving.setText(item.getTitle());

//                btnLiving.setText(item.getOrder()+"");
                userLooking.living = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }


    void showMenuKid(){
        PopupMenu popupMenu = new PopupMenu(this, btnKid2);
        popupMenu.getMenuInflater().inflate(R.menu.kid, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnKid.setText(item.getTitle());


//                btnKid.setText(item.getOrder()+"");
                userLooking.kid = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }


    void showMenuSmoking(){
        PopupMenu popupMenu = new PopupMenu(this, btnSmoking2);
        popupMenu.getMenuInflater().inflate(R.menu.smoking, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                btnSmoking.setText(item.getTitle());


//                btnSmoking.setText(item.getOrder()+"");
                userLooking.smoking = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }





    void showMenuDrinking(){
        PopupMenu popupMenu = new PopupMenu(this, btnDrinking2);
        popupMenu.getMenuInflater().inflate(R.menu.drinking, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnDrinking.setText(item.getTitle());



//                btnDrinking.setText(item.getOrder()+"");
                userLooking.drinking = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }
    void showMenuRelationship(){
        PopupMenu popupMenu = new PopupMenu(this, btnRelationship2);
        popupMenu.getMenuInflater().inflate(R.menu.relationship, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnRelationship.setText(item.getTitle());


//                btnRelationship.setText(item.getOrder()+"");
                userLooking.relationship = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }





    void showMenuSexuality(){
        PopupMenu popupMenu = new PopupMenu(this, btnSexuality2);
        popupMenu.getMenuInflater().inflate(R.menu.sexuality, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnSexuality.setText(item.getTitle());


//                btnSexuality.setText(item.getOrder()+"");
                userLooking.sexuality = item.getOrder();


                return false;
            }
        });
        popupMenu.show();
    }

    void showMenuHeight(){
        PopupMenu popupMenu = new PopupMenu(this, btnHeight2);
        popupMenu.getMenuInflater().inflate(R.menu.height, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                btnHeight.setText(item.getTitle());


//                btnHeight.setText(item.getOrder()+"");
                userLooking.height = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }

    void showMenuWeight(){
        PopupMenu popupMenu = new PopupMenu(this, btnWeight2);
        popupMenu.getMenuInflater().inflate(R.menu.weight, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                btnWeight.setText(item.getTitle());


//                btnWeight.setText(item.getOrder()+"");
                userLooking.weight = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }




    void showMenuBodyType(){
        PopupMenu popupMenu = new PopupMenu(this, btnBodyType2);
        popupMenu.getMenuInflater().inflate(R.menu.body_image, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnBodyType.setText(item.getTitle());
//                btnBodyType.setText(item.getOrder()+"");


//                btnBodyType.setText(item.getOrder()+"");
                userLooking.bodyImage = item.getOrder();


                return false;
            }
        });
        popupMenu.show();
    }




    void showMenuEyeColor(){
        PopupMenu popupMenu = new PopupMenu(this, btnEyeColor2);
        popupMenu.getMenuInflater().inflate(R.menu.eye_color, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnEyeColor.setText(item.getTitle());



//                btnEyeColor.setText(item.getOrder()+"");
                userLooking.eyeColor = item.getOrder();

                return false;
            }
        });
        popupMenu.show();
    }

    void showMenuHairColor(){
        PopupMenu popupMenu = new PopupMenu(this, btnHairColor2);
        popupMenu.getMenuInflater().inflate(R.menu.hair_color, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnHairColor.setText(item.getTitle());


//                btnHairColor.setText(item.getOrder()+"");
                userLooking.hairColor = item.getOrder();


                return false;
            }
        });
        popupMenu.show();
    }




}
