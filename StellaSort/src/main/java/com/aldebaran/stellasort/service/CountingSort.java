package com.aldebaran.stellasort.service;

import javafx.animation.Animation;

import java.util.ArrayList;
import java.util.List;

public class CountingSort {
    public static List<Integer> sort(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        int max = list.getFirst();
        for (int i : list) {
            if (i < 0) throw new IllegalArgumentException("CountingSort only supports non-negative integers: " + i + " is not a valid input");
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

    @FunctionalInterface
    public interface OnIncrementCountingArray {
        List<Animation> createAnimations(int index, int oldValue);
    }

    @FunctionalInterface
    public interface OnDecrementCountingArray {
        List<Animation> createAnimations(int index, int oldValue);
    }

    @FunctionalInterface
    public interface OnBeforeIncrementCountingArray {
        List<Animation> createAnimations(int inputIndex, int inputValue);
    }

    @FunctionalInterface
    public interface OnBeforeDecrementCountingArray {
        List<Animation> createAnimations(int inputIndex, int inputValue, int resultIndex, int resultValue, int resultIndexOffset);
    }

    @FunctionalInterface
    public interface OnAfterIncrementCountingArray {
        // index is the result index, offsetCount[val] - 1
        // index offset is the current offset, offsetCount[val]
        // value is the value at index, list[index]
        List<Animation> createAnimations(int index, int indexOffset, int value);
    }

    public static List<Animation> getSortAnimations(
            List<Integer> list, int max,
            OnIncrementCountingArray incrementCountingArrayAnimation,
            OnDecrementCountingArray decrementCountingArrayAnimation,
            OnBeforeIncrementCountingArray beforeIncrementCountingArrayAnimation,
            OnBeforeDecrementCountingArray beforeDecrementCountingAnimation,
            OnAfterIncrementCountingArray afterIncrementCountingArrayAnimation
    ) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<Animation> animations = new ArrayList<>();

        // initialize the count array
        int[] count = new int[max + 1];
        int[] offsetCount = new int[max + 1];

        // count occurrences
        for (int i = 0; i < list.size(); i++) {
            int j = list.get(i);
            count[j]++;
            offsetCount[j] = count[j];

            animations.addAll(beforeIncrementCountingArrayAnimation.createAnimations(i, j));
            animations.addAll(incrementCountingArrayAnimation.createAnimations(j, count[j]-1));
            System.out.println("Play animation to transition to count[" + j + "] = " + count[j] + "");
        }

        // offset the count array
        for (int i = 1; i <= max; i++) {
            offsetCount[i] += offsetCount[i - 1];
        }

        Integer[] resultsArray = new Integer[list.size()];

        for (int i = list.size() - 1; i >= 0; i--) {
            int val = list.get(i);
            resultsArray[offsetCount[val] - 1] = val;

            animations.addAll(beforeDecrementCountingAnimation.createAnimations(i, val, offsetCount[val] - 1, val, offsetCount[val]));

            count[val]--;
            offsetCount[val]--;

            if (count[val] >= 0) {
                System.out.println("Play animation to transition to count[" + val + "] = " + count[val] + "");
                animations.addAll(decrementCountingArrayAnimation.createAnimations(val, count[val]+1));
            }

            animations.addAll(afterIncrementCountingArrayAnimation.createAnimations(offsetCount[val], offsetCount[val], val));

            System.out.println("count[" + val + "] = " + count[val] + "");
        }

        return animations;
    }
}
