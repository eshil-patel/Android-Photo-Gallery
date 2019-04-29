package com.example.test_photo_app;
/**
 * @author Imrhankhan Shajahan & Eshil Patel
 */


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable{
    /**
     * name - name of User
     * Albums - ArrayList of albums the user has
     */
    private static final long serialVersionUID = 3742889052001853904L;
    private String name;
    ArrayList <Album> Albums;
    /**
     * Constructor for User object
     * @param name Accepts User name as the parameter.
     */
    public User(String name){
        this.name = name;
        Albums = new ArrayList<Album>();
    }
    /**
     * Adds an album associated with the user.
     * @param n
     */
    public void addAlbum(Album n){
        Albums.add(n);
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return name;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object user) {
        if(user==null || !(user instanceof User) ) {
            return false;
        }
        User m= (User) user;
        if(this.name.equals(m.name)) {
            return true;
        }
        return false;
    }
    /**
     * Getter methods for Albums
     * @return ArrayList of albums the user has.
     */
    public ArrayList<Album> getAlbums() {
        return Albums;
    }
    /**
     * Getter method for Name
     * @return Name of album
     */
    public String getName() {
        return name;
    }
    /**
     * Removes the album for the user.
     * @param album Album to be removed.
     */
    public void removeAlbum(Album album) {
        Albums.remove(album);
    }
    /**
     * Renames the album
     * @param album Album to be renamed
     * @param newname New name the album needs to be changed to.
     */
    public void renameAlbum(Album album, String newname) {
        Albums.get(Albums.indexOf(album)).setName(newname);
    }
    /**
     * Checks whether the User has the specified album.
     * @param name Album name that is checked.
     * @return Boolean referring to user having albums.
     */
    public boolean hasAlbum(String name) {
        Album album= new Album(name);
        return Albums.contains(album);
    }
    /**
     * Getter method for returning the album associated with the name
     * @param name Name for Album
     * @return Album associated with the name in the User
     */
    public Album getAlbum(String name){
        Album n = new Album(name);
        return Albums.get(Albums.indexOf(n));
    }
    /**
     * Adds a photo to an album.
     * @param albumName Name of album referred to
     * @param i Photo to be added to the album.
     */
    public void addPhoto(String albumName, Photo i){
        Album aN = getAlbum(albumName);
        aN.addPhoto(i);
    }
    /**
     * Method that returns an arrayList of photos by date.
     * @param start Start Date
     * @param end End Date
     * @return ArrayList with photos in range of start and end date.
     * @throws ParseException
     */
    public ArrayList<Photo> getPhotosByDate(String start,String end) throws ParseException{
        ArrayList<Photo> output=new ArrayList<Photo>();
        Date s=new SimpleDateFormat("MM/dd/yyyy").parse(start);
        Date e=new SimpleDateFormat("MM/dd/yyyy").parse(end);
        for(Album a:Albums) {
            ArrayList<Photo> x=a.getPhotosByDate(s, e);
            output.addAll(x);
        }
        return output;
    }
    /**
     * Returns an ArrayList of Users with matching tags.
     * @param t1 Name of tag.
     * @param v1 Value of tag
     * @return ArrayList of photos with matching tags.
     */
    public ArrayList<Photo> getPhotosByTag(String t1,String v1){
        ArrayList<Photo> output = new ArrayList<Photo>();
        for(Album a:Albums) {
            ArrayList<Photo> x=a.getPhotosByTag(t1, v1);
            output.addAll(x);
        }
        return output;
    }
    // and overloaded method for conj/disj tag searching
    /**
     * ALternate get photo by tag
     * @param t1 tag name 1
     * @param v1 value 1
     * @param t2 tag name 2
     * @param v2 value 2
     * @param comp
     * @return
     */
    public ArrayList<Photo> getPhotosByTag(String t1,String v1, String t2,String v2,String comp){
        ArrayList<Photo> output = new ArrayList<Photo>();
        for(Album a: Albums) {
            ArrayList<Photo> x=a.getPhotosByTag(t1, v1, t2, v2, comp);
            output.addAll(x);
        }
        return output;
    }
}

