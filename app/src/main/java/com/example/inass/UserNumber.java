package com.example.inass;

import java.util.Random;

public class UserNumber {

    private String id = "";
    private Random n;



    public String getID(){
        for (int i = 0;i<10;i++){
            n = new Random();
            id = id+n.nextInt(999999);
        }

        return id;
    }
}
