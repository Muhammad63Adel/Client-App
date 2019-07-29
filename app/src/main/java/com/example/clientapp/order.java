package com.example.clientapp;

public class order {


    private String red;
    private String green;
    private String yellow;
    private Client client;


    order(){

    }
    order( String r, String g, String y,Client client){

        this.red = r;
        this.green = g;
        this.yellow = y;
        this.client = client;

    }

    public String getRed(){return red;}
    public String getGreen(){return green;}
    public String getYellow(){return yellow;}
    public Client getClient(){return client;}


}
