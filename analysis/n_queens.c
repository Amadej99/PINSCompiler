#include <stdio.h>
#include <stdbool.h>

#define NUM_QUEENS 13

int board[NUM_QUEENS] = {0};
int solution_count = 0;

bool isConflict(int q, int r) {
    for (int c = 0; c < q; c++) {
        if (board[c] == r ||
            board[c] - r == q - c ||
            board[c] - r == c - q) {
            return true;
        }
    }
    return false;
}

int queens(int q, int num_queens) {
    int count = 0;
    if (q == num_queens) {
        solution_count++;
        return 1;
    } else {
        for (int r = 0; r < num_queens; r++) {
            if (!isConflict(q, r)) {
                board[q] = r;
                count += queens(q + 1, num_queens);
            }
        }
    }
    return count;
}

int main() {
    printf("%d\n", queens(0, NUM_QUEENS));
    return 0;
}
