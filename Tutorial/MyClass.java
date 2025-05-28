package hw_pd2.Tutorial;

import java.io.*;

public class MyClass {
    // Fields
    private int myField;

    // Constructor
    public MyClass(int field) {
        myField = field;
    }

    // Methods
    public void myMethod() {
        System.out.println(myField);
    }

    // Main method
    public static void main(String[] args) {
        int rrqs = 127;
        byte rrq = (byte)rrqs;
        int[] intr = new int[2];
        System.out.println(intr[0]);
        MyClass myObject = new MyClass(10);
        System.out.println(rrq);
        myObject.myMethod();
    }
}