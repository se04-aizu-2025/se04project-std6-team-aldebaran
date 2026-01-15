package com.aldebaran.stellasort.service;

import java.util.ArrayList;
import java.util.List;

public class CountingSort {
    public static List<Integer> sort(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        int max = list.getFirst();
        for (int i : list) {
            if (i > max) {
                max = i;
            }
        }

        // initialize the count array
        int[] count = new int[max + 1];

        // count occurrences
        for (int i : list) {
            count[i]++;
        }

        // offset the count array
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        Integer[] resultsArray = new Integer[list.size()];

        for (int i = list.size() - 1; i >= 0; i--) {
            int val = list.get(i);
            resultsArray[count[val] - 1] = val;
            count[val]--;
        }

        return new ArrayList<>(List.of(resultsArray));
    }

    // test driver
    // takes an array of positive integers as command-line arguments
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

        for (String arg : args) {
            int num;

            try { num = Integer.parseInt(arg); }
            catch (NumberFormatException e) {
                System.out.println("Argument is not an integer: " + arg);
                throw e;
            }

            if (num < 0) {
                System.out.println("Argument is negative: " + arg);
                throw new IllegalArgumentException();
            }

            list.add(num);
        }

        System.out.println(sort(list));
    }
}
