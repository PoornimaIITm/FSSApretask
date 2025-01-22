import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    static long[] riddle(long[] arr) {
         int n = arr.length;

        int[] nextSmaller = computeNextSmaller(arr, n);
        int[] prevSmaller = computePrevSmaller(arr, n);

        // Result array for maximum of minimums
        long[] result = new long[n + 1];

        // Populate result array
        for (int i = 0; i < n; i++) {
            int length = nextSmaller[i] - prevSmaller[i] - 1;
            result[length] = Math.max(result[length], arr[i]);
        }

        // Fill missing values for smaller window sizes
        for (int i = n - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        return Arrays.copyOfRange(result, 1, n + 1);
    }
    
     private static int[] computeNextSmaller(long[] arr, int n) {
        int[] nextSmaller = new int[n];
        Arrays.fill(nextSmaller, n);
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                nextSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }

        return nextSmaller;
    }

    private static int[] computePrevSmaller(long[] arr, int n) {
        int[] prevSmaller = new int[n];
        Arrays.fill(prevSmaller, -1);
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
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
