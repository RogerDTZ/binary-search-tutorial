/**
 * Data Structures and Algorithm Analysis - 2023 Fall
 *
 * This tutorial demonstrates a simple problem solved by binary search.
 * In this problem, there is a NON-DESCENDING integer array of length N, and 2 magic numbers.
 * We will locate the last element that is no greater than one magic number, as well as the first
 * element that is greater than the other magic number.
 * Each of them is in time complexity of O(log N).
 */

#include <iostream>
#include <vector>

constexpr static int MagicNumber1 = 42;
constexpr static int MagicNumber2 = 0x42; // 0x42 (hexadecimal) = 66 (decimal)

/**
 * This is a judge function that returns true when "value" is small.
 * It returns false IFF "value" is greater than (or equal to) a certain threshold.
 */
static bool judge_type1(int value) {
    return value <= MagicNumber1;
    // More options:
    // return value < MagicNumber1;
    // ...
}

/**
 * This function accepts an array "arr", and returns the maximum index "i"
 * where "judge_type1(arr[i])" returns true.
 *
 * For example:
 * ...TTTTTTTTTTTTTTTTTTTTTTTFFFFFFFFFF...
 *                          ^
 *            The function returns this index
 */
static int binary_search_rightmost(const std::vector<int>& arr) {
    int l = 0;
    int r = arr.size() - 1;
    while (l <= r) {
        const int mid = (l + r) / 2;
        if (judge_type1(arr[mid])) {
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
static bool judge_type2(int value) {
    return value > MagicNumber2;
    // More options:
    // return value >= MagicNumber2;
    // ...
}

/**
 * This function accepts an array "arr", and returns the minimum index "i"
 * where "judge_type2(arr[i])" returns true.
 *
 * For example:
 * ...FFFFFFFFFFFFFFFFFFFFTTTTTTTTTTTTT...
 *                        ^
 *          The function returns this index
 */
static int binary_search_leftmost(const std::vector<int>& arr) {
    int l = 0;
    int r = arr.size() - 1;
    while (l <= r) {
        const int mid = (l + r) / 2;
        if (judge_type2(arr[mid])) {
            r = mid - 1; // let's go left!
        } else {
            l = mid + 1; // let's be conservative, go back right...
        }
    }
    return l;
}

int main() {
    int n;
    std::cin >> n;
    std::vector<int> arr(n);
    for (int i = 0; i < n; ++i) {
        std::cin >> arr[i];
    }

    std::cout << binary_search_rightmost(arr) << '\n';
    std::cout << binary_search_leftmost(arr) << '\n';

    return 0;
}

/*
===============================================
            Try this sample input
-----------------------------------------------
16
1 41 41 42 42 42 43 43 65 65 66 66 66 67 67 100
===============================================
*/
