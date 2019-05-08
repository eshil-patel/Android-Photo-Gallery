package com.example.androidproject16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

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
    protected void onResume(){
        super.onResume();
        user=DataSaver.load(search_photos.this);
    }
    public void search(View view){
        EditText tag1=findViewById(R.id.tag1);
        EditText tag2=findViewById(R.id.tag2);
        EditText val1=findViewById(R.id.val1);
        EditText val2=findViewById(R.id.val2);
        String t1=tag1.getText().toString().trim();
        String t2=tag2.getText().toString().trim();
        String v1=val1.getText().toString().trim();
        String v2=val2.getText().toString().trim();
        tag1.setText("");
        tag2.setText("");
        val1.setText("");
        val2.setText("");
        ArrayList<Photo> searchResults = null;
        if(t1.equals("") || v1.equals("")) {
            showAlert("Please make sure to enter in all relevant fields for tag 1 and value 1");
            return;
        }
        else if(t2.equals("") && t2.equals("")) {
            searchResults=user.getPhotosByTag(t1, v1);
        }
        else if(!(t1.equals(""))||!(v1.equals(""))||!(t2.equals(""))||!(v2.equals(""))) {
            CheckBox or = findViewById(R.id.or);
            CheckBox and = findViewById(R.id.and);
            boolean isOr=or.isChecked();
            boolean isAnd=and.isChecked();
            if(isOr){
                or.toggle();
            }
            if(isAnd){
                and.toggle();
            }
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
        TableLayout grid = findViewById(R.id.searchresults);
        grid.removeAllViews();
        int r = 0;
        int j = 0;
        TableRow row = new TableRow(this);
        System.out.println("WIDTH = " + grid.getWidth());
        int m = grid.getWidth();
        TableRow.LayoutParams lp = new TableRow.LayoutParams(m/3,m/3,TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setId(r);

        for (Photo i: photos){
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
            row.addView(image,j);
            j++;
        }
        grid.addView(row,r);
    }
}
