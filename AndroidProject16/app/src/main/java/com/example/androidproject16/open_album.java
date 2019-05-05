package com.example.androidproject16;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.net.URI;
import java.util.ArrayList;

public class open_album extends AppCompatActivity implements View.OnClickListener {
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
        updateLayout();
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
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(image, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    cursor.close();
                    Photo newphoto=new Photo(path);
                    album.addPhoto(newphoto);
                    DataSaver.save(open_album.this,user);
//                    updatelayout();
                    updateLayout();
                }
            }
        }
/*        public void updatelayout(){
            //clear the linearlayout first
            LinearLayout gallery = findViewById(R.id.displayphotos);
            gallery.removeAllViews();
            for(int i=0;i<album.getNumPhotos();i++){
                System.out.println("Image "+i);
                Photo todisplay=album.getPhoto(i);
                ImageView image = new ImageView(this);
                Bitmap bitmap = BitmapFactory.decodeFile(todisplay.getPath());
                image.setImageBitmap(bitmap);
                //image.getLayoutParams().height=40;
                //image.getLayoutParams().width=60;
                gallery.addView(image);

            }
        }*/
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

        }
    }


