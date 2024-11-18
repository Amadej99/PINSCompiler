#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SIZE 20

int fixed [SIZE];
int x [SIZE];

void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int partition(int arr[], int low, int high) {

    int p = arr[low];
    int i = low;
    int j = high;

    while (i < j) {
        while (arr[i] <= p && i <= high - 1) {
            i++;
        }

        while (arr[j] > p && j >= low + 1) {
            j--;
        }
        if (i < j) {
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[low], &arr[j]);
    return j;
}

void quickSort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
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
        quickSort(x, 0, 19);
    }
    printArray(x, SIZE);

    return 0;
}