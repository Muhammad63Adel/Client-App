package com.example.clientapp;

public class Client {


    private String email ;
    private String name ;
    private String latitude ;
    private String longitude ;

    Client(){

    }

    Client(String email ,String name ,String latitude, String longitude){

        this.email = email;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;

    }

      String getemail(){return email;}
      String getname(){return name;}
      String getLatitude(){return latitude;}
      String getLongitude(){return longitude;}


}
