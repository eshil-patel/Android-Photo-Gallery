package com.example.androidproject16;
/**
 *
 */


import java.io.Serializable;

/**
 * @author Imrhankhan Shajahan & Eshil Patel
 *
 */
public class Tag implements Serializable{

    private static final long serialVersionUID = -2158173759556195914L;

    private String name;
    private String value;
    /**Constructor for Tag
     * @param name name of tag
     * @param value value of tag
     */
    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }
    /** get name of tag
     * @return
     */
    public String getName() {
        return name;
    }
    /**set name of tag
     * @param name name of tag
     */
    public void setName(String name) {
        this.name = name;
    }
    /**Get value of tag
     * @return
     */
    public String getValue() {
        return value;
    }
    /**set value of tag
     * @param value string name of value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * custom equals method for tags
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object a) {
        if (a.getClass() != this.getClass()){
            return false;
        }
        Tag b = (Tag)a;
        if (this.name.equalsIgnoreCase(b.name) && this.value.equalsIgnoreCase(b.value)){
            return true;
        }
        return false;
    }
    /* (non-Javadoc)
     * custom toString for tags
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return ("(" + this.name+ ", " + this.value+")");
    }
}

