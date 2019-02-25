package com.example.inass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("name")
    @Expose
    private String poseName;
    private int size;


    public String getName(){
        return this.poseName;
    }
    public void setName(String positionName){
        this.poseName = poseName;
    }

    public void setSize(int size){this.size = size;}
    public int getSize(){return size;}



}
