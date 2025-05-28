package project;

import java.text.NumberFormat;
import java.util.Scanner;

public class mortgage {
    public static void main(String[] args) {        
        final byte Months_in_year = 12;
        final byte percent = 100;
        Scanner a = new Scanner(System.in);
        System.out.print("Principal : ");
        double P = a.nextDouble();
        System.out.print("Annual Interest Rate : ");
        double r = a.nextDouble()/(Months_in_year*percent);
        System.out.print("Period (Years) : ");
        int n = a.nextInt()*Months_in_year;

        double result = P*Math.pow(r+1, n)*r/(Math.pow(r+1, n)-1);
        String result_final = NumberFormat.getCurrencyInstance().format(result);
        System.out.println("Mortgage : " + result_final);
    }
}
