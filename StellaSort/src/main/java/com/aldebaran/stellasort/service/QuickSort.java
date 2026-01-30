package com.aldebaran.stellasort.service;

import com.aldebaran.stellasort.service.SortListener;

public class QuickSort {
		
	// Aldebaran: [Your Sort Was Quick.]
	
	public static void sort(int[] A, SortListener listener)
	{
		quickSort(A, 0, A.length - 1, listener);
		if (listener != null) listener.onFinished();
	}
	
	private static void quickSort(int[] A, int low, int high, SortListener listener) {
		if (low < high) {
			
			int pi = partition(A, low, high, listener);
			
			quickSort(A, low, pi-1, listener);
			quickSort(A, pi +1, high, listener);
			
		}
	}
	
	private static int partition(int[] A, int low, int high, SortListener listener){
		
		int pivot = A[high];
		
		int i = low - 1;
		
		
		for (int j = low; j <= high - 1; j++) {
			if (listener != null)
                listener.onCompare(j, high, A[j], pivot);
			
			if (A[j] < pivot) {
				i++;
				if (listener != null)
                    listener.onSwap(i, j, A[i], A[j]);
				swap(A, i, j);
			}
		}
		
		if (listener != null)
            listener.onSwap(i + 1, high, A[i + 1], A[high]);
		
		swap(A, i+1, high);
		
		return i + 1;
	}
		
	private static void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
		
}
