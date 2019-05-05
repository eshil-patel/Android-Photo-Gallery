package com.example.androidproject16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class display_Photo extends AppCompatActivity {
    public static User user;
    public static Album album;
    public static int currentImg = -1;

    public static void init(User u,String a,int cImg){
        user = u;
        album = user.getAlbum(a);
        currentImg = cImg;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__photo);
        updateScreen();
        activateButtons();
    }

    private void updateScreen(){
        if (currentImg == -1){
            showAlert("INVALID IMG");
            return;
        }
        ImageView m = findViewById(R.id.dispImg);
        Photo toDisplay= album.getPhoto(currentImg);
        Bitmap bitmap = BitmapFactory.decodeFile(toDisplay.getPath());
        m.setImageBitmap(bitmap);

    }
    public void activateButtons(){
        Button m1 = findViewById(R.id.nextImgId);
        Button m2 = findViewById(R.id.prevImgId);
        m1.setEnabled(false);
        m2.setEnabled(false);
        if (currentImg < album.getNumPhotos()-1){
            m1.setEnabled(true);
        }
        if (currentImg > 0){
            m2.setEnabled(true);
        }
    }
    public void nextImg(View view){
        currentImg++;
        updateScreen();
        activateButtons();
    }
    public void prevImg(View view){
        currentImg--;
        updateScreen();
        activateButtons();
    }
    public void showAlert(String text){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.show();
    }

}
