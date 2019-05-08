package com.example.androidproject16;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;

public class open_album extends AppCompatActivity implements View.OnClickListener {
    public static User user;
    public static Album album;
    public static final int photo_request_code=1;
    public int currentImg = -1;
    public static void initvar(User u,String a){
        user=u;
        album=user.getAlbum(a);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        Button m1 = findViewById(R.id.rmvPhoto);
        m1.setEnabled(false);
        Button m2 = findViewById(R.id.dispPhoto);
        m2.setEnabled(false);
        ActivityCompat.requestPermissions(open_album.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        updateLayout();

    }
    @Override
    protected void onResume(){
        super.onResume();
        user=DataSaver.load(open_album.this);
        album=user.getAlbum(album.getName());
        updateLayout();
    }
    public void addPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,photo_request_code);
    }
    public void removePhoto(View view){
        System.out.println("REMOVE PHOTO BUTTON PRESSED");
        user.getAlbum(album.getName()).removePhoto(currentImg);
        album=user.getAlbum(album.getName());
        if (currentImg == album.getNumPhotos()){
            currentImg--;
        }
        if (album.getNumPhotos() == 0){
            Button m1 = findViewById(R.id.rmvPhoto);
            m1.setEnabled(false);
            Button m2 = findViewById(R.id.dispPhoto);
            m2.setEnabled(false);
        }
        DataSaver.save(this,user);
        updateLayout();
    }
    public void displayPhoto(View view){
        System.out.println("DISPLAY PHOTO BUTTON PRESSED");
        display_Photo.init(user,album.getName(),currentImg);
        Intent intent = new Intent(this,display_Photo.class);
        // send in the album to the next page to use
        startActivity(intent);
    }
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode== Activity.RESULT_OK){
            switch(requestCode){
                case photo_request_code:
                    Uri image = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(image, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    cursor.close();
                    Photo newphoto=new Photo(path);
                    //album.addPhoto(newphoto); // not putting it into the right place?
                    user.getAlbum(album.getName()).addPhoto(newphoto);
                    album=user.getAlbum(album.getName());
                    DataSaver.save(open_album.this,user);
                    updateLayout();
                }
            }
        }
        public void updateLayout(){
            TableLayout grid = findViewById(R.id.imgTable);
            ArrayList<Photo> temp;
            temp=album.getPhotos();
            grid.removeAllViews();
            int r = 0;
            int j = 0;
            TableRow row = new TableRow(this);
            System.out.println("WIDTH = " + grid.getWidth());
            int m = grid.getWidth();
            TableRow.LayoutParams lp = new TableRow.LayoutParams(m/3,m/3,TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setId(r);

            for (Photo i: temp){
                if (j == 3){
                    grid.addView(row,r);
                    r++;
                    j = 0;
                    row = new TableRow(this);
                    row.setId(r);
                    row.setLayoutParams(lp);
                }
                System.out.println("Image "+i);
                Photo toDisplay= i;
                ImageView image = new ImageView(this);
                Bitmap bitmap = BitmapFactory.decodeFile(toDisplay.getPath());
                image.setImageBitmap(bitmap);
                image.setLayoutParams(lp);
                image.setId(3*r + j);
                image.setOnClickListener(this);
                row.addView(image,j);
                j++;
            }
            grid.addView(row,r);
        }
        @Override
        public void onClick(View v){
            int id = v.getId();
            System.out.println(id);
            currentImg = id;
            Button m1 = findViewById(R.id.rmvPhoto);
            m1.setEnabled(true);
            Button m2 = findViewById(R.id.dispPhoto);
            m2.setEnabled(true);

        }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        updateLayout();
                }
                else {
                    Toast.makeText(open_album.this, "Permission denied, please allow permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    }


