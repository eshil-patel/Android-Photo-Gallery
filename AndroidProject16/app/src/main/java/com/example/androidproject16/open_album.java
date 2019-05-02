package com.example.androidproject16;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.URI;

public class open_album extends AppCompatActivity {
    public static User user;
    public static String album;
    public static final int photo_request_code=1;
    public static void init(User u,String albumname){
        user=u;
        album=albumname;
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
                    
                }
            }
        }
    }
}
