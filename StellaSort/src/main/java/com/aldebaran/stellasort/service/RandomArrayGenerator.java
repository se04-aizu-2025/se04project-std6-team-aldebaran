package com.aldebaran.stellasort.service;

import java.util.function.Consumer;

public class RandomArrayGenerator {

    private final int size;
    private final int minValue;
    private final int maxValue;
    private final Consumer<int[]> onGenerated;

    public RandomArrayGenerator(int size, int minValue, int maxValue, Consumer<int[]> onGenerated) {
        this.size = size;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.onGenerated = onGenerated;
    }

    public void generate() {
        new Thread(() -> {
            int[] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = minValue + (int)(Math.random() * (maxValue - minValue + 1));
            }
            if (onGenerated != null) {
                onGenerated.accept(result);
            }
        }).start();
    }
}
