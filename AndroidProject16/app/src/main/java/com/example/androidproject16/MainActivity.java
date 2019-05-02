package com.example.androidproject16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Album> albums;
    public static String albumselected="";
    public static User user;
    public static ArrayAdapter<String> adapter;
    public void init(User user){
        if(user==null){
            albums=new ArrayList<Album>();
        }
        else{
            albums=user.getAlbums();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // R.layout refers to the activity_main xml that is in res/layout
        // gui helps create the activity_main xml layout and look
        setContentView(R.layout.activity_main);
        // set the listView with the album names
        user = DataSaver.load(MainActivity.this);
        ArrayList<String> albumnames= new ArrayList<>();
        init(user);
        for (Album a:albums){
            String name = a.getName();
            System.out.println(name);
            albumnames.add(name);
        }
        final ListView listview = findViewById(R.id.albumname);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,albumnames);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                v.setSelected(true);
                albumselected= (String) listview.getItemAtPosition(position);
            }
        });
    }
    public void open(View view){
        // initialize the data for openalbum (send the entire user object
        open_album.init(user,albumselected);
        albumselected=null;
        Intent intent = new Intent(this,open_album.class);
        // send in the album to the next page to use
        startActivity(intent);
    }
    public void create(View view){
        ListView listview = findViewById(R.id.albumname);
        EditText newalbum = findViewById(R.id.createalbum);
        System.out.println(newalbum.getText().toString());
        Album toadd=new Album(newalbum.getText().toString());
        // check if the album is already in the list
        user.addAlbum(toadd);
        DataSaver.save(MainActivity.this,user);
        setListView();
    }
    public void delete(View view){
        ListView listview = findViewById(R.id.albumname);
        if(albumselected==null){
            System.out.println("this is null, please choose an album");
            return;
        }
        String o=albumselected;
        Album todelete= new Album(albumselected);
        user.removeAlbum(todelete);
        DataSaver.save(MainActivity.this,user);
        albumselected=null;
        setListView();
    }
    public void rename(View view){
        ListView listview = findViewById(R.id.albumname);
        if(albumselected==null){
            System.out.println("this album is null");
            return;
        }
        String old=albumselected;
        EditText newname = findViewById(R.id.renamealbum);
        Album a = new Album(albumselected);
        user.renameAlbum(a,newname.getText().toString());
        albumselected=null;
        DataSaver.save(MainActivity.this,user);
        setListView();
    }
    public void setListView(){
        ListView listview = findViewById(R.id.albumname);
        ArrayList<String> albumnames= new ArrayList<>();
        init(user);
        for (Album al:user.getAlbums()){
            String name = al.getName();
            System.out.println(name);
            albumnames.add(name);
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,albumnames);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
