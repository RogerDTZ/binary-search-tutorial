/**
 * Data Structures and Algorithm Analysis - 2023 Fall
 *
 * This tutorial demonstrates a simple problem solved by binary search.
 * In this problem, there is a NON-DESCENDING integer array of length N, and 2 magic numbers.
 * We will locate the last element that is no greater than one magic number, as well as the first
 * element that is greater than the other magic number.
 * Each of them is in time complexity of O(log N).
 */

import java.util.ArrayList;
import java.util.Scanner;

public class BinarySearchDemo {

    private static int MagicNumber1 = 42;
    private static int MagicNumber2 = 0x42; // 0x42 (hexadecimal) = 66 (decimal)

    /**
     * This is a judge function that returns true when "value" is small.
     * It returns false IFF "value" is greater than (or equal to) a certain threshold.
     */
    private static Boolean judgeType1(int value) {
        return value <= MagicNumber1;
        // More options:
        // return value < MagicNumber1;
        // ...
    }

    /**
     * This function accepts an array "arr", and returns the maximum index "i"
     * where "judgeType1(arr[i])" returns true.
     *
     * For example:
     * ...TTTTTTTTTTTTTTTTTTTTTTTFFFFFFFFFF...
     *                          ^
     *            The function returns this index
     */
    private static int binarySearchRightmost(ArrayList<Integer> arr) {
        int l = 0;
        int r = arr.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (judgeType1(arr.get(mid))) {
                l = mid + 1; // let's go right!
            } else {
                r = mid - 1; // let's be conservative, go back left...
            }
        }
        return r;
    }

    /**
     * This is a judge function that returns true when "value" is large.
     * It returns false IFF "value" is less than (or equal to) a certain threshold.
     */
    private static Boolean judgeType2(int value) {
        return value > MagicNumber2;
        // More options:
        // return value >= MagicNumber2;
        // ...
    }

    /**
     * This function accepts an array "arr", and returns the minimum index "i"
     * where "judgeType2(arr[i])" returns true.
     *
     * For example:
     * ...FFFFFFFFFFFFFFFFFFFFTTTTTTTTTTTTT...
     *                        ^
     *          The function returns this index
     */
    private static int binarySearchLeftmost(ArrayList<Integer> arr) {
        int l = 0;
        int r = arr.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (judgeType2(arr.get(mid))) {
                r = mid - 1; // let's go left!
            } else {
                l = mid + 1; // let's be conservative, go back right...
            }
        }
        return l;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        ArrayList<Integer> arr = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            arr.add(scanner.nextInt());
        }

        System.out.println(binarySearchRightmost(arr));
        System.out.println(binarySearchLeftmost(arr));

        scanner.close();
    }
}

/*
===============================================
            Try this sample input
-----------------------------------------------
16
1 41 41 42 42 42 43 43 65 65 66 66 66 67 67 100
===============================================
*/
