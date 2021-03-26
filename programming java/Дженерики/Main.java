package expression;

import expression.generic.GenericTabulator;
import expression.generic.Tabulator;

public class Main {

    public static void main(String[] args) throws Exception {
        Tabulator tabulator = new GenericTabulator();
        Object[][][] objects = tabulator.tabulate(args[0], args[1], -2, 2, -2, 2, -2, 2);
        printTable(objects, 5, 5, 5);
    }


    private static void printTable(Object[][][] objects, int xSize, int ySize, int zSize) {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                for (int k = 0; k < zSize; k++) {
                    System.out.print("i = " + i + "; j = " + j + "; k = " + k);
                    System.out.println("; Object[" + i + "][" + j + "][" + k +"] = " + objects[i][j][k]);
                }
            }
        }
    }
}