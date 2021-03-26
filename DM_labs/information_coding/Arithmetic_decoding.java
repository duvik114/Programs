package com.company;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] mas = new int[n];
        int num = 0;
        for(int i = 0; i < n; i++) {
            mas[i] = scanner.nextInt();
            num += mas[i];
        }
        scanner.nextLine();
        String str = scanner.nextLine();
        BigDecimal code = (new BigDecimal(new BigInteger(str, 2))).divide(BigDecimal.valueOf(2).pow(str.length()), 444, RoundingMode.HALF_UP);
        //1100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001110011000111001100011100110001
        BigDecimal left = BigDecimal.valueOf(0), right = BigDecimal.valueOf(1);
        for(int q = 0; q < num; q++) {
            BigDecimal part = right.subtract(left).divide(BigDecimal.valueOf(num), 444, RoundingMode.HALF_UP);
            for (int i = 0; i < n; i++) {
                right = left.add(part.multiply(BigDecimal.valueOf(mas[i])));
                if (right.min(code).compareTo(right) != 0) {
                    System.out.print((char) (i + 97));
                    break;
                }
                left = left.add(part.multiply(BigDecimal.valueOf(mas[i])));
            }
        }
    }
}