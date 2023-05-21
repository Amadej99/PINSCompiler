!compiler_flags: --dump INT --exec INT

!code:
var a : arr[10] arr[5] integer;

fun main(_ : integer) : integer =
(
    f(2),
    { for i = 0, 5, 1 : print_int(a[2][i]) },
    0
) { where var i : integer };

fun f(n : integer) : arr[5] integer = 
(
    { for i = 0, 5, 1 : { a[n][i] = i } },
    a[n]
) { where var i : integer }
!expected:
0
1
2
3
4
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(10),
    0
)
!expected:
10
!end

!code:
fun main(_ : integer) : integer =
(
    print_str('Test'),
    0
)
!expected:
"Test"
!end

!code:
fun main(_ : integer) : integer =
(
    print_log(true),
    0
)
!expected:
true
!end

!code:
var x : integer;
fun main(_ : integer) : integer =
(
    print_int({ x = 10 }),
    0
)
!expected:
10
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(f(1) + f(2)),
    0
);

fun f(x : integer) : integer = x
!expected:
3
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(fib(5)),
    0
);

fun fib(n : integer) : integer =
(
    { if n == 0 | n == 1 then
        { x = 1 }
    else (
        { l = fib(n - 1) },
        { r = fib(n - 2) },
        { x = l + r }
    )},
    x
) { where var x : integer; var l : integer; var r : integer }
!expected:
8
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(fact(6)),
    0
);

fun fact(n : integer) : integer =
(
    { if n == 1 then { x = 1 } else { x = n * fact(n - 1) } },
    x
) { where var x : integer }
!expected:
720
!end

!code:
fun main(_ : integer) : integer =
(
    seed(10),
    { for i = 0, 10, 1 : { t[i] = rand_int(0, 10) } },
    bubble(t),
    { for i = 0, 10, 1 : print_int(t[i]) },
    0
) { where var t : arr[10] integer; var i : integer };

fun bubble(t : arr[10] integer) : integer =
(
    { for i = 0, 9, 1 : (
        { for j = 0, 9 - i, 1 : (
            { if t[j] > t[j + 1] then swap(t, j, j + 1) }
        )}
    )},
    0
) { where var i : integer; var j : integer };

fun swap(t : arr[10] integer, a : integer, b : integer) : integer =
(
    { temp = t[a] },
    { t[a] = t[b] },
    { t[b] = temp }
) { where var temp : integer }
!expected:
0
0
1
3
3
4
6
6
7
8
!end

!code:
var s : string;
fun main(_ : integer) : integer =
(
    { s = 'Testni niz' },
    print_str(s),
    0
)
!expected:
"Testni niz"
!end

!code:
fun main(_ : integer) : integer =
(
    f(f1(0)),
    0
);

fun f(s : string) : string = print_str(s);
fun f1(i : integer) : string = 'Testni niz'
!expected:
"Testni niz"
!end

!code:
fun main(_ : integer) : integer =
(
    seed(10),
    { for i = 0, 10, 1 : { t[i] = rand_int(0, 10) } },
    quick(t, 0, 9),
    { for i = 0, 10, 1 : print_int(t[i]) },
    0
) { where var t : arr[10] integer; var i : integer };

fun quick(t : arr [10] integer, left : integer, right : integer) : integer =
(
    { if left < right then (
        { r = partition(t, left, right) },
        quick(t, left, r-1),
        quick(t, r + 1, right)
    )},
    0
) { where var r : integer };

fun partition(t : arr[10] integer, left : integer, right : integer) : integer = (
    {l = left},
    {r = right + 1},
    {c = true},
    { while c : (
        { l = l + 1 },
        { while t[l] < t[left] & l < right : { l = l + 1 } },
        { r = r - 1 },
        { while t[r] > t[left] : { r = r - 1 } },
        { c = l < r },
        { if c then swap(t, l, r) }
    )},
    swap(t, left, r),
    r
) { where var l : integer; var r : integer; var c : logical };

fun swap(t : arr[10] integer, a : integer, b : integer) : integer =
(
    { temp = t[a] },
    { t[a] = t[b] },
    { t[b] = temp }
) { where var temp : integer }
!expected:
0
0
1
3
3
4
6
6
7
8
!end

!code:
fun main(_ : integer) : integer =
(
    seed(10),
    { for i = 0, 10, 1 : { t[i] = rand_int(0, 10) } },
    print_int(sum(t, 9))

) { where var t : arr [10] integer; var i : integer };

fun sum(t : arr[10] integer, i : integer) : integer =
(
    { if i < 0 then { s = 0 } else { s = t[i] + sum(t, i - 1) } },
    s
) { where var s : integer }
!expected:
38
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(10 + 100 * 15 - 6 / 2),
    0
)
!expected:
1507
!end

!code:
fun main(_ : integer) : integer =
(
    { a = { b = { c = 11 } } },
    print_int(a),
    print_int(b),
    print_int(c),
    0
) { where var a : integer; var b : integer; var c : integer }
!expected:
11
11
11
!end

!code:
fun main(_ : integer) : integer =
(
    { for i = 0, 10, 1 : print_str('For zanka') },
    0
) { where var i : integer }
!expected:
"For zanka"
"For zanka"
"For zanka"
"For zanka"
"For zanka"
"For zanka"
"For zanka"
"For zanka"
"For zanka"
"For zanka"
!end

!code:
fun main(_ : integer) : integer =
(
    print_log(true),
    print_log(false),
    0
)
!expected:
true
false
!end

!code:
var t : arr[10] integer;
fun main(_ : integer) : integer =
(
    fill(t),
    { for i = 0, 10, 1 : print_int(t[i]) },
    0
) { where var i : integer };

fun fill(t : arr[10] integer) : arr[10] integer =
(
    { for i = 0, 10, 1 : { t[i] = 1 } },
    t
) { where var i : integer }
!expected:
1
1
1
1
1
1
1
1
1
1
!end

!code:
fun main(_ : integer) : integer =
(
    { for i = 0, 10, 1 : (
        { if i == 3 then { i = 100 } else print_int(i) }
    )},
    0
) { where var i : integer }
!expected:
0
1
2
!end

!code:
var a : matrix;
var b : matrix;
var c : matrix;

fun main(_ : integer) : integer =
(
    fill_rand(a),
    fill_rand(b),
    mul(a, b, c),
    print_mat(c),
    0
) { where var i : integer };

fun print_mat(t : matrix) : matrix =
(
    { for i = 0, 3, 1 : (
        { for j = 0, 3, 1 : (
            print_int(t[i][j])
        )},
        print_str('')
    )},
    print_str(''),
    t
) { where var i : integer; var j : integer };

fun fill_rand(t : matrix) : matrix =
(
    seed(3),
    { for i = 0, 3, 1 : (
        { for j = 0, 3, 1 : (
            { t[i][j] = rand_int(1, 10) }
        )}
    )},
    t
) { where var i : integer; var j : integer };

fun fill(t : matrix, n : integer) : matrix =
(
    { for i = 0, 3, 1 : (
        { for j = 0, 3, 1 : (
            { t[i][j] = n }
        )}
    )},
    t
) { where var i : integer; var j : integer };

fun mul(a : matrix, b : matrix, c : matrix) : matrix =
(
    fill(c, 0),
    { for i = 0, 3, 1 : (
        { for j = 0, 3, 1 : (
            { for k = 0, 3, 1 : {
                c[i][j] = c[i][j] + a[i][k] * b[k][j]
            }}
        )}
    )},
    c
) { where var i : integer; var j : integer; var k : integer };

typ matrix : arr[3] arr[3] integer
!expected:
88
95
59
""
108
81
65
""
96
84
40
""
""
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(queens(0, 8, board)),
    0
) { where var board : arr [8] integer };

fun queens(q : integer, num_queens : integer, board : arr[8] integer) : integer =
(
    {
        if q == num_queens then (
            { count = 1 }
        )
        else (
            { count = 0 },
            {
                for r = 0, 8, 1 : (
                    { conflict = false },
                    {
                        for c = 0, q, 1 : (
                            { conflict = conflict | r == board[c] | board[c] - r == q - c | board[c] - r == c - q }
                        )
                    },
                    {
                        if !conflict then (
                            { board[q] = r },
                            { count = count + queens(q + 1, num_queens, board) }
                        )
                    }
                )
            }
        )
    },
    count
) { where var count : integer; var r : integer; var c : integer; var conflict : logical }
!expected:
92
!end

!code:
var num_moves : integer;
fun main(_ : integer) : integer =
(
    { num_moves = 0 },
    hanoi(5, 'A', 'B', 'C'),
    print_int(num_moves),
    0
);

fun hanoi(n : integer, t1 : string, t2 : string, t3 : string) : integer =
(
    {
        if n == 1 then (
            #print_str(t1),
            #print_str('->'),
            #print_str(t3),
            #print_str(''),
            { num_moves = num_moves + 1 }
        ) else (
            hanoi(n - 1, t1, t3, t2),
            hanoi(1, t1, t2, t3),
            hanoi(n - 1, t2, t1, t3)
        )
    },
    0
)
!expected:
31
!end

!code:
fun main(_ : integer) : integer =
(
    { n = 2021 },
    print_int(collatz_len(n)),
    0
) { where var n : integer };

fun collatz(n : integer) : integer =
(
    {
        if n % 2 == 0 then
            { temp = n / 2 }
        else
            { temp = 3 * n + 1 }
    },
    temp
) { where var temp : integer };

fun collatz_len(n : integer) : integer =
(
    { len = 1 },
    {
        while n != 1 : (
            { n = collatz(n) },
            { len = len + 1 }
        )
    },
    len
) { where var len : integer }
!expected:
64
!end

!code:
fun main(_ : integer) : integer =
(
    { v0 = 0 },
    print_str('main'),
    f1(10),
    0
) { where
    fun f1(p1 : integer) : integer = (
        { v1 = 1 },
        print_str('f1'),
        print_int(v1),
        print_int(p1),
        f2(20),
        0
    ) { where
        fun f2(p2 : integer) : integer = (
            { v2 = 2 },
            print_str('f2'),
            print_int(v2),
            print_int(v1),
            print_int(p2),
            print_int(p1),
            f3(30),
            0
        ) { where
            fun f3(p3 : integer) : integer = (
                { v3 = 3 },
                print_str('f3'),
                print_int(v3),
                print_int(v2),
                print_int(v1),
                print_int(p3),
                print_int(p2),
                print_int(p1),
                f10(100),
                f20(200),
                0
            ) { where
                var v3 : integer
            };
            var v2 : integer
        };
        var v1 : integer;
        fun f20(p20 : integer) : integer = (
            print_str('f20'),
            print_int(v1),
            print_int(p20),
            print_int(p1),
            f21(21),
            0
        );
        fun f21(p21 : integer) : integer = (
            print_str('f21'),
            print_int(v1),
            print_int(p21),
            print_int(p1),
            0
        )
    };
    var v0 : integer;
    fun f10(p10 : integer) : integer = (
        print_str('f10'),
        print_int(p10),
        print_int(v0),
        0
    )
}
!expected:
"main"
"f1"
1
10
"f2"
2
1
20
10
"f3"
3
2
1
30
20
10
"f10"
100
0
"f20"
1
200
10
"f21"
1
21
10
!end

!code:
fun main(_ : integer) : integer =
(
    print_int(fib(6)),
    0
);

fun fib(n : integer) : integer =
(
    {
        if n == 0 | n == 1 then
            { x = 1 }
        else
            { x = fib(n - 1) + fib(n - 2) }
    },
    x
) { where var x : integer }
!expected:
13
!end