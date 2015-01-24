package com.example.jakub.kolonieparse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class Start extends Application {

    @Override
    public void onCreate(){
        Parse.initialize(this, "ylX9nSaXcMoiDDU4Q9OBBWRuv3s9VYvJRXSxSMQm", "iyP1cuJugkMOb6EmLnqOozXWUDyfnkxE3JpDv7xA");


    }

}
