package com.aldebaran.stellasort.service;

public interface SortListener {
	
    void onCompare(int i, int j, int a, int b);
    void onSwap(int i, int j, int a, int b);
	
}