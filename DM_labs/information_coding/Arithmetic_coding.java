package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        /*BigDecimal justI = new BigDecimal(1);
        for (int i = 0; i < 4; i++) {
            justI = justI.divide(BigDecimal.valueOf(100));
            justI = justI.multiply(BigDecimal.valueOf(4));
            System.out.println(justI);
        }*/

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] mas = new int[n];
        scanner.nextLine();
        String string = scanner.nextLine();
        for (int i = 0; i < string.length(); i++) {
            mas[(int) string.charAt(i) - 97]++;
        }
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            System.out.print(mas[i] + " ");
        }
        System.out.println();
        int[] masPrefx = new int[n + 1];
        masPrefx[0] = 0;
        for(int i = 1; i <= n; i++) {
            masPrefx[i] = masPrefx[i - 1] + mas[i - 1];
            //System.out.print(masPrefx[i] + " ");
        }
        //System.out.println();

        //BigDecimal length = new BigDecimal(string.length());
        BigDecimal left = new BigDecimal(0), right = new BigDecimal(1);
        for(int i = 0; i < string.length(); i++) {
            //System.out.println(left + " " + right);
            BigDecimal justInt;
            justInt = right.subtract(left);
            justInt = justInt.divide(BigDecimal.valueOf(string.length()), 444, RoundingMode.HALF_UP);
            //System.out.println(justInt);
            //left += justInt * masPrefx[(int) string.charAt(i) - 97];
            left = left.add(justInt.multiply(BigDecimal.valueOf(masPrefx[(int) string.charAt(i) - 97])));
            //right = left + justInt * mas[(int) string.charAt(i) - 97];
            right = left.add(justInt.multiply(BigDecimal.valueOf(mas[(int) string.charAt(i) - 97])));
            //System.out.println(left + " " + right);
        }
        //System.out.println(left + " " + right);

        /*int highLevel = (int) Math.ceil(Math.log10(Math.pow(right - left, -1)) / Math.log10(2));
        if (highLevel < 1) {
            highLevel = 1;
        }*/
        //System.out.println(highLevel);
        BigDecimal pows = new BigDecimal(1);
        for (int q = 1; q < 4000; q++) {
            pows = pows.multiply(new BigDecimal(2));
            BigDecimal reesL = left.multiply(pows).setScale(0, RoundingMode.CEILING);
            BigDecimal reesR = right.multiply(pows).setScale(0, RoundingMode.FLOOR);
            //System.out.println(rees);
            if ( (reesL.min(right.multiply(pows)).compareTo(right.multiply(pows)) != 0)
                    && (reesR.max(left.multiply(pows)).compareTo(reesR) == 0) /*&& (rees.compareTo(right.multiply(pows)) != 0)*/ ) {
            //if ((Math.ceil(left * Math.pow(2, q)) / Math.pow(2, q) < right) && (Math.floor(right * Math.pow(2, q))) / Math.pow(2, q) >= left) {
                //System.out.println(((int) Math.ceil(left * Math.pow(2, q))) + " : " + ((int) Math.floor(right * Math.pow(2, q))));
                StringBuilder res = new StringBuilder(reesL.toBigInteger().toString(2));
                while (res.length() < q) {
                    res.insert(0, "0");
                }
                System.out.println(res);
                return;
            }
        }

        System.out.println(mas[-1]);// :)

    }
}