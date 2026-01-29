package com.aldebaran.stellasort.service;

public class QuickSort {
		
	// Aldebaran: [Your Sort Was Quick.]
	
	public static void sort(int[] A)
	{
		quickSort(A, 0, A.length - 1);
	}
	
	private static void quickSort(int[] A, int low, int high) {
		if (low < high) {
			
			int pi = partition(A, low, high);
			
			quickSort(A, low, pi-1);
			quickSort(A, pi +1, high);
			
		}
	}
	
	private static int partition(int[] A, int low, int high){
		
		int pivot = A[high];
		
		int i = low - 1;
		
		
		for (int j = low; j <= high - 1; j++) {
			if (A[j] < pivot) {
				i++;
				swap(A, i, j);
			}
		}
		
		swap(A, i+1, high);
		
		
		return i + 1;
	}
		
	private static void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
		
}
