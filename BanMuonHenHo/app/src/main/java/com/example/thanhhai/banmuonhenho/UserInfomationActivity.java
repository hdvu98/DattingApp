package com.example.thanhhai.banmuonhenho;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import android.provider.Settings.Secure;

public class UserInfomationActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;

    private ArrayList<String> mImageUrls = new ArrayList<>();


    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    ImageButton btnHome,btnRoom,btnUser,btnSetting,btnEditUserInfo,btnEditCrushInfo;

    TextView name,selfIntro,lookingCrush;

    ImageView avatar,addPhoto;
    Boolean isAvatarCheck,isAddPhotoCheck;

    ImageView ImgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri ;
    User user;
    String id;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = new User();
        getSupportActionBar().hide();

        setContentView(R.layout.user_info);
//
        init();
        getIdForItem();
        eventItem();


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        String android_id = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("User").orderByChild("id").equalTo(id).limitToFirst(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        user = data.getValue(User.class);
                        getImages();
                        name.setText(user.userBasicInfo.name+", "+(Calendar.getInstance().get(Calendar.YEAR)
                                -user.userBasicInfo.birthday.year)+"");
                        selfIntro.setText(user.userIntro.getString());
                        lookingCrush.setText(user.userLooking.getString());
                        if(user.images.avatar.length()>0)
                            Picasso.with(UserInfomationActivity.this).load(user.images.avatar).into(avatar);

                    }

                }
                else {

                    Toast.makeText(UserInfomationActivity.this,"Không tồn tại user",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private void getImages(){
        initRecyclerView();

    }

    void init(){
        isAddPhotoCheck = false;
        isAvatarCheck = false;
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
            mImageUrls=user.images.linkImages;


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageUrls);

        recyclerView.setAdapter(adapter);
    }

    void getIdForItem(){
        ImgUserPhoto = findViewById(R.id.changeAvatar) ;
        avatar = (ImageView) findViewById(R.id.imgAvatar);
        addPhoto = (ImageView) findViewById(R.id.addPhoto);
        btnSetting = (ImageButton) findViewById(R.id.imgSettingUser);
        btnHome = (ImageButton) findViewById(R.id.btnMatching);
        btnRoom = (ImageButton) findViewById(R.id.btnRoom);
        btnUser = (ImageButton) findViewById(R.id.btnUser);
        btnEditUserInfo = (ImageButton) findViewById(R.id.imgEditInfoUser);
        btnEditCrushInfo = (ImageButton) findViewById(R.id.imgEditInfoCrush);
        name = (TextView) findViewById(R.id.txtNameUser);
        selfIntro = (TextView) findViewById(R.id.txtUserDescriptor);
        lookingCrush = (TextView) findViewById(R.id.txtCrush);

    }

    void eventItem(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(UserInfomationActivity.this,"Home", Toast.LENGTH_SHORT).show();

                if(user.datingStatus.currentLover.length()<5){


                    Toast.makeText(UserInfomationActivity.this,"Đây là nơi chia sẻ thông tin về người đồng ý hẹn hò với bạn",Toast.LENGTH_SHORT).show();
                    Toast.makeText(UserInfomationActivity.this,"Rất tiếc. Bạn chưa được chấp nhận hẹn hò",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(UserInfomationActivity.this,CrushActivity.class);
                    intent.putExtra("myId",user.id);
                    intent.putExtra("idCrush",user.datingStatus.currentLover);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user.datingStatus.currentMatching.length()>0){
                    Intent intent = new Intent(UserInfomationActivity.this,MatchingActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("idRoom",user.datingStatus.currentMatching);

                    startActivity(intent);
                    finish();
                }
                else if(user.datingStatus.currentRoom.length()>0){
                    Intent intent = new Intent(UserInfomationActivity.this,ChatRoomActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("idRoom",user.datingStatus.currentRoom);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(UserInfomationActivity.this,"Chưa có sự kiện",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });


        btnEditUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(UserInfomationActivity.this,"Chỉnh sửa giới thiệu bản thân", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfomationActivity.this,editUserInfo.class);
                intent.putExtra("id",user.id);
                startActivity(intent);
                finish();



            }
        });



        btnEditCrushInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInfomationActivity.this,"Chỉnh sửa thông tin Crush", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfomationActivity.this,lookingPeopleActivity.class);
                intent.putExtra("id",user.id);
                startActivity(intent);
                finish();
            }
        });

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isGetImageSuccessfulFromGallery = false;
                isAvatarCheck = true;
                isAddPhotoCheck = false;
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                }
                else
                    openGallery();

            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgUserPhoto.callOnClick();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isGetImageSuccessfulFromGallery = false;
                isAddPhotoCheck = true;
                isAvatarCheck = false;
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                }
                else
                    openGallery();

            }
        });

    }


    void updateImage(ImageView imageView){
        imageView.setImageURI(pickedImgUri);
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(this, btnSetting);
        popupMenu.getMenuInflater().inflate(R.menu.menu_in_user, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.accountInfo:
//                        Toast.makeText(UserInfomationActivity.this,"Thông tin tài khoản",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserInfomationActivity.this,manageAcount.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.changePassword:
                        //Toast.makeText(UserInfomationActivity.this,"Thay đổi mật khẩu",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(UserInfomationActivity.this,changePasswordActivity.class);
                        intent2.putExtra("id",id);
                        startActivity(intent2);
                        finish();break;
                    case R.id.logout:
                        SaveLogin saveLogin = new SaveLogin(getAndroidId(),id,false);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("SaveLogin").child(saveLogin.idPhone).setValue(saveLogin);

                        Intent intent3 = new Intent(UserInfomationActivity.this,WelcomeActivity.class);
                        startActivity(intent3);
                        finish();break;

                }


                return false;
            }
        });
        popupMenu.show();
    }

    void saveUser(){
        FirebaseDatabase.getInstance().getReference().child("User").child(user.id).setValue(user);
    }

    public String getAndroidId(){
        String androidId =Secure.getString(getContentResolver(),
                Secure.ANDROID_ID);
        return  androidId;
    }

    void circularAvatarImage(ImageView imageView){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.chipuavatar);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);
    }


    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(UserInfomationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserInfomationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }

            else
            {
                ActivityCompat.requestPermissions(UserInfomationActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else{
            openGallery();

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   Toast.makeText(UserInfomationActivity.this,mImageUrls.get(0).imageView.getDrawable()+"",Toast.LENGTH_SHORT).show();
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            if(isAvatarCheck){
                //avatar.setImageURI(pickedImgUri);
                isAvatarCheck = false;
                uploadFile(0);
            }else if(isAddPhotoCheck){
//
                uploadFile(1);
                initRecyclerView();


                isAddPhotoCheck = false;

            }




        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(final int type) {


        mStorageRef = FirebaseStorage.getInstance().getReference("Images");


//        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://image-41577.appspot.com/Images/");
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        StorageTask mUploadTask;
        if (pickedImgUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(pickedImgUri));

            mUploadTask = fileReference.putFile(pickedImgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(UserInfomationActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                            //String uploadId = mDatabaseRef.push().getKey();
                            //Toast.makeText(UserInfomationActivity.this,uploadId,Toast.LENGTH_LONG).show();

                            //mDatabaseRef.child(uploadId).setValue(taskSnapshot.getDownloadUrl().toString());
                            if(type==0) user.images.avatar=taskSnapshot.getDownloadUrl().toString();
                            else if(type == 1) user.images.linkImages.add(taskSnapshot.getDownloadUrl().toString());
                            saveUser();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(UserInfomationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            //Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
