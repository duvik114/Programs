package search;

import java.util.ArrayList;

public class BinarySearchMissing {

    // MAS : (mas - отсортирован по НЕвозрастанию && mas[-1] = +oo && mas[mas.size] = -oo)
    /* POST : (R == -1 - r && mas[l] > x && mas[r] < x && r - l == 1) ||
                || (R == r && mas[l] > x && mas[r] == x && r - l == 1) && MAS
    */
    public static int iterBinSearch(ArrayList<Integer> mas, int x) {
        // MAS
        /* l' && r' - значения l && r на предыдущей итерации цикла while */
        int l = -1;
        int r = mas.size();
        int mid;
        // l == l' && r == r' && MAS
        // I : (mas[l] > x && mas[r] <= x && r - l >= 1 && -1 <= l < r <= mas.size() && MAS)
        while (l < r - 1) {
            // I && l == l' && r == r'
            mid = (r + l) / 2;
            // I && mid == (r + l) / 2 && l == l' && r == r'
            // I && mid == (r + l) / 2 && l == l' && r == r'
            if (mas.get(mid) > x) {
                // I && mas.get(mid) > x && mid == (r + l) / 2 && l == l' && r == r'
                l = mid;
                // I && r - l == (r' - l') / 2 && r == r'
            } else {
                // I && mas.get(mid) <= x && mid == (r + l) / 2 && l == l' && r == r'
                r = mid;
                // I && r - l == (r' - l') / 2 && l == l'
            }
            // I && r - l == (r' - l') / 2
        }
        // mas[l] > x && mas[r] <= x && r - l == 1 && MAS
        if (r == mas.size() || mas.get(r) < x) {
            // mas[l] > x && mas[r] < x && r - l == 1 && MAS
            return -1 - r;
        } else {
            // mas[l] > x && mas[r] == x && r - l == 1 && MAS
            return r;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // MAS : (mas - отсортирован по НЕвозрастанию && mas[-1] = +oo && mas[mas.size] = -oo)
    // PRE : (l == -1 && r == mas.size()) - (на первом шаге рекурсии) && MAS
    /* POST : (R == -1 - r && mas[l] > x && mas[r] < x && r - l == 1) ||
                || (R == r && mas[l] > x && mas[r] == x && r - l == 1) && MAS
    */
    // I : (mas[l] > x && mas[r] <= x && r - l >= 1 && -1 <= l < r <= mas.size() && MAS)
    public  static  int recBinSearch(ArrayList<Integer> mas, int x, int l, int r) {
        /* l' && r' - новые значения l и r */
        // I && l' == l && r' == r
        if (!(l < r - 1)) {
            // mas[l] > x && mas[r] <= x && r - l == 1 && MAS
            if (r == mas.size() || mas.get(r) < x) {
                // mas[l] > x && mas[r] < x && r - l == 1 && MAS
                return -1 - r;
            } else {
                // mas[l] > x && mas[r] == x && r - l == 1 && MAS
                return r;
            }
        }
        // I && r - l > 1 && l' == l && r' == r
        int mid = (r + l) / 2;
        // I && mid == (r + l) / 2 && l' == l && r' == r
        if (mas.get(mid) > x) {
            // I && mas.get(mid) > x && mid == (r + l) / 2 && l' == l && r' == r
            l = mid;
            // I && l == mid && r' - l' == (r - l) / 2 && r' == r
        } else {
            // I && mas.get(mid) <= x && mid == (r + l) / 2 && l' == l && r' == r
            r = mid;
            // I && r == mid && r' - l' == (r - l) / 2 && l' == l
        }
        // I && r' - l' == (r - l) / 2
        return recBinSearch(mas, x, l, r);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {

        ArrayList<Integer> mas = new ArrayList<>();
        if (args.length == 0) {
            System.out.println("length of args = 0");
            return;
        }
        for (int i = 1; i < args.length; i++) {
            mas.add(Integer.parseInt(args[i]));
        }
        int res1 = iterBinSearch(mas, Integer.parseInt(args[0]));
        int res2 = recBinSearch(mas, Integer.parseInt(args[0]), -1, mas.size());
        if (res1 == res2) {
            System.out.println(res1);
        } else {
            System.out.println("bruh");
        }

    }
}