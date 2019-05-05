package com.example.androidproject16;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;

public class search_photos extends AppCompatActivity {
    public static User user;

    public static void init(User u){
        user=u;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photos);
    }
    public void search(View view){
        EditText tag1=findViewById(R.id.tag1);
        EditText tag2=findViewById(R.id.tag2);
        EditText val1=findViewById(R.id.val1);
        EditText val2=findViewById(R.id.val2);
        String t1=tag1.getText().toString();
        String t2=tag2.getText().toString();
        String v1=val1.getText().toString();
        String v2=val2.getText().toString();
        ArrayList<Photo> searchResults = null;
        if(t1.equals("") || v1.equals("")) {
            showAlert("Please make sure to enter in all relevant fields for tag 1 and value 1");
            return;
        }
        else if(t2.equals("") && t2.equals("")) {
            searchResults=user.getPhotosByTag(t1, v1);
        }
        else if(!(t1.equals(""))||!(v1.equals(""))||!(t2.equals(""))||!(v2.equals(""))) {
            RadioButton or = findViewById(R.id.or);
            RadioButton and = findViewById(R.id.and);
            boolean isOr=or.isSelected();
            boolean isAnd=and.isSelected();
            if(isOr && isAnd) { //both selected
                showAlert("Please select only one of or/and");
                return;
            }
            if(!(isOr) && !(isAnd) && !(t2.equals("")) && !(v2.equals(""))) {
//				System.out.println(t2);
                showAlert("Please select or/and as the search method");
                return;
            }
            if(isOr) {
                searchResults=user.getPhotosByTag(t1, v1, t2, v2, "or");
            }
            if(isAnd) {
                searchResults = user.getPhotosByTag(t1, v1, t2, v2, "and");
            }
        }
        else {
            showAlert("Please make sure to enter in all relevant fields for tag 2 and value 2");
            return;
        }
        // set the linear layout with the photos in the results
        updatelayout(searchResults);

    }
    public void showAlert(String text){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.show();
    }
    public void updatelayout(ArrayList<Photo> photos){

    }
}
