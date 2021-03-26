package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        char[] str = scan.nextLine().toCharArray();
        char[] strSort = str.clone();
        int n = str.length;
        Arrays.sort(strSort);
        if(n == 0) {
            return;
        }

        int[] mas = new int[n];
        boolean[] flags = new boolean[n];
        Arrays.fill(flags, true);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if (str[i] == strSort[j] && flags[j]) {
                    flags[j] = false;
                    mas[i] = j;/**/
                    break;
                }
            }
        }

        StringBuilder res = new StringBuilder();
        res.append(str[0]);
        int pos = mas[0];
        for(int i = 1; i < n; i++) {
            res.insert(0, str[pos]);
            pos = mas[pos];
        }

        String[] strings = new String[n];
        for(int i = 0; i < n; i++) {
            char ch = res.charAt(0);
            res.deleteCharAt(0);
            res.append(ch);
            strings[i] = res.toString();
        }

        Arrays.sort(strings);
        System.out.println(strings[0]);
    }
}