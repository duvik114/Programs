package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> alph = new LinkedHashMap<>();
        for (int i = 97; i <= 122; i++) {
            alph.put(String.valueOf((char)i), i - 97);
        }
        Scanner scanner = new Scanner(System.in);
        String source = scanner.nextLine();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new StringReader(source));
        try {
            int ch = reader.read();
            if (ch == -1) {
                return;
            }
            buffer.append((char) ch);
            ch = reader.read();
            while (ch != -1) {
                String str = buffer.toString() + (char) ch;
                if (alph.containsKey(str)) {
                    buffer.append((char) ch);
                } else {
                    System.out.print(alph.get(buffer.toString()) + " ");
                    alph.put(str, alph.size());
                    buffer = new StringBuilder(String.valueOf((char) ch));
                }
                ch = reader.read();
            }
            if (alph.containsKey(buffer.toString())) {
                System.out.print(alph.get(buffer.toString()));
            } else {
                System.out.print(alph.get(buffer.toString()));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}