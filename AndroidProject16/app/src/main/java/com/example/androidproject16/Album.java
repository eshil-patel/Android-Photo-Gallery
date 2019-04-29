package com.example.test_photo_app;

/*
  @author Imrhankhan Shajahan & Eshil Patel
 */



import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Album implements Serializable{
    /**
     * Photos is an ArrayList of the Photo object. Consists of all the photos in the album
     * presetTagNames are the TagNames that the user can choose from.
     * minDate is the minimum date of all the photos.
     * maxDate is the maximum date of all the photos.
     */
    private static final long serialVersionUID = 4790223858908565017L;
    private String name;
    private int numPhotos;
    private String dateRange;

    ArrayList <Photo> Photos;
    ArrayList <String> presetTagNames;
    Date minDate;
    Date maxDate;
    /**
     * Constructor for Album class.
     * @param name Accepts the name of the album name.
     */
    public Album(String name) {
        this.name = name;
        Photos = new ArrayList<Photo>();
        presetTagNames = new ArrayList<String>();
        presetTagNames.add("Location");
        presetTagNames.add("Person");
        numPhotos=0;
        dateRange = "";
    }
    /**
     * Adds the photo to the album.
     * @param d Photo to be added
     */
    public void addPhoto(Photo d){
        Photos.add(d);
        numPhotos++;
        updateDates();
    }
    /**
     * Removes the photos from Photos
     * @param d The photo to be removed.
     */
    public void removePhoto(Photo d){
        Photos.remove(d);
        numPhotos--;
        updateDates();
    }
    /**
     *Removes the photos from Photos
     * @param d The index of the photo to be removed.
     */
    public void removePhoto(int d){
        Photos.remove(d);
        numPhotos--;
        updateDates();
    }
    /**
     * Helper method to keep the min and maxdate accurate. Is called whenever a photo is added or removed.
     */
    private void updateDates(){
        if (Photos.size() == 0){
            minDate = null;
            maxDate = null;
            dateRange="";
            return;
        }
        if (minDate == null || maxDate == null){
            minDate = Photos.get(0).getDate();
            maxDate = Photos.get(0).getDate();
        }
        for (Photo d:Photos){
            if (d.getDate().compareTo(minDate)<0){
                minDate = d.getDate();
            }
            if (d.getDate().compareTo(maxDate)>0){
                maxDate = d.getDate();
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String min=format.format(minDate);
        String max=format.format(maxDate);
        //dateRange = minDate.toString().concat("+").concat(maxDate.toString());
        dateRange=min.concat("-").concat(max);

    }
    /**
     * Getter method for Album Name.
     * @return Name of the album.
     */
    public String getName() {
        return name;
    }
    /**
     * Getter method for the number of photos in an album.
     * @return Number of photos in the album
     */
    public int getNumPhotos() {
        return numPhotos;
    }
    /**
     * Gets the date range of the photos in the album.
     * @return String DateRange
     */
    public String getDateRange() {
        return dateRange;
    }
    /**
     * Sets the name of the album.
     * @param name
     */
    public void setName(String name) {
        this.name=name;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object a) {
        if(a==null ||!(a instanceof Album)) {
            return false;
        }
        Album album= (Album) a;
        return album.getName().equals(this.name);
    }
    /**
     * Getter method for the arraylist of Photos.
     * @return Array List of Photos in album.
     */
    public ArrayList<Photo> getPhotos() {
        return Photos;
    }
    /**
     * Gets all the photos within the date range.
     * @param start Start Date
     * @param end End Date
     * @return Returns an arraylist of photos within the date range.
     * @throws ParseException
     */
    public ArrayList<Photo> getPhotosByDate(Date start,Date end) throws ParseException{
        ArrayList<Photo> output=new ArrayList<Photo>();
        for (Photo p: Photos) {
            if(p.getDate().compareTo(start)>=0 && p.getDate().compareTo(end)<=0) {
                output.add(p);
            }
        }
        return output;
    }
    /**
     * Gets all the photos with the specified tag.
     * @param t1 Name of the tag
     * @param v1 Value of the tag
     * @return ArrayList of Photos with the matching tags.
     */
    public ArrayList<Photo> getPhotosByTag(String t1,String v1){
        ArrayList<Photo> output = new ArrayList<Photo>();
        Tag tag = new Tag(t1,v1);
        for(Photo p:Photos) {
            if(p.getTags().contains(tag)) {
                output.add(p);
            }
        }
        return output;
    }
    /**
     * Returns an ArrayList of photos that have matching tags.
     * @param t1 Name of tag 1
     * @param v1 Value of tag 1
     * @param t2 Name of tag 2
     * @param v2 Value of Tag 2
     * @param comp And or Or. Conjunction or disjunction.
     * @return ArrayList of Photos with matching tags.
     */
    public ArrayList<Photo> getPhotosByTag(String t1, String v1, String t2, String v2, String comp){
        ArrayList<Photo> output = new ArrayList<Photo>();
        Tag tag1=new Tag(t1,v1);
        Tag tag2=new Tag(t2,v2);
        for(Photo p:Photos) {
            ArrayList<Tag> tags = p.getTags();
            if(comp.equals("or")) {
                if(tags.contains(tag1) || tags.contains(tag2)) {
                    output.add(p);
                }
            }
            else {
                if(tags.contains(tag1) && tags.contains(tag2)) {
                    output.add(p);
                }
            }
        }
        return output;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return(Photos.toString());
    }
    /**
     * Getter method for PresetTagNames
     * @return ArrayList of PresetTagNames
     */
    public ArrayList<String> getPresetTagNames() {
        return presetTagNames;
    }
    /**
     * Adds a preset tag.
     * @param presetTagName Adds this tag to the PresetTagList.
     */
    public void addPresetTagName(String presetTagName) {
        presetTagNames.add(presetTagName);
    }
    /**
     * Deletes a preset tag from the list.
     * @param presetTagName Name to be deleted from the list.
     */
    public void deletePresetTagName(String presetTagName) {
        presetTagNames.remove(presetTagName);
    }
}

