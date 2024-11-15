#include <stdio.h>

int fib(int n)
{
    int x;
    if (n == 0 || n == 1)
    {
        x = 1;
    }
    else
    {
        x = fib(n - 1) + fib(n - 2);
    }
    return x;
}

int main()
{
    printf("%d\n", fib(6));
    return 0;
}