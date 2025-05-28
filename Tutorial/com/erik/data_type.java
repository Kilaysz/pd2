package com.erik;

import java.util.Date;
import java.awt.*;;

public class data_type {
    public static void main(String[] args) {
        int a = 5, b = 9;
        System.out.println(a);
        System.out.println(b);
        // primitive type = int, long, float, byte 
        int age = 30;
        byte age2 = 127; 
        long taik = 123_123_124_213L;
        float price = 123.123F;
        char ok = 'A';
        // reference type: date, point
        Date now = new Date();
        now.getTime();
        System.out.println(now);
        Point okgas = new Point(1, 3);
        Point okgas2 = okgas;
        okgas.x=3;
        System.out.println(okgas2);


         
        
    }
    
}
