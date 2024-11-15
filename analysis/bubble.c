#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SIZE 20

int fixed [SIZE];
int x [SIZE];

void swap(int t[SIZE], int a, int b) {
    int temp = t[a];
    t[a] = t[b];
    t[b] = temp;
}

void bubbleSort(int arr[], int size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
            }
        }
    }
}

void printArray(int arr[], int size) {
    printf("[ ");
    for (int i = 0; i < size; i++) {
        printf("%d ", arr[i]);
    }
    printf("]\n");
}

int main() {
    srand(12);

    for (int i = 0; i < SIZE; i++) {
        fixed[i] = rand() % 100;
    }
    printArray(fixed, SIZE);
    for (int i = 0; i < 1000000; i++){
        memcpy(x, fixed, sizeof(fixed));
//        printArray(x, SIZE);
        bubbleSort(x, SIZE);
    }
    printArray(x, SIZE);

    return 0;
}
