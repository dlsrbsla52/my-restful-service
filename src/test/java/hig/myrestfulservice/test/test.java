package hig.myrestfulservice.test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class test {

    @Test
    public void test() {
        int[] a = new int[]{1, 2, 3, 4};
        int[] b = new int[]{1, 2, 3, 4};
        int[] c = new int[]{1, 2, 3};

        check(a, b);  // O
        check(a, c);  // N
        check(b, c);  // N
    }


    public static void check(int[] a, int[] b) {
        if (Arrays.equals(a, b)) {
            System.out.print("O");
        } else {
            System.out.print("N");
        }
    }
}
