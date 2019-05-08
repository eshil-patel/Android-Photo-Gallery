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
import android.widget.AdapterView;
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
    @Override
    protected void onResume(){
        super.onResume();
        user=DataSaver.load(display_Photo.this);
        // need to update currentImg
    }
    private void updateScreen(){
        user = DataSaver.load(display_Photo.this);
        album = user.getAlbum(album.getName());
        activateButtons();
        if (currentImg == -1){
            showAlert("INVALID IMG");
            return;
        }
        if (currentImg >= album.getNumPhotos()){
            return;
        }
        ImageView m = findViewById(R.id.dispImg);
        Photo toDisplay= user.getAlbum(album.getName()).getPhoto(currentImg);
        Bitmap bitmap = BitmapFactory.decodeFile(toDisplay.getPath());
        m.setImageBitmap(bitmap);

        EditText t1n = findViewById(R.id.tagNameText);
        EditText t1v = findViewById(R.id.tagValueText);

        t1n.setText("");
        t1v.setText("");

        if (currentTag!=-1){
            t1n.setText(toDisplay.getTags().get(currentTag).getName());
            t1v.setText(toDisplay.getTags().get(currentTag).getValue());
        }

        final ListView tagList = findViewById(R.id.tagList);
        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentTag = i;
                updateScreen();
                System.out.println(currentTag + " " +i);
            }
        });


        adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,toDisplay.getTagStrings());
        tagList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        System.out.println(toDisplay.getTags());
    }
    public void updateSelectTag(View v){
        ListView tagList = findViewById(R.id.tagList);
        currentTag = tagList.getSelectedItemPosition();
        System.out.println(currentTag);
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
        currentTag = -1;
        updateScreen();
        activateButtons();
    }
    public void prevImg(View view){
        currentImg--;
        currentTag = -1;
        updateScreen();
        activateButtons();
    }

    public void addTag(View view){
        EditText t1n = findViewById(R.id.tagNameText);
        EditText t1v = findViewById(R.id.tagValueText);
        String tagn = t1n.getText().toString();
        String tagv = t1v.getText().toString();
        if (tagn.isEmpty() || tagv.isEmpty()){
            showAlert("Name and value must be nonnull!");
            return;
        }
        Photo toDisplay= user.getAlbum(album.getName()).getPhoto(currentImg);
        if (tagn.equalsIgnoreCase("Person") || tagn.equalsIgnoreCase("Location")){
            user.getAlbum(album.getName()).getPhoto(currentImg).addTags(new Tag(tagn,tagv));
            toDisplay=user.getAlbum(album.getName()).getPhoto(currentImg);
            DataSaver.save(this,user);
            updateScreen();
        }else{
            showAlert("Tag name should be 'Person' or 'Location'");
        }
    }
    public void editTag(View view){
        EditText t1n = findViewById(R.id.tagNameText);
        EditText t1v = findViewById(R.id.tagValueText);
        String tagn = t1n.getText().toString();
        String tagv = t1v.getText().toString();
        if (tagn.isEmpty() || tagv.isEmpty()){
            showAlert("Name and value must be nonnull!");
            return;
        }
        if (currentTag == -1){
            showAlert("Select a tag first");
            return;
        }
        Photo toDisplay= album.getPhoto(currentImg);
        if (tagn.equalsIgnoreCase("Person") || tagn.equalsIgnoreCase("Location")){
            user.getAlbum(album.getName()).getPhoto(currentImg).editTag(currentTag,tagn,tagv);
            toDisplay=user.getAlbum(album.getName()).getPhoto(currentImg);
            DataSaver.save(this,user);
            updateScreen();
        }else{
            showAlert("Tag name should be 'Person' or 'Location'");
        }

    }
    public void deleteTag(View view){
        Photo photo = album.getPhoto(currentImg);
        if (currentTag!=-1) {
            user.getAlbum(album.getName()).getPhoto(currentImg).removeTags(currentTag);
            photo=user.getAlbum(album.getName()).getPhoto(currentImg);
            if (currentTag == photo.getTags().size()){
                currentTag--;
            }
            DataSaver.save(this,user);
            updateScreen();
        }
    }
    public void moveAlbum(View view){
        EditText mA = findViewById(R.id.albumNameText);
        String j = mA.getText().toString();
        if (j.isEmpty()){
            showAlert("AlbumName must be nonempty");
            return;
        }else{
            if ( user.hasAlbum(j)){
                Photo ph = user.getAlbum(album.getName()).getPhoto(currentImg);
                System.out.println(album.getNumPhotos());
                user.getAlbum(album.getName()).removePhoto(currentImg);
                user.getAlbum(j).addPhoto(ph);
                System.out.println(album.getNumPhotos());
                mA.setText("");
                if (currentImg == album.getNumPhotos()){
                    currentImg--;
                }
                DataSaver.save(this,user);
                updateScreen();

            }else{
                showAlert("Album does not exist");
                return;
            }
        }
    }
    public void showAlert(String text){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.show();
    }


}
