package com.example.clientapp;


    public class User {
        public String name, email, password ,latitude,longitude;

        public User(){

        }

        public User(String name, String email, String password, String latitude, String longitude) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.latitude = latitude;
            this.longitude = longitude;

        }
    }
