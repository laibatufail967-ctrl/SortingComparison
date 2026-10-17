
import java.util.Arrays;
import java.util.Random;

public class SortingComparison {

    static final int SIZE = 100000;

    public static void main(String[] args) {

        int[] original = new int[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            original[i] = random.nextInt(1000000);
        }

        String[] names = {
            "Bubble Sort", "Selection Sort",
            "Merge Sort", "Insertion Sort"
        };

        long[] times = new long[4];

        System.out.println("=== SORTING COMPARISON ===");
        System.out.println("Array Size: " + SIZE);

        times[0] = testSort(original, 0);
        times[1] = testSort(original, 1);
        times[2] = testSort(original, 2);
        times[3] = testSort(original, 3);

        System.out.println("\n=== FINAL COMPARISON ===");

        for (int i = 0; i < 4; i++) {
            System.out.printf("%s: %.3f ms%n",
                names[i], times[i] / 1_000_000.0);
        }

        int fastest = 0;
        int slowest = 0;

        for (int i = 1; i < 4; i++) {
            if (times[i] < times[fastest]) fastest = i;
            if (times[i] > times[slowest]) slowest = i;
        }

        System.out.println("\nFastest: " + names[fastest]);
        System.out.println("Slowest: " + names[slowest]);
        System.out.println("\nReason:");
        System.out.println("Merge Sort uses O(n log n) time.");
        System.out.println("The other three have O(n^2) average/worst-case time.");
    }

    static long testSort(int[] original, int type) {
        int[] arr = Arrays.copyOf(original, original.length);

        long start = System.nanoTime();

        switch (type) {
            case 0: bubbleSort(arr); break;
            case 1: selectionSort(arr); break;
            case 2: mergeSort(arr); break;
            case 3: insertionSort(arr); break;
        }

        long end = System.nanoTime();

        System.out.println("\n" + getName(type));
        System.out.printf("Time: %.3f ms%n",
            (end - start) / 1_000_000.0);
        System.out.println("Sorted correctly: " + isSorted(arr));

        return end - start;
    }

    static String getName(int type) {
        String[] names = {
            "Bubble Sort", "Selection Sort",
            "Merge Sort", "Insertion Sort"
        };
        return names[type];
    }

    static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) min = j;
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    static void mergeSort(int[] arr) {
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    static void mergeSort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;

        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        for (int x = left; x <= right; x++) {
            arr[x] = temp[x];
        }
    }

    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}
