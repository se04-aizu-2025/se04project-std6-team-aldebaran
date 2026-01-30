package com.aldebaran.stellasort.service;

import javafx.animation.Animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeapSort {
    //#region Heap Sort
    public static List<Integer> sort(List<Integer> list) {
        int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // Extract elements from the heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Heapify reduced heap
            heapify(arr, i, 0);
        }

        return Arrays.stream(arr).boxed().toList();
    }

    static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = left(i);
        int right = right(i);

        if (left < n && arr[left] > arr[largest])
            largest = left;

        if (right < n && arr[right] > arr[largest])
            largest = right;

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }

    //#endregion

    //#region Animated Heap Sort

    @FunctionalInterface
    public static interface OnHeapSwap {
        List<Animation> getAnimation(int i, int j);
    }

    @FunctionalInterface
    public static interface OnRemoveHeapMax {
        List<Animation> getAnimation(int index, int value);
    }

    public static List<Animation> getSortAnimations(
            List<Integer> list,
            OnHeapSwap swapAnimation,
            OnRemoveHeapMax removeHeapMaxAnimation
    ){
        int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
        int n = arr.length;

        List<Animation> animations = new ArrayList<>();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--)
            animations.addAll(getHeapifyAnimations(arr, n, i, swapAnimation));

        // Extract elements from the heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            animations.addAll(swapAnimation.getAnimation(0, i));
            animations.addAll(removeHeapMaxAnimation.getAnimation(i, temp)); // returns max value

            // Heapify reduced heap
            animations.addAll(getHeapifyAnimations(arr, i, 0, swapAnimation));
        }

        animations.addAll(removeHeapMaxAnimation.getAnimation(0, arr[0]));
        return animations;
    }

    public static List<Animation> getHeapifyAnimations(int[] arr, int n, int i, OnHeapSwap swapAnimation) {
        List<Animation> animations = new ArrayList<>();

        int largest = i;
        int left = left(i);
        int right = right(i);

        if (left < n && arr[left] > arr[largest])
            largest = left;

        if (right < n && arr[right] > arr[largest])
            largest = right;

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            animations.addAll(swapAnimation.getAnimation(i, largest));

            // Recursively heapify the affected sub-tree
            animations.addAll(getHeapifyAnimations(arr, n, largest, swapAnimation));
        }

        return animations;
    }

    //#endregion

    //#region Heap Utils
    public static int left(int i) {
        return 2 * i + 1;
    }

    public static int right(int i) {
        return 2 * i + 2;
    }

    public static int parent(int i) {
        return (i - 1) / 2;
    }

    public static int level(int i) {
        return 31 - Integer.numberOfLeadingZeros(i + 1);
    }

//    public static int level(int i) {
//        return (int) Math.floor(Math.log(i+1) / Math.log(2));
//    }

    public static int levelStart(int level) {
        return (int) Math.pow(2, level) - 1;
    }

    public static int indexInLevel(int i) {
        int level = level(i);
        return i - levelStart(level);
    }

    //#endregion
}
