!compiler_flags: --dump TYP --exec TYP

!code:
typ x : x
!failure:
99
!end

!code:
typ x : y;
typ y : x
!failure:
99
!end

!code:
typ t1 : t2;
typ t2 : t3;
typ t3 : t4;
typ t4 : t1
!failure:
99
!end

!code:
var x : integer;
fun f(a : integer) : logical = a + x
!failure:
99
!end

!code:
var x : logical;
fun f(a : integer) : integer = a + x
!failure:
99
!end

!code:
fun f(a : integer) : integer = 10 + false
!failure:
99
!end

!code:
fun f(a : integer) : integer = f(true)
!failure:
99
!end

!code:
typ x : integer;
fun f(a : x) : x = 10
!expected:
Defs [1:1-2:22]
  TypeDef [1:1-1:16]: x
    # typed as: int
    Atom [1:9-1:16]: INT
      # typed as: int
  FunDef [2:1-2:22]: f
    # typed as: (int) -> int
    Parameter [2:7-2:12]: a
      # typed as: int
      TypeName [2:11-2:12]: x
        # defined at: [1:1-1:16]
        # typed as: int
    TypeName [2:16-2:17]: x
      # defined at: [1:1-1:16]
      # typed as: int
    Literal [2:20-2:22]: INT(10)
      # typed as: int
!end

!code:
fun f(a : integer) : integer = f(a + 1)
!expected:
Defs [1:1-1:40]
  FunDef [1:1-1:40]: f
    # typed as: (int) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Call [1:32-1:40]: f
      # defined at: [1:1-1:40]
      # typed as: int
      Binary [1:34-1:39]: ADD
        # typed as: int
        Name [1:34-1:35]: a
          # defined at: [1:7-1:18]
          # typed as: int
        Literal [1:38-1:39]: INT(1)
          # typed as: int
!end

!code:
fun f(a : integer) : logical = f(a + 1)
!expected:
Defs [1:1-1:40]
  FunDef [1:1-1:40]: f
    # typed as: (int) -> log
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: LOG
      # typed as: log
    Call [1:32-1:40]: f
      # defined at: [1:1-1:40]
      # typed as: log
      Binary [1:34-1:39]: ADD
        # typed as: int
        Name [1:34-1:35]: a
          # defined at: [1:7-1:18]
          # typed as: int
        Literal [1:38-1:39]: INT(1)
          # typed as: int
!end

!code:
fun f(a : integer) : integer = f(true)
!failure:
99
!end

!code:
fun f(a : integer) : integer = f(a, 10)
!failure:
99
!end

!code:
fun f(a : integer, b : logical) integer = f(a)
!failure:
99
!end

!code:
fun f(a : integer) : integer = g(a);
fun g(a : integer) : logical a == 10
!failure:
99
!end

!code:
fun f(a : integer) : logical = a == 10
!expected:
Defs [1:1-1:39]
  FunDef [1:1-1:39]: f
    # typed as: (int) -> log
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: LOG
      # typed as: log
    Binary [1:32-1:39]: EQ
      # typed as: log
      Name [1:32-1:33]: a
        # defined at: [1:7-1:18]
        # typed as: int
      Literal [1:37-1:39]: INT(10)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = a + 10
!expected:
Defs [1:1-1:38]
  FunDef [1:1-1:38]: f
    # typed as: (int) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Binary [1:32-1:38]: ADD
      # typed as: int
      Name [1:32-1:33]: a
        # defined at: [1:7-1:18]
        # typed as: int
      Literal [1:36-1:38]: INT(10)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = a - 10
!expected:
Defs [1:1-1:38]
  FunDef [1:1-1:38]: f
    # typed as: (int) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Binary [1:32-1:38]: SUB
      # typed as: int
      Name [1:32-1:33]: a
        # defined at: [1:7-1:18]
        # typed as: int
      Literal [1:36-1:38]: INT(10)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = a * 10
!expected:
Defs [1:1-1:38]
  FunDef [1:1-1:38]: f
    # typed as: (int) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Binary [1:32-1:38]: MUL
      # typed as: int
      Name [1:32-1:33]: a
        # defined at: [1:7-1:18]
        # typed as: int
      Literal [1:36-1:38]: INT(10)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = a / 10
!expected:
Defs [1:1-1:38]
  FunDef [1:1-1:38]: f
    # typed as: (int) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Binary [1:32-1:38]: DIV
      # typed as: int
      Name [1:32-1:33]: a
        # defined at: [1:7-1:18]
        # typed as: int
      Literal [1:36-1:38]: INT(10)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = a % 10
!expected:
Defs [1:1-1:38]
  FunDef [1:1-1:38]: f
    # typed as: (int) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Binary [1:32-1:38]: MOD
      # typed as: int
      Name [1:32-1:33]: a
        # defined at: [1:7-1:18]
        # typed as: int
      Literal [1:36-1:38]: INT(10)
        # typed as: int
!end

!code:
var a : arr [10] integer;
fun f(b : integer) : integer = a[0]
!expected:
Defs [1:1-2:36]
  VarDef [1:1-1:25]: a
    # typed as: ARR(10,int)
    Array [1:9-1:25]
      # typed as: ARR(10,int)
      [10]
      Atom [1:18-1:25]: INT
        # typed as: int
  FunDef [2:1-2:36]: f
    # typed as: (int) -> int
    Parameter [2:7-2:18]: b
      # typed as: int
      Atom [2:11-2:18]: INT
        # typed as: int
    Atom [2:22-2:29]: INT
      # typed as: int
    Binary [2:32-2:36]: ARR
      # typed as: int
      Name [2:32-2:33]: a
        # defined at: [1:1-1:25]
        # typed as: ARR(10,int)
      Literal [2:34-2:35]: INT(0)
        # typed as: int
!end

!code:
var a : arr [10] integer;
fun f(b : integer) : integer = a[b]
!expected:
Defs [1:1-2:36]
  VarDef [1:1-1:25]: a
    # typed as: ARR(10,int)
    Array [1:9-1:25]
      # typed as: ARR(10,int)
      [10]
      Atom [1:18-1:25]: INT
        # typed as: int
  FunDef [2:1-2:36]: f
    # typed as: (int) -> int
    Parameter [2:7-2:18]: b
      # typed as: int
      Atom [2:11-2:18]: INT
        # typed as: int
    Atom [2:22-2:29]: INT
      # typed as: int
    Binary [2:32-2:36]: ARR
      # typed as: int
      Name [2:32-2:33]: a
        # defined at: [1:1-1:25]
        # typed as: ARR(10,int)
      Name [2:34-2:35]: b
        # defined at: [2:7-2:18]
        # typed as: int
!end

!code:
var a : arr [10] integer;
fun f(b : logical) : integer = a[b]
!failure:
99
!end

!code:
var a : arr [10] logical;
fun f(b : integer) : integer = a[b]
!failure:
99
!end

!code:
var a : arr [10] arr [5] integer;
fun f(b : integer) : arr [5] integer = a[b]
!expected:
Defs [1:1-2:44]
  VarDef [1:1-1:33]: a
    # typed as: ARR(10,ARR(5,int))
    Array [1:9-1:33]
      # typed as: ARR(10,ARR(5,int))
      [10]
      Array [1:18-1:33]
        # typed as: ARR(5,int)
        [5]
        Atom [1:26-1:33]: INT
          # typed as: int
  FunDef [2:1-2:44]: f
    # typed as: (int) -> ARR(5,int)
    Parameter [2:7-2:18]: b
      # typed as: int
      Atom [2:11-2:18]: INT
        # typed as: int
    Array [2:22-2:37]
      # typed as: ARR(5,int)
      [5]
      Atom [2:30-2:37]: INT
        # typed as: int
    Binary [2:40-2:44]: ARR
      # typed as: ARR(5,int)
      Name [2:40-2:41]: a
        # defined at: [1:1-1:33]
        # typed as: ARR(10,ARR(5,int))
      Name [2:42-2:43]: b
        # defined at: [2:7-2:18]
        # typed as: int
!end

!code:
var a : arr [10] integer;
fun f(b : integer) : integer = f(a[b] + 1)
!expected:
Defs [1:1-2:43]
  VarDef [1:1-1:25]: a
    # typed as: ARR(10,int)
    Array [1:9-1:25]
      # typed as: ARR(10,int)
      [10]
      Atom [1:18-1:25]: INT
        # typed as: int
  FunDef [2:1-2:43]: f
    # typed as: (int) -> int
    Parameter [2:7-2:18]: b
      # typed as: int
      Atom [2:11-2:18]: INT
        # typed as: int
    Atom [2:22-2:29]: INT
      # typed as: int
    Call [2:32-2:43]: f
      # defined at: [2:1-2:43]
      # typed as: int
      Binary [2:34-2:42]: ADD
        # typed as: int
        Binary [2:34-2:38]: ARR
          # typed as: int
          Name [2:34-2:35]: a
            # defined at: [1:1-1:25]
            # typed as: ARR(10,int)
          Name [2:36-2:37]: b
            # defined at: [2:7-2:18]
            # typed as: int
        Literal [2:41-2:42]: INT(1)
          # typed as: int
!end

!code:
typ x : integer;
typ y : integer;
typ z : x;

fun f(a : x, b : y) : z = { a = b }
!expected:
Defs [1:1-5:36]
  TypeDef [1:1-1:16]: x
    # typed as: int
    Atom [1:9-1:16]: INT
      # typed as: int
  TypeDef [2:1-2:16]: y
    # typed as: int
    Atom [2:9-2:16]: INT
      # typed as: int
  TypeDef [3:1-3:10]: z
    # typed as: int
    TypeName [3:9-3:10]: x
      # defined at: [1:1-1:16]
      # typed as: int
  FunDef [5:1-5:36]: f
    # typed as: (int, int) -> int
    Parameter [5:7-5:12]: a
      # typed as: int
      TypeName [5:11-5:12]: x
        # defined at: [1:1-1:16]
        # typed as: int
    Parameter [5:14-5:19]: b
      # typed as: int
      TypeName [5:18-5:19]: y
        # defined at: [2:1-2:16]
        # typed as: int
    TypeName [5:23-5:24]: z
      # defined at: [3:1-3:10]
      # typed as: int
    Binary [5:27-5:36]: ASSIGN
      # typed as: int
      Name [5:29-5:30]: a
        # defined at: [5:7-5:12]
        # typed as: int
      Name [5:33-5:34]: b
        # defined at: [5:14-5:19]
        # typed as: int
!end

!code:
fun f(a : integer, b : logical) : integer = ({ b = false }, a)
!expected:
Defs [1:1-1:63]
  FunDef [1:1-1:63]: f
    # typed as: (int, log) -> int
    Parameter [1:7-1:18]: a
      # typed as: int
      Atom [1:11-1:18]: INT
        # typed as: int
    Parameter [1:20-1:31]: b
      # typed as: log
      Atom [1:24-1:31]: LOG
        # typed as: log
    Atom [1:35-1:42]: INT
      # typed as: int
    Block [1:45-1:63]
      # typed as: int
      Binary [1:46-1:59]: ASSIGN
        # typed as: log
        Name [1:48-1:49]: b
          # defined at: [1:20-1:31]
          # typed as: log
        Literal [1:52-1:57]: LOG(false)
          # typed as: log
      Name [1:61-1:62]: a
        # defined at: [1:7-1:18]
        # typed as: int
!end

!code:
fun f(a : integer, b : logical) : integer = { a = b }
!failure:
99
!end

!code:
fun f(a : integer, b : logical) : string = { a = b }
!failure:
99
!end

!code:
fun f(a : string, b : string) : integer = { a = b }
!failure:
99
!end

!code:
fun f(a : logical, b : logical) : integer = { a = b }
!failure:
99
!end

!code:
fun f(a : integer, b : integer) : logical = { a = b }
!failure:
99
!end

!code:
fun f(a : integer, b : logical) : integer =
{ while b : a }
!failure:
99
!end

!code:
fun f(a : integer, b : logical) : integer =
( { while a : a }, b)
!failure:
99
!end

!code:
fun f(a : logical, b : integer, c : integer) : integer = 
(
    { for a, b, c : 10 }, 10
)
!failure:
99
!end

!code:
fun f(a : logical, b : integer, c : integer) : integer = 
(
    { for b, b, c : 10 }, 10
)
!failure:
99
!end

!code:
fun f(a : logical, b : integer, c : integer) : integer = 
(
    { for b = 10, b, c : 10 }, 10
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    # typed as: (log, int, int) -> int
    Parameter [1:7-1:18]: a
      # typed as: log
      Atom [1:11-1:18]: LOG
        # typed as: log
    Parameter [1:20-1:31]: b
      # typed as: int
      Atom [1:24-1:31]: INT
        # typed as: int
    Parameter [1:33-1:44]: c
      # typed as: int
      Atom [1:37-1:44]: INT
        # typed as: int
    Atom [1:48-1:55]: INT
      # typed as: int
    Block [2:1-4:2]
      # typed as: int
      For [3:5-3:30]
        # typed as: void
        Name [3:11-3:12]: b
          # defined at: [1:20-1:31]
          # typed as: int
        Literal [3:15-3:17]: INT(10)
          # typed as: int
        Name [3:19-3:20]: b
          # defined at: [1:20-1:31]
          # typed as: int
        Name [3:22-3:23]: c
          # defined at: [1:33-1:44]
          # typed as: int
        Literal [3:26-3:28]: INT(10)
          # typed as: int
      Literal [3:32-3:34]: INT(10)
        # typed as: int
!end

!code:
fun f(a : logical, b : integer, c : integer) : integer = 
(
    { for a = 10, b, c : 10 }, 10
)
!failure:
99
!end

!code:
fun f(a : logical) : integer = 
(
    { if a then 10 }, 10
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    # typed as: (log) -> int
    Parameter [1:7-1:18]: a
      # typed as: log
      Atom [1:11-1:18]: LOG
        # typed as: log
    Atom [1:22-1:29]: INT
      # typed as: int
    Block [2:1-4:2]
      # typed as: int
      IfThenElse [3:5-3:21]
        # typed as: void
        Name [3:10-3:11]: a
          # defined at: [1:7-1:18]
          # typed as: log
        Literal [3:17-3:19]: INT(10)
          # typed as: int
      Literal [3:23-3:25]: INT(10)
        # typed as: int
!end

!code:
fun f(a : logical) : integer = 
(
    { if a then 10 else 0 }, 10
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    # typed as: (log) -> int
    Parameter [1:7-1:18]: a
      # typed as: log
      Atom [1:11-1:18]: LOG
        # typed as: log
    Atom [1:22-1:29]: INT
      # typed as: int
    Block [2:1-4:2]
      # typed as: int
      IfThenElse [3:5-3:28]
        # typed as: void
        Name [3:10-3:11]: a
          # defined at: [1:7-1:18]
          # typed as: log
        Literal [3:17-3:19]: INT(10)
          # typed as: int
        Literal [3:25-3:26]: INT(0)
          # typed as: int
      Literal [3:30-3:32]: INT(10)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = 
(
    { if a then 10 else 1 }, 10
)
!failure:
99
!end

!code:
var a : arr [10] arr [20] logical;
fun f (i : integer) : logical = a[i]
!failure:
99
!end

!code:
var a : arr [10] arr [20] logical;
fun f (i : integer) : integer = 
(
    { for x = 0, 20, { x = x + 1 } : { a[i][x] = false } },
    g(a[i])
) { where var x : int; typ int : integer };

fun g(a : arr[20] logical) : integer =
(
    { for x = 0, 10, { x = x + 1 } : { if a[x] == true then { c = c + 1 } } },
    c
) { where var x : integer; var c : integer }
!expected:
Defs [1:1-12:45]
  VarDef [1:1-1:34]: a
    # typed as: ARR(10,ARR(20,log))
    Array [1:9-1:34]
      # typed as: ARR(10,ARR(20,log))
      [10]
      Array [1:18-1:34]
        # typed as: ARR(20,log)
        [20]
        Atom [1:27-1:34]: LOG
          # typed as: log
  FunDef [2:1-6:43]: f
    # typed as: (int) -> int
    Parameter [2:8-2:19]: i
      # typed as: int
      Atom [2:12-2:19]: INT
        # typed as: int
    Atom [2:23-2:30]: INT
      # typed as: int
    Where [3:1-6:43]
      # typed as: int
      Defs [6:11-6:41]
        VarDef [6:11-6:22]: x
          # typed as: int
          TypeName [6:19-6:22]: int
            # defined at: [6:24-6:41]
            # typed as: int
        TypeDef [6:24-6:41]: int
          # typed as: int
          Atom [6:34-6:41]: INT
            # typed as: int
      Block [3:1-6:2]
        # typed as: int
        For [4:5-4:59]
          # typed as: void
          Name [4:11-4:12]: x
            # defined at: [6:11-6:22]
            # typed as: int
          Literal [4:15-4:16]: INT(0)
            # typed as: int
          Literal [4:18-4:20]: INT(20)
            # typed as: int
          Binary [4:22-4:35]: ASSIGN
            # typed as: int
            Name [4:24-4:25]: x
              # defined at: [6:11-6:22]
              # typed as: int
            Binary [4:28-4:33]: ADD
              # typed as: int
              Name [4:28-4:29]: x
                # defined at: [6:11-6:22]
                # typed as: int
              Literal [4:32-4:33]: INT(1)
                # typed as: int
          Binary [4:38-4:57]: ASSIGN
            # typed as: log
            Binary [4:40-4:47]: ARR
              # typed as: log
              Binary [4:40-4:44]: ARR
                # typed as: ARR(20,log)
                Name [4:40-4:41]: a
                  # defined at: [1:1-1:34]
                  # typed as: ARR(10,ARR(20,log))
                Name [4:42-4:43]: i
                  # defined at: [2:8-2:19]
                  # typed as: int
              Name [4:45-4:46]: x
                # defined at: [6:11-6:22]
                # typed as: int
            Literal [4:50-4:55]: LOG(false)
              # typed as: log
        Call [5:5-5:12]: g
          # defined at: [8:1-12:45]
          # typed as: int
          Binary [5:7-5:11]: ARR
            # typed as: ARR(20,log)
            Name [5:7-5:8]: a
              # defined at: [1:1-1:34]
              # typed as: ARR(10,ARR(20,log))
            Name [5:9-5:10]: i
              # defined at: [2:8-2:19]
              # typed as: int
  FunDef [8:1-12:45]: g
    # typed as: (ARR(20,log)) -> int
    Parameter [8:7-8:26]: a
      # typed as: ARR(20,log)
      Array [8:11-8:26]
        # typed as: ARR(20,log)
        [20]
        Atom [8:19-8:26]: LOG
          # typed as: log
    Atom [8:30-8:37]: INT
      # typed as: int
    Where [9:1-12:45]
      # typed as: int
      Defs [12:11-12:43]
        VarDef [12:11-12:26]: x
          # typed as: int
          Atom [12:19-12:26]: INT
            # typed as: int
        VarDef [12:28-12:43]: c
          # typed as: int
          Atom [12:36-12:43]: INT
            # typed as: int
      Block [9:1-12:2]
        # typed as: int
        For [10:5-10:78]
          # typed as: void
          Name [10:11-10:12]: x
            # defined at: [12:11-12:26]
            # typed as: int
          Literal [10:15-10:16]: INT(0)
            # typed as: int
          Literal [10:18-10:20]: INT(10)
            # typed as: int
          Binary [10:22-10:35]: ASSIGN
            # typed as: int
            Name [10:24-10:25]: x
              # defined at: [12:11-12:26]
              # typed as: int
            Binary [10:28-10:33]: ADD
              # typed as: int
              Name [10:28-10:29]: x
                # defined at: [12:11-12:26]
                # typed as: int
              Literal [10:32-10:33]: INT(1)
                # typed as: int
          IfThenElse [10:38-10:76]
            # typed as: void
            Binary [10:43-10:55]: EQ
              # typed as: log
              Binary [10:43-10:47]: ARR
                # typed as: log
                Name [10:43-10:44]: a
                  # defined at: [8:7-8:26]
                  # typed as: ARR(20,log)
                Name [10:45-10:46]: x
                  # defined at: [12:11-12:26]
                  # typed as: int
              Literal [10:51-10:55]: LOG(true)
                # typed as: log
            Binary [10:61-10:74]: ASSIGN
              # typed as: int
              Name [10:63-10:64]: c
                # defined at: [12:28-12:43]
                # typed as: int
              Binary [10:67-10:72]: ADD
                # typed as: int
                Name [10:67-10:68]: c
                  # defined at: [12:28-12:43]
                  # typed as: int
                Literal [10:71-10:72]: INT(1)
                  # typed as: int
        Name [11:5-11:6]: c
          # defined at: [12:28-12:43]
          # typed as: int
!end

!code:
fun f(a : integer, b : logical) : integer =
(
  { a = b },
  a
)
!failure:
99
!end

!code:
fun f(a : arr[10] integer) : integer = a
!failure:
99
!end

!code:
fun f(a : arr[10] arr[20] integer) : integer =
(
  { a[1] = 10 },
  10
)
!failure:
99
!end

!code:
fun f(a : arr[10] arr[20] integer) : integer =
(
  { a = a[1] },
  10
)
!failure:
99
!end

!code:
fun f(a : integer, b : logical, c : arr[10] string) : integer = f(false, 10, c)
!failure:
99
!end

!code:
var x : arr[10] logical;
fun f(y : integer) : integer =
(
  x[1] = y;
  y
)
!failure:
99
!end

!code:
var x : arr[10] logical;
fun f(y : integer) : logical = x
!failure:
99
!end

!code:
typ int : integer;
fun f(a : int, b : int, c : logical, d : string) : integer = f(a, b, c, d)
!expected:
Defs [1:1-2:75]
  TypeDef [1:1-1:18]: int
    # typed as: int
    Atom [1:11-1:18]: INT
      # typed as: int
  FunDef [2:1-2:75]: f
    # typed as: (int, int, log, str) -> int
    Parameter [2:7-2:14]: a
      # typed as: int
      TypeName [2:11-2:14]: int
        # defined at: [1:1-1:18]
        # typed as: int
    Parameter [2:16-2:23]: b
      # typed as: int
      TypeName [2:20-2:23]: int
        # defined at: [1:1-1:18]
        # typed as: int
    Parameter [2:25-2:36]: c
      # typed as: log
      Atom [2:29-2:36]: LOG
        # typed as: log
    Parameter [2:38-2:48]: d
      # typed as: str
      Atom [2:42-2:48]: STR
        # typed as: str
    Atom [2:52-2:59]: INT
      # typed as: int
    Call [2:62-2:75]: f
      # defined at: [2:1-2:75]
      # typed as: int
      Name [2:64-2:65]: a
        # defined at: [2:7-2:14]
        # typed as: int
      Name [2:67-2:68]: b
        # defined at: [2:16-2:23]
        # typed as: int
      Name [2:70-2:71]: c
        # defined at: [2:25-2:36]
        # typed as: log
      Name [2:73-2:74]: d
        # defined at: [2:38-2:48]
        # typed as: str
!end

!code:
typ int : integer;
fun f(a : int, b : int, c : logical, d : string) : integer = f(d, c, a, b)
!failure:
99
!end