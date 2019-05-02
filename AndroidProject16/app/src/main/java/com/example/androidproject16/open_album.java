package com.example.androidproject16;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.URI;

public class open_album extends AppCompatActivity {
    public static User user;
    public static Album album;
    public static final int photo_request_code=1;
    public static void init(User u,String a){
        user=u;
        album=user.getAlbum(a);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        // will have initialize the view for whatever photos there are, and then give options
    }
    public void addPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,photo_request_code);
    }
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode== Activity.RESULT_OK){
            switch(requestCode){
                case photo_request_code:
                    Uri image = data.getData();
                    System.out.println("this worked up to here");
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(image, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    cursor.close();
                    Photo newphoto=new Photo(path);
                    album.addPhoto(newphoto);
                    System.out.println("This worked");
                }
            }
        }
        public void updatelayout(){
        
        }
    }


