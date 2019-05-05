package com.example.androidproject16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        EditText t1n = findViewById(R.id.tag1Name);
        EditText t1v = findViewById(R.id.tag1Value);
        EditText t2n = findViewById(R.id.tag2Name);
        EditText t2v = findViewById(R.id.tag2Value);

        t1n.setText("");
        t1v.setText("");
        t2n.setText("");
        t2v.setText("");


        if (toDisplay.getTags().size() >= 1){
            t1n.setText(toDisplay.getTags().get(0).getName());
            t1v.setText(toDisplay.getTags().get(0).getValue());
        }
        if (toDisplay.getTags().size() == 2){
            t2n.setText(toDisplay.getTags().get(1).getName());
            t2v.setText(toDisplay.getTags().get(1).getValue());
        }
        System.out.println(toDisplay.getTags());
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
    public void editTags(View view){
        Photo photo= album.getPhoto(currentImg);

        EditText t1n = findViewById(R.id.tag1Name);
        EditText t1v = findViewById(R.id.tag1Value);
        EditText t2n = findViewById(R.id.tag2Name);
        EditText t2v = findViewById(R.id.tag2Value);

        if(t2n.getText().toString().isEmpty() ){
            photo.removeTags(1);
            return;
        }
        if (t1n.getText().toString().isEmpty()){
            photo.removeTags(0);
            return;
        }

        if (t1n.getText().toString().equalsIgnoreCase("person") || t1n.getText().toString().equalsIgnoreCase("location")){
            if (t1v.getText().toString().isEmpty()){
                showAlert("TAG VALUE MUST BE NONNULL");
                return;
            }
            photo.editTag(0,t1n.getText().toString(),t1v.getText().toString());
            DataSaver.save(this,user);
        }else{
            showAlert("TAGS MUST BE 'PERSON' OR 'LOCATION'");
            return;
        }
        if(t2n.getText().toString().isEmpty()){
            return;
        }
        if (t2n.getText().toString().equalsIgnoreCase("person") || t2n.getText().toString().equalsIgnoreCase("location")){
            if (t2v.getText().toString().isEmpty()){
                showAlert("TAG VALUE MUST BE NONNULL");
                return;
            }
            photo.editTag(1,t2n.getText().toString(),t2v.getText().toString());
            DataSaver.save(this,user);
        }else{
            showAlert("TAGS MUST BE 'PERSON' OR 'LOCATION'");
            return;
        }
    }
    public void showAlert(String text){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.show();
    }

}
