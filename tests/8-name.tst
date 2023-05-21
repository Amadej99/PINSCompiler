!compiler_flags: --dump NAME --exec NAME

!code:
typ int : integer;
var x : int;
fun f(a:int) : int = a * x + y { where var y : int }
!expected:
Defs [1:1-3:53]
  TypeDef [1:1-1:18]: int
    Atom [1:11-1:18]: INT
  VarDef [2:1-2:12]: x
    TypeName [2:9-2:12]: int
      # defined at: [1:1-1:18]
  FunDef [3:1-3:53]: f
    Parameter [3:7-3:12]: a
      TypeName [3:9-3:12]: int
        # defined at: [1:1-1:18]
    TypeName [3:16-3:19]: int
      # defined at: [1:1-1:18]
    Where [3:22-3:53]
      Defs [3:40-3:51]
        VarDef [3:40-3:51]: y
          TypeName [3:48-3:51]: int
            # defined at: [1:1-1:18]
      Binary [3:22-3:31]: ADD
        Binary [3:22-3:27]: MUL
          Name [3:22-3:23]: a
            # defined at: [3:7-3:12]
          Name [3:26-3:27]: x
            # defined at: [2:1-2:12]
        Name [3:30-3:31]: y
          # defined at: [3:40-3:51]
!end

!code:
var x : integer;
typ int : x
!failure:
99
!end

!code:
fun f(a:integer) : integer = a + f
!failure:
99
!end

!code:
typ i : integer;
var i : integer
!failure:
99
!end

!code:
typ a : integer;
fun f(b : integer) : integer = a + b
!failure:
99
!end

!code:
typ a: integer;
fun f(a : integer) : integer = a + 10
!expected:
Defs [1:1-2:38]
  TypeDef [1:1-1:15]: a
    Atom [1:8-1:15]: INT
  FunDef [2:1-2:38]: f
    Parameter [2:7-2:18]: a
      Atom [2:11-2:18]: INT
    Atom [2:22-2:29]: INT
    Binary [2:32-2:38]: ADD
      Name [2:32-2:33]: a
        # defined at: [2:7-2:18]
      Literal [2:36-2:38]: INT(10)
!end

!code:
var a : integer;
fun f(a : integer) : integer = a * f(a) { where fun f(a:integer) : integer = a }
!expected:
Defs [1:1-2:81]
  VarDef [1:1-1:16]: a
    Atom [1:9-1:16]: INT
  FunDef [2:1-2:81]: f
    Parameter [2:7-2:18]: a
      Atom [2:11-2:18]: INT
    Atom [2:22-2:29]: INT
    Where [2:32-2:81]
      Defs [2:49-2:79]
        FunDef [2:49-2:79]: f
          Parameter [2:55-2:64]: a
            Atom [2:57-2:64]: INT
          Atom [2:68-2:75]: INT
          Name [2:78-2:79]: a
            # defined at: [2:55-2:64]
      Binary [2:32-2:40]: MUL
        Name [2:32-2:33]: a
          # defined at: [2:7-2:18]
        Call [2:36-2:40]: f
          # defined at: [2:49-2:79]
          Name [2:38-2:39]: a
            # defined at: [2:7-2:18]
!end

!code:
var a : integer;
fun f(a : integer) : integer = 
(
    { for a = 0, a < 10, { a = a + 1 } : f(a) }
)
!expected:
Defs [1:1-5:2]
  VarDef [1:1-1:16]: a
    Atom [1:9-1:16]: INT
  FunDef [2:1-5:2]: f
    Parameter [2:7-2:18]: a
      Atom [2:11-2:18]: INT
    Atom [2:22-2:29]: INT
    Block [3:1-5:2]
      For [4:5-4:48]
        Name [4:11-4:12]: a
          # defined at: [2:7-2:18]
        Literal [4:15-4:16]: INT(0)
        Binary [4:18-4:24]: LT
          Name [4:18-4:19]: a
            # defined at: [2:7-2:18]
          Literal [4:22-4:24]: INT(10)
        Binary [4:26-4:39]: ASSIGN
          Name [4:28-4:29]: a
            # defined at: [2:7-2:18]
          Binary [4:32-4:37]: ADD
            Name [4:32-4:33]: a
              # defined at: [2:7-2:18]
            Literal [4:36-4:37]: INT(1)
        Call [4:42-4:46]: f
          # defined at: [2:1-5:2]
          Name [4:44-4:45]: a
            # defined at: [2:7-2:18]
!end

!code:
var a : integer;
fun f(a : integer) : integer = 
(
    { while a < 10 : f(a*10) }
)
!expected:
Defs [1:1-5:2]
  VarDef [1:1-1:16]: a
    Atom [1:9-1:16]: INT
  FunDef [2:1-5:2]: f
    Parameter [2:7-2:18]: a
      Atom [2:11-2:18]: INT
    Atom [2:22-2:29]: INT
    Block [3:1-5:2]
      While [4:5-4:31]
        Binary [4:13-4:19]: LT
          Name [4:13-4:14]: a
            # defined at: [2:7-2:18]
          Literal [4:17-4:19]: INT(10)
        Call [4:22-4:29]: f
          # defined at: [2:1-5:2]
          Binary [4:24-4:28]: MUL
            Name [4:24-4:25]: a
              # defined at: [2:7-2:18]
            Literal [4:26-4:28]: INT(10)
!end

!code:
var a : integer;
fun f(a : integer) : integer = 
(
   ((( a { where var a : integer }) { where var a : integer }) { where var a : int }) { where typ int : integer }
)
!expected:
Defs [1:1-5:2]
  VarDef [1:1-1:16]: a
    Atom [1:9-1:16]: INT
  FunDef [2:1-5:2]: f
    Parameter [2:7-2:18]: a
      Atom [2:11-2:18]: INT
    Atom [2:22-2:29]: INT
    Block [3:1-5:2]
      Where [4:4-4:114]
        Defs [4:95-4:112]
          TypeDef [4:95-4:112]: int
            Atom [4:105-4:112]: INT
        Block [4:4-4:86]
          Where [4:5-4:85]
            Defs [4:72-4:83]
              VarDef [4:72-4:83]: a
                TypeName [4:80-4:83]: int
                  # defined at: [4:95-4:112]
            Block [4:5-4:63]
              Where [4:6-4:62]
                Defs [4:45-4:60]
                  VarDef [4:45-4:60]: a
                    Atom [4:53-4:60]: INT
                Block [4:6-4:36]
                  Where [4:8-4:35]
                    Defs [4:18-4:33]
                      VarDef [4:18-4:33]: a
                        Atom [4:26-4:33]: INT
                    Name [4:8-4:9]: a
                      # defined at: [4:18-4:33]
!end

!code:
var a : integer;
fun f(a : integer) : integer = 
(
   ((( a { where var a : integer }) { where var a : integer }) { where var a : int })
)
!failure:
99
!end

!code:
var a : integer;
fun f(a : integer) : integer = 
(
   ((( a { where var a : integer }) { where var a : integer }) { where typ int : integer }) { where var a : int }
)
!failure:
99
!end

!code:
var a : arr[10] x
!failure:
99
!end

!code:
var a : arr[x] integer;
typ x : integer
!failure:
99
!end

!code:
var a : integer;
var b : arr[10] a
!failure:
99
!end

!code:
fun f(a : integer) : integer = 10;
var b : arr[10] f
!failure:
99
!end

!code:
typ a : integer;
fun f(a : a) : a = a
!expected:
Defs [1:1-2:21]
  TypeDef [1:1-1:16]: a
    Atom [1:9-1:16]: INT
  FunDef [2:1-2:21]: f
    Parameter [2:7-2:12]: a
      TypeName [2:11-2:12]: a
        # defined at: [1:1-1:16]
    TypeName [2:16-2:17]: a
      # defined at: [1:1-1:16]
    Name [2:20-2:21]: a
      # defined at: [2:7-2:12]
!end

!code:
typ x : x;
var y : x
!expected:
Defs [1:1-2:10]
  TypeDef [1:1-1:10]: x
    TypeName [1:9-1:10]: x
      # defined at: [1:1-1:10]
  VarDef [2:1-2:10]: y
    TypeName [2:9-2:10]: x
      # defined at: [1:1-1:10]
!end

!code:
typ x : logical;
fun f(x : x) : x = x { where var x : x }
!failure:
99
!end

!code:
typ y : integer;
var a : [10] integer;
fun f(var z : y) : integer = a[y]
!failure:
99
!end

!code:
typ y : integer;
var a : [10] integer;
fun f(var z : y) : integer = f[10]
!failure:
99
!end

!code:
typ y : integer;
var a : [10] integer;
fun f(var z : y) : integer = y[10]
!failure:
99
!end

!code:
typ y : integer;
var a : [10] integer;
fun f(var z : y) : integer = f(y)
!failure:
99
!end

!code:
typ y : integer;
var a : [10] integer;
fun f(var z : y) : integer = a(10)
!failure:
99
!end

!code:
typ y : integer;
var a : [10] integer;
fun f(var z : y) : integer = y(10)
!failure:
99
!end

!code:
var x : integer;
var y : x
!failure:
99
!end

!code:
fun x (p : integer) : integer = 10;
var y : x
!failure:
99
!end

!code:
typ x : integer;
var x : x
!failure:
99
!end

!code:
fun f (x : integer) : integer = f
!failure:
99
!end

!code:
typ y : integer;
fun f(x : integer) : integer = y
!failure:
99
!end

!code:
var a : integer;
fun f(a : integer) : integer = 
(
   ((( a { where var a : integer }) + a { where var a : integer }) + a { where var a : int }) { where typ int : integer }
)
!expected:
Defs [1:1-5:2]
  VarDef [1:1-1:16]: a
    Atom [1:9-1:16]: INT
  FunDef [2:1-5:2]: f
    Parameter [2:7-2:18]: a
      Atom [2:11-2:18]: INT
    Atom [2:22-2:29]: INT
    Block [3:1-5:2]
      Where [4:4-4:122]
        Defs [4:103-4:120]
          TypeDef [4:103-4:120]: int
            Atom [4:113-4:120]: INT
        Block [4:4-4:94]
          Where [4:5-4:93]
            Defs [4:80-4:91]
              VarDef [4:80-4:91]: a
                TypeName [4:88-4:91]: int
                  # defined at: [4:103-4:120]
            Binary [4:5-4:71]: ADD
              Block [4:5-4:67]
                Where [4:6-4:66]
                  Defs [4:49-4:64]
                    VarDef [4:49-4:64]: a
                      Atom [4:57-4:64]: INT
                  Binary [4:6-4:40]: ADD
                    Block [4:6-4:36]
                      Where [4:8-4:35]
                        Defs [4:18-4:33]
                          VarDef [4:18-4:33]: a
                            Atom [4:26-4:33]: INT
                        Name [4:8-4:9]: a
                          # defined at: [4:18-4:33]
                    Name [4:39-4:40]: a
                      # defined at: [4:49-4:64]
              Name [4:70-4:71]: a
                # defined at: [4:80-4:91]
!end