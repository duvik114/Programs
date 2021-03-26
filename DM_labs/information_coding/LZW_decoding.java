package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*Map<String, Integer> alph2 = new LinkedHashMap<>();
        for (int i = 97; i <= 122; i++) {
            alph2.put(String.valueOf((char)i), i - 97);
        }
        Scanner scanner2 = new Scanner(System.in);
        String source = scanner2.nextLine();
        StringBuilder buffer1 = new StringBuilder();
        BufferedReader reader = new BufferedReader(new StringReader(source));
        try {
            int ch = reader.read();
            if (ch == -1) {
                return;
            }
            buffer1.append((char) ch);
            ch = reader.read();
            while (ch != -1) {
                String str = buffer1.toString() + (char) ch;
                if (alph2.containsKey(str)) {
                    buffer1.append((char) ch);
                } else {
                    System.out.print(alph2.get(buffer1.toString()) + " ");
                    alph2.put(str, alph2.size());
                    buffer1 = new StringBuilder(String.valueOf((char) ch));
                }
                ch = reader.read();
            }
            System.out.print(alph2.get(buffer1.toString()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/

        Map<Integer, String> alph = new LinkedHashMap<>();
        for (int i = 97; i <= 122; i++) {
            alph.put(i - 97, String.valueOf((char)i));
        }
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int number = scanner.nextInt();
            StringBuilder plusString;
            if (!alph.containsKey(number)) {
                plusString = new StringBuilder(buffer.toString() + buffer.charAt(0));
            } else {
                plusString = new StringBuilder(alph.get(number));
            }
            for (int pos = 0; pos < plusString.length(); pos++) {
                String str = buffer.toString() + plusString.charAt(pos);
                if (alph.containsValue(str)) {
                    buffer.append(plusString.charAt(pos));
                } else {
                    System.out.print(buffer.toString());
                    alph.put(alph.size(), str);
                    buffer = new StringBuilder(String.valueOf(plusString.charAt(pos)));
                }
            }
        }
        System.out.print(buffer.toString());
        /*if (source.equals(res.toString())) {
            System.out.println(" yes");
        } else {
            System.out.println(" no");
        }*/
    }
}
