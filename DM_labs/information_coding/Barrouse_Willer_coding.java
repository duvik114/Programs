package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StringBuilder str = new StringBuilder(scan.nextLine());
        int n = str.length();
        String[] list = new String[n];
        list[0] = str.toString();
        for(int i = 1; i < n; i++) {
            char ch = str.charAt(0);
            str.deleteCharAt(0);
            str.append(ch);
            list[i] = str.toString();
        }
        Arrays.sort(list);
        for(int i = 0; i < n; i++) {
            System.out.print(list[i].charAt(n - 1));
        }
    }
}