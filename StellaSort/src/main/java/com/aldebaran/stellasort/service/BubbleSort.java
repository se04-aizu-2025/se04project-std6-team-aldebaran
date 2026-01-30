package com.aldebaran.stellasort.service;

import java.util.function.BiConsumer;
import com.aldebaran.stellasort.service.SortListener;

public class BubbleSort {
		
	// Aldebaran: [Your Sort Was Bubble.]
    public static void sort(int[] A, SortListener listener){

        int n = A.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {

                if (listener != null)
                    listener.onCompare(j, j + 1, A[j], A[j + 1]);

                if (A[j] > A[j + 1]) {
					
					if (listener != null)
                        listener.onSwap(j, j + 1, A[j], A[j + 1]);
					
                    int temp = A[j];
                    A[j] = A[j + 1];
                    A[j + 1] = temp;

                    

                    swapped = true;
                }
            }

            if (!swapped) break;
        }
		listener.onFinished();
    }
}