package com.example.test_photo_app;



import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class DataSaver {
    /**
     * This method is dedicated to serializing the UserList object and saves to disk using the Serializable interface
     * @param user A UserList object consisting of all the users -> primary object that permeates through all other controllers and is also the object being serialized. Consists of all application information.
     *
     */
    public static void save(Context context,User user){
        try
        {

            FileOutputStream fileOutputStream = context.openFileOutput("UserData.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            fileOutputStream.close();

        }

        catch(IOException ex) {
            System.out.println("IOException is caught");
        }
    }
    /**
     * Responsible for deserializing data stored in disk and create a UserList object consisting of all the users.
     * @return A userlist after deserializing the data. Consists of all application data.
     */
    public static User load(Context context){
        User user = null;
        try {

            File file = new File(context.getFilesDir(),"UserData.dat");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileInputStream FIS = context.openFileInput("UserData.dat");
            if(FIS.available()==0){
                user = new User("phone");
                return user;
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(FIS);
            user = (User) objectInputStream.readObject();
            objectInputStream.close();
            FIS.close();
        }
        catch(Exception e) {
            		e.printStackTrace();
            //System.out.println("There was an error deserializing the data");
        }
        return user;
    }
}

