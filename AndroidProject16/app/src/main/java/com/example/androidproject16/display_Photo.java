package com.example.androidproject16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


public class display_Photo extends AppCompatActivity {
    public static User user;
    public static Album album;
    public static int currentImg = -1;
    public static int currentTag = -1;
    public static ArrayAdapter<String> adapter;


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

        EditText t1n = findViewById(R.id.tagNameText);
        EditText t1v = findViewById(R.id.tagValueText);

        t1n.setText("");
        t1v.setText("");


        if (toDisplay.getTags().size() >= 1){
            t1n.setText(toDisplay.getTags().get(0).getName());
            t1v.setText(toDisplay.getTags().get(0).getValue());
        }

        ListView tagList = findViewById(R.id.tagList);


        adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,toDisplay.getTags().toString());
        tagList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

    public void addTag(View view){

    }
    public void deleteTag(View view){

    }
    public void moveAlbum(View view){

    }
    public void showAlert(String text){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.show();
    }

}
