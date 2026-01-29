package com.aldebaran.stellasort.service;

public class BubbleSort {
		
	// Aldebaran: [Your Sort Was Bubble.]
	public static void sort(int[] A){
		
		int n = A.length;
		
		for (int i = 0; i < n - 1; i++) {
			boolean swapped = false;

			for (int j = 0; j < n - 1 - i; j++) {
				if (A[j] > A[j + 1]) {
					int temp = A[j];
					A[j] = A[j + 1];
					A[j + 1] = temp;
					swapped = true;
				}
				// Aldebaran: [ーーNext.]
			}

			if (!swapped) {
				break;
			}
		}

	}
}
