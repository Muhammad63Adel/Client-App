package com.example.clientapp;


import android.media.Image;

public class product {

    private  String name;
    private  String content;
    private  Integer pic;


    public product(String iname, String cont, int imageId){

        name = iname;
        content = cont;
        pic = imageId;
    }



    public String getname(){return name;}

    public String getnationality(){return content;}

    public int getpic(){return pic;}
}