/**
* Solution for https://www.hackerrank.com/challenges/min-max-riddle/problem
**/

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    /**
     * Computes the maximum of the minimums for every window size in the array.
     *
     * @param input Input array of type long.
     * @return Array of maximum of minimums for each window size.
     */
    static long[] riddle(long[] input) {
        int size = input.length;
        //edge conditions
        if (size == 0) return new long[0];
        if (size == 1) return new long[]{input[0]};
        
        // Find indices of the next smaller elements for each element in the array
        int[] nextSmaller = computeNextSmaller(input, size);
        // Find indices of the previous smaller elements for each element in the array
        int[] prevSmaller = computePrevSmaller(input, size);

        // Result array for maximum of minimums
        long[] result = new long[size + 1];

        // Populate result array
        for (int i = 0; i < size; i++) {
            int length = nextSmaller[i] - prevSmaller[i] - 1;
            result[length] = Math.max(result[length], input[i]);
        }

        // Fill in missing values for smaller window sizes by propagating larger values down
        for (int i = size - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }
        return Arrays.copyOfRange(result, 1, size + 1);
    }
    
     /**
     * Computes the next smaller element indices for each element in the array.
     *
     * @param input Input array of type long.
     * @param size   Size of the array.
     * @return Array of indices of the next smaller element for each position.
     */
     private static int[] computeNextSmaller(long[] input, int size) {
        int[] nextSmaller = new int[size];
        Arrays.fill(nextSmaller, size);
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && input[stack.peek()] > input[i]) {
                nextSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }
        return nextSmaller;
    }

     /**
     * Computes the previous smaller element indices for each element in the array.
     *
     * @param input Input array of type long.
     * @param size   Size of the array.
     * @return Array of indices of the previous smaller element for each position.
     */
    private static int[] computePrevSmaller(long[] input, int size) {
        int[] prevSmaller = new int[size];
        Arrays.fill(prevSmaller, -1);
        Stack<Integer> stack = new Stack<>();

        for (int i = size - 1; i >= 0; i--) {
            while (!stack.isEmpty() && input[stack.peek()] >= input[i]) {
                prevSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }
        return prevSmaller;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        long[] arr = new long[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            long arrItem = Long.parseLong(arrItems[i]);
            arr[i] = arrItem;
        }

        long[] res = riddle(arr);

        for (int i = 0; i < res.length; i++) {
            bufferedWriter.write(String.valueOf(res[i]));

            if (i != res.length - 1) {
                bufferedWriter.write(" ");
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.close();
        scanner.close();
    }
}
