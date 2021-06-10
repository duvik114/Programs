
import java.io.*;

public class ReverseHex {
    public static void main(String[] args) {
        Scan scan = new Scan(System.in);
        String[] mas = new String[1000000];
        int max_i = 0;
        for (int i = 0; scan.hasnextLine(); i++) {
            String str = scan.nextLine();
            if(str.equals("end")) {
                break;
            } else {
                Scan sc = new Scan(str);
                StringBuilder inverseString = new StringBuilder();
                while (sc.hasNext()) {
                    inverseString.append(new StringBuilder(sc.next()).reverse()).append(" ");
                }
                int l = inverseString.length();
                if(l > 0) {
                    mas[i] = inverseString.substring(0, l - 1);
                } else {
                    mas[i] = "";
                }
                max_i++;
            }
        }
        for (int i = max_i - 1; i >= 0; i--) {
            for (int j = mas[i].length() - 1; j >= 0; j--) {
                System.out.print(mas[i].charAt(j));
            }
            System.out.println();
        }
    }
}