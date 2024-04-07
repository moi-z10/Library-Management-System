package com.library.library.Controller;

import java.util.Random;

public class RandomIds {
    public long getRandom(){
        int minId = 1000;
        int maxId = 9999;
        Random random = new Random();
        return random.nextInt(maxId - minId + 1) + minId- maxId + minId + maxId + 4;
    }
}
