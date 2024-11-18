#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SIZE 20

int fixed [SIZE];
int x [SIZE];

void printArray(int arr[], int size) {
    printf("[ ");
    for (int i = 0; i < size; i++) {
        printf("%d ", arr[i]);
    }
    printf("]\n");
}

int compare(const void* a, const void* b) {
    return (*(int*)a - *(int*)b);
}

int main() {
    srand(12);

    for (int i = 0; i < SIZE; i++) {
        fixed[i] = rand() % 100;
    }
    printArray(fixed, SIZE);
    for (int i = 0; i < 1000000; i++){
        memcpy(x, fixed, sizeof(fixed));
        qsort(x, SIZE, sizeof(int), compare);
    }
    printArray(x, SIZE);

    return 0;
}