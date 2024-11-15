#include <stdio.h>
#include <stdbool.h>

#define NUM_QUEENS 13  // Size of the board and the number of queens

int board[NUM_QUEENS] = {0};  // Array to hold queen positions
int solution_count = 0;       // Counter for solutions

// Function to check if the current position (row `r`) for the current queen `q` is safe
bool isConflict(int q, int r) {
    for (int c = 0; c < q; c++) {
        if (board[c] == r ||               // Same row conflict
            board[c] - r == q - c ||       // Major diagonal conflict
            board[c] - r == c - q) {       // Minor diagonal conflict
            return true;
        }
    }
    return false;
}

// Recursive function to find solutions
int queens(int q, int num_queens) {
    int count = 0;
    if (q == num_queens) {
        solution_count++;
        return 1;
    } else {
        for (int r = 0; r < num_queens; r++) {
            if (!isConflict(q, r)) {
                board[q] = r;  // Place queen in row `r`
                count += queens(q + 1, num_queens);  // Recursively place the next queen
            }
        }
    }
    return count;
}

int main() {
    printf("%d\n", queens(0, NUM_QUEENS));
    return 0;
}
