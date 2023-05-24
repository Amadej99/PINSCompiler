!compiler_flags: --dump FRM --exec FRM

!code:
fun f(i : integer) : integer = g(i)
{ where
    fun g(x : integer) : integer = x + i
}
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    # typed as: (int) -> int
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=8,parameters_size=8,size=12
    Parameter [1:7-1:18]: i
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Where [1:32-4:2]
      # typed as: int
      Defs [3:5-3:41]
        FunDef [3:5-3:41]: g
          # typed as: (int) -> int
          # framed as: FRAME [L[0]]: level=2,locals_size=0,arguments_size=0,parameters_size=8,size=8
          Parameter [3:11-3:22]: x
            # typed as: int
            # accessed as: Parameter: size[4],offset[4],sl[2]
            Atom [3:15-3:22]: INT
              # typed as: int
          Atom [3:26-3:33]: INT
            # typed as: int
          Binary [3:36-3:41]: ADD
            # typed as: int
            Name [3:36-3:37]: x
              # defined at: [3:11-3:22]
              # typed as: int
            Name [3:40-3:41]: i
              # defined at: [1:7-1:18]
              # typed as: int
      Call [1:32-1:36]: g
        # defined at: [3:5-3:41]
        # typed as: int
        Name [1:34-1:35]: i
          # defined at: [1:7-1:18]
          # typed as: int
!end

!code:
fun f(i : integer) : integer = g(i) 
{ where
    fun g(x : integer) : integer = x + i + z(x) 
    { where
        fun z(c : integer) : integer = c * 2
    };
    
    fun h(y : logical) : logical = false
}
!expected:
Defs [1:1-9:2]
  FunDef [1:1-9:2]: f
    # typed as: (int) -> int
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=8,parameters_size=8,size=12
    Parameter [1:7-1:18]: i
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Where [1:32-9:2]
      # typed as: int
      Defs [3:5-8:41]
        FunDef [3:5-6:6]: g
          # typed as: (int) -> int
          # framed as: FRAME [L[0]]: level=2,locals_size=0,arguments_size=8,parameters_size=8,size=12
          Parameter [3:11-3:22]: x
            # typed as: int
            # accessed as: Parameter: size[4],offset[4],sl[2]
            Atom [3:15-3:22]: INT
              # typed as: int
          Atom [3:26-3:33]: INT
            # typed as: int
          Where [3:36-6:6]
            # typed as: int
            Defs [5:9-5:45]
              FunDef [5:9-5:45]: z
                # typed as: (int) -> int
                # framed as: FRAME [L[1]]: level=3,locals_size=0,arguments_size=0,parameters_size=8,size=8
                Parameter [5:15-5:26]: c
                  # typed as: int
                  # accessed as: Parameter: size[4],offset[4],sl[3]
                  Atom [5:19-5:26]: INT
                    # typed as: int
                Atom [5:30-5:37]: INT
                  # typed as: int
                Binary [5:40-5:45]: MUL
                  # typed as: int
                  Name [5:40-5:41]: c
                    # defined at: [5:15-5:26]
                    # typed as: int
                  Literal [5:44-5:45]: INT(2)
                    # typed as: int
            Binary [3:36-3:48]: ADD
              # typed as: int
              Binary [3:36-3:41]: ADD
                # typed as: int
                Name [3:36-3:37]: x
                  # defined at: [3:11-3:22]
                  # typed as: int
                Name [3:40-3:41]: i
                  # defined at: [1:7-1:18]
                  # typed as: int
              Call [3:44-3:48]: z
                # defined at: [5:9-5:45]
                # typed as: int
                Name [3:46-3:47]: x
                  # defined at: [3:11-3:22]
                  # typed as: int
        FunDef [8:5-8:41]: h
          # typed as: (log) -> log
          # framed as: FRAME [L[2]]: level=2,locals_size=0,arguments_size=0,parameters_size=8,size=8
          Parameter [8:11-8:22]: y
            # typed as: log
            # accessed as: Parameter: size[4],offset[4],sl[2]
            Atom [8:15-8:22]: LOG
              # typed as: log
          Atom [8:26-8:33]: LOG
            # typed as: log
          Literal [8:36-8:41]: LOG(false)
            # typed as: log
      Call [1:32-1:36]: g
        # defined at: [3:5-6:6]
        # typed as: int
        Name [1:34-1:35]: i
          # defined at: [1:7-1:18]
          # typed as: int
!end

!code:
var a : integer;
var b : integer;
fun f(p : integer) : integer =
(
    c { where var c : integer }
)
!expected:
Defs [1:1-6:2]
  VarDef [1:1-1:16]: a
    # typed as: int
    # accessed as: Global: size[4],label[a]
    Atom [1:9-1:16]: INT
      # typed as: int
  VarDef [2:1-2:16]: b
    # typed as: int
    # accessed as: Global: size[4],label[b]
    Atom [2:9-2:16]: INT
      # typed as: int
  FunDef [3:1-6:2]: f
    # typed as: (int) -> int
    # framed as: FRAME [f]: level=1,locals_size=4,arguments_size=0,parameters_size=8,size=12
    Parameter [3:7-3:18]: p
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [3:11-3:18]: INT
        # typed as: int
    Atom [3:22-3:29]: INT
      # typed as: int
    Block [4:1-6:2]
      # typed as: int
      Where [5:5-5:32]
        # typed as: int
        Defs [5:15-5:30]
          VarDef [5:15-5:30]: c
            # typed as: int
            # accessed as: Local: size[4],offset[-4],sl[1]
            Atom [5:23-5:30]: INT
              # typed as: int
        Name [5:5-5:6]: c
          # defined at: [5:15-5:30]
          # typed as: int
!end

!code:
fun f(a : integer) : integer = 10;
fun g(b : integer) : integer = 11;
fun h(c : integer) : integer = 12
!expected:
Defs [1:1-3:34]
  FunDef [1:1-1:34]: f
    # typed as: (int) -> int
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [1:7-1:18]: a
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Literal [1:32-1:34]: INT(10)
      # typed as: int
  FunDef [2:1-2:34]: g
    # typed as: (int) -> int
    # framed as: FRAME [g]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [2:7-2:18]: b
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [2:11-2:18]: INT
        # typed as: int
    Atom [2:22-2:29]: INT
      # typed as: int
    Literal [2:32-2:34]: INT(11)
      # typed as: int
  FunDef [3:1-3:34]: h
    # typed as: (int) -> int
    # framed as: FRAME [h]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [3:7-3:18]: c
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [3:11-3:18]: INT
        # typed as: int
    Atom [3:22-3:29]: INT
      # typed as: int
    Literal [3:32-3:34]: INT(12)
      # typed as: int
!end

!code:
fun f(a : integer, b : integer, c : integer) : integer =
(
    g(a, b, c, 'niz')
    { where
        fun g(x1 : integer, x2 : integer, x3 : integer, x4 : string) : integer = 
        (
            x1 + x2 + x3 + x5 { where var x5 : integer }
        )
    }
)
!expected:
Defs [1:1-10:2]
  FunDef [1:1-10:2]: f
    # typed as: (int, int, int) -> int
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=20,parameters_size=16,size=24
    Parameter [1:7-1:18]: a
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Parameter [1:20-1:31]: b
      # typed as: int
      # accessed as: Parameter: size[4],offset[8],sl[1]
      Atom [1:24-1:31]: INT
        # typed as: int
    Parameter [1:33-1:44]: c
      # typed as: int
      # accessed as: Parameter: size[4],offset[12],sl[1]
      Atom [1:37-1:44]: INT
        # typed as: int
    Atom [1:48-1:55]: INT
      # typed as: int
    Block [2:1-10:2]
      # typed as: int
      Where [3:5-9:6]
        # typed as: int
        Defs [5:9-8:10]
          FunDef [5:9-8:10]: g
            # typed as: (int, int, int, str) -> int
            # framed as: FRAME [L[0]]: level=2,locals_size=4,arguments_size=0,parameters_size=20,size=12
            Parameter [5:15-5:27]: x1
              # typed as: int
              # accessed as: Parameter: size[4],offset[4],sl[2]
              Atom [5:20-5:27]: INT
                # typed as: int
            Parameter [5:29-5:41]: x2
              # typed as: int
              # accessed as: Parameter: size[4],offset[8],sl[2]
              Atom [5:34-5:41]: INT
                # typed as: int
            Parameter [5:43-5:55]: x3
              # typed as: int
              # accessed as: Parameter: size[4],offset[12],sl[2]
              Atom [5:48-5:55]: INT
                # typed as: int
            Parameter [5:57-5:68]: x4
              # typed as: str
              # accessed as: Parameter: size[4],offset[16],sl[2]
              Atom [5:62-5:68]: STR
                # typed as: str
            Atom [5:72-5:79]: INT
              # typed as: int
            Block [6:9-8:10]
              # typed as: int
              Where [7:13-7:57]
                # typed as: int
                Defs [7:39-7:55]
                  VarDef [7:39-7:55]: x5
                    # typed as: int
                    # accessed as: Local: size[4],offset[-4],sl[2]
                    Atom [7:48-7:55]: INT
                      # typed as: int
                Binary [7:13-7:30]: ADD
                  # typed as: int
                  Binary [7:13-7:25]: ADD
                    # typed as: int
                    Binary [7:13-7:20]: ADD
                      # typed as: int
                      Name [7:13-7:15]: x1
                        # defined at: [5:15-5:27]
                        # typed as: int
                      Name [7:18-7:20]: x2
                        # defined at: [5:29-5:41]
                        # typed as: int
                    Name [7:23-7:25]: x3
                      # defined at: [5:43-5:55]
                      # typed as: int
                  Name [7:28-7:30]: x5
                    # defined at: [7:39-7:55]
                    # typed as: int
        Call [3:5-3:22]: g
          # defined at: [5:9-8:10]
          # typed as: int
          Name [3:7-3:8]: a
            # defined at: [1:7-1:18]
            # typed as: int
          Name [3:10-3:11]: b
            # defined at: [1:20-1:31]
            # typed as: int
          Name [3:13-3:14]: c
            # defined at: [1:33-1:44]
            # typed as: int
          Literal [3:16-3:21]: STR(niz)
            # typed as: str
!end

!code:
fun f1(a : integer) : integer =
(
    (0 { where fun f2(b : integer) : integer = 0 }) { where fun f3(c : integer) : integer = 0 }
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f1
    # typed as: (int) -> int
    # framed as: FRAME [f1]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [1:8-1:19]: a
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:12-1:19]: INT
        # typed as: int
    Atom [1:23-1:30]: INT
      # typed as: int
    Block [2:1-4:2]
      # typed as: int
      Where [3:5-3:96]
        # typed as: int
        Defs [3:61-3:94]
          FunDef [3:61-3:94]: f3
            # typed as: (int) -> int
            # framed as: FRAME [L[0]]: level=2,locals_size=0,arguments_size=0,parameters_size=8,size=8
            Parameter [3:68-3:79]: c
              # typed as: int
              # accessed as: Parameter: size[4],offset[4],sl[2]
              Atom [3:72-3:79]: INT
                # typed as: int
            Atom [3:83-3:90]: INT
              # typed as: int
            Literal [3:93-3:94]: INT(0)
              # typed as: int
        Block [3:5-3:52]
          # typed as: int
          Where [3:6-3:51]
            # typed as: int
            Defs [3:16-3:49]
              FunDef [3:16-3:49]: f2
                # typed as: (int) -> int
                # framed as: FRAME [L[1]]: level=2,locals_size=0,arguments_size=0,parameters_size=8,size=8
                Parameter [3:23-3:34]: b
                  # typed as: int
                  # accessed as: Parameter: size[4],offset[4],sl[2]
                  Atom [3:27-3:34]: INT
                    # typed as: int
                Atom [3:38-3:45]: INT
                  # typed as: int
                Literal [3:48-3:49]: INT(0)
                  # typed as: int
            Literal [3:6-3:7]: INT(0)
              # typed as: int
!end

!code:
fun f(a : string) : string = f(a)
!expected:
Defs [1:1-1:34]
  FunDef [1:1-1:34]: f
    # typed as: (str) -> str
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=8,parameters_size=8,size=12
    Parameter [1:7-1:17]: a
      # typed as: str
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:17]: STR
        # typed as: str
    Atom [1:21-1:27]: STR
      # typed as: str
    Call [1:30-1:34]: f
      # defined at: [1:1-1:34]
      # typed as: str
      Name [1:32-1:33]: a
        # defined at: [1:7-1:17]
        # typed as: str
!end

!code:
var a : arr[10] string;
fun f(i : integer) : string = a[i]
!expected:
Defs [1:1-2:35]
  VarDef [1:1-1:23]: a
    # typed as: ARR(10,str)
    # accessed as: Global: size[40],label[a]
    Array [1:9-1:23]
      # typed as: ARR(10,str)
      [10]
      Atom [1:17-1:23]: STR
        # typed as: str
  FunDef [2:1-2:35]: f
    # typed as: (int) -> str
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [2:7-2:18]: i
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [2:11-2:18]: INT
        # typed as: int
    Atom [2:22-2:28]: STR
      # typed as: str
    Binary [2:31-2:35]: ARR
      # typed as: str
      Name [2:31-2:32]: a
        # defined at: [1:1-1:23]
        # typed as: ARR(10,str)
      Name [2:33-2:34]: i
        # defined at: [2:7-2:18]
        # typed as: int
!end

!code:
fun f(i : integer, a : arr[10] string) : string = a[i]
!expected:
Defs [1:1-1:55]
  FunDef [1:1-1:55]: f
    # typed as: (int, ARR(10,str)) -> str
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=0,parameters_size=12,size=8
    Parameter [1:7-1:18]: i
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Parameter [1:20-1:38]: a
      # typed as: ARR(10,str)
      # accessed as: Parameter: size[4],offset[8],sl[1]
      Array [1:24-1:38]
        # typed as: ARR(10,str)
        [10]
        Atom [1:32-1:38]: STR
          # typed as: str
    Atom [1:42-1:48]: STR
      # typed as: str
    Binary [1:51-1:55]: ARR
      # typed as: str
      Name [1:51-1:52]: a
        # defined at: [1:20-1:38]
        # typed as: ARR(10,str)
      Name [1:53-1:54]: i
        # defined at: [1:7-1:18]
        # typed as: int
!end

!code:
fun f(i : integer) : string =
(
    a[i] { where var a : arr [10] string }
)
!expected:
Defs [1:1-4:2]
  FunDef [1:1-4:2]: f
    # typed as: (int) -> str
    # framed as: FRAME [f]: level=1,locals_size=40,arguments_size=0,parameters_size=8,size=48
    Parameter [1:7-1:18]: i
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:28]: STR
      # typed as: str
    Block [2:1-4:2]
      # typed as: str
      Where [3:5-3:43]
        # typed as: str
        Defs [3:18-3:41]
          VarDef [3:18-3:41]: a
            # typed as: ARR(10,str)
            # accessed as: Local: size[40],offset[-40],sl[1]
            Array [3:26-3:41]
              # typed as: ARR(10,str)
              [10]
              Atom [3:35-3:41]: STR
                # typed as: str
        Binary [3:5-3:9]: ARR
          # typed as: str
          Name [3:5-3:6]: a
            # defined at: [3:18-3:41]
            # typed as: ARR(10,str)
          Name [3:7-3:8]: i
            # defined at: [1:7-1:18]
            # typed as: int
!end

!code:
fun f(a : integer) : integer = x { where var x : integer }
!expected:
Defs [1:1-1:59]
  FunDef [1:1-1:59]: f
    # typed as: (int) -> int
    # framed as: FRAME [f]: level=1,locals_size=4,arguments_size=0,parameters_size=8,size=12
    Parameter [1:7-1:18]: a
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Where [1:32-1:59]
      # typed as: int
      Defs [1:42-1:57]
        VarDef [1:42-1:57]: x
          # typed as: int
          # accessed as: Local: size[4],offset[-4],sl[1]
          Atom [1:50-1:57]: INT
            # typed as: int
      Name [1:32-1:33]: x
        # defined at: [1:42-1:57]
        # typed as: int
!end

!code:
var a : integer;
var b : integer;
var c : arr[10] integer
!expected:
Defs [1:1-3:24]
  VarDef [1:1-1:16]: a
    # typed as: int
    # accessed as: Global: size[4],label[a]
    Atom [1:9-1:16]: INT
      # typed as: int
  VarDef [2:1-2:16]: b
    # typed as: int
    # accessed as: Global: size[4],label[b]
    Atom [2:9-2:16]: INT
      # typed as: int
  VarDef [3:1-3:24]: c
    # typed as: ARR(10,int)
    # accessed as: Global: size[40],label[c]
    Array [3:9-3:24]
      # typed as: ARR(10,int)
      [10]
      Atom [3:17-3:24]: INT
        # typed as: int
!end

!code:
var a : arr[10] arr[10] string
!expected:
Defs [1:1-1:31]
  VarDef [1:1-1:31]: a
    # typed as: ARR(10,ARR(10,str))
    # accessed as: Global: size[400],label[a]
    Array [1:9-1:31]
      # typed as: ARR(10,ARR(10,str))
      [10]
      Array [1:17-1:31]
        # typed as: ARR(10,str)
        [10]
        Atom [1:25-1:31]: STR
          # typed as: str
!end

!code:
fun f1(a1 : integer) : integer =
(
  1 { where
    fun f2(a2 : integer) : integer =
    (
      2 { where 
        fun f3(a3 : integer) : integer = 1
        }
    )
  }
)
!expected:
Defs [1:1-11:2]
  FunDef [1:1-11:2]: f1
    # typed as: (int) -> int
    # framed as: FRAME [f1]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [1:8-1:20]: a1
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:13-1:20]: INT
        # typed as: int
    Atom [1:24-1:31]: INT
      # typed as: int
    Block [2:1-11:2]
      # typed as: int
      Where [3:3-10:4]
        # typed as: int
        Defs [4:5-9:6]
          FunDef [4:5-9:6]: f2
            # typed as: (int) -> int
            # framed as: FRAME [L[0]]: level=2,locals_size=0,arguments_size=0,parameters_size=8,size=8
            Parameter [4:12-4:24]: a2
              # typed as: int
              # accessed as: Parameter: size[4],offset[4],sl[2]
              Atom [4:17-4:24]: INT
                # typed as: int
            Atom [4:28-4:35]: INT
              # typed as: int
            Block [5:5-9:6]
              # typed as: int
              Where [6:7-8:10]
                # typed as: int
                Defs [7:9-7:43]
                  FunDef [7:9-7:43]: f3
                    # typed as: (int) -> int
                    # framed as: FRAME [L[1]]: level=3,locals_size=0,arguments_size=0,parameters_size=8,size=8
                    Parameter [7:16-7:28]: a3
                      # typed as: int
                      # accessed as: Parameter: size[4],offset[4],sl[3]
                      Atom [7:21-7:28]: INT
                        # typed as: int
                    Atom [7:32-7:39]: INT
                      # typed as: int
                    Literal [7:42-7:43]: INT(1)
                      # typed as: int
                Literal [6:7-6:8]: INT(2)
                  # typed as: int
        Literal [3:3-3:4]: INT(1)
          # typed as: int
!end

!code:
typ array : arr[10] arr[20] integer;
var a : array;
fun f(a : array) : integer = a[1][2]
!expected:
Defs [1:1-3:37]
  TypeDef [1:1-1:36]: array
    # typed as: ARR(10,ARR(20,int))
    Array [1:13-1:36]
      # typed as: ARR(10,ARR(20,int))
      [10]
      Array [1:21-1:36]
        # typed as: ARR(20,int)
        [20]
        Atom [1:29-1:36]: INT
          # typed as: int
  VarDef [2:1-2:14]: a
    # typed as: ARR(10,ARR(20,int))
    # accessed as: Global: size[800],label[a]
    TypeName [2:9-2:14]: array
      # defined at: [1:1-1:36]
      # typed as: ARR(10,ARR(20,int))
  FunDef [3:1-3:37]: f
    # typed as: (ARR(10,ARR(20,int))) -> int
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=0,parameters_size=8,size=8
    Parameter [3:7-3:16]: a
      # typed as: ARR(10,ARR(20,int))
      # accessed as: Parameter: size[4],offset[4],sl[1]
      TypeName [3:11-3:16]: array
        # defined at: [1:1-1:36]
        # typed as: ARR(10,ARR(20,int))
    Atom [3:20-3:27]: INT
      # typed as: int
    Binary [3:30-3:37]: ARR
      # typed as: int
      Binary [3:30-3:34]: ARR
        # typed as: ARR(20,int)
        Name [3:30-3:31]: a
          # defined at: [3:7-3:16]
          # typed as: ARR(10,ARR(20,int))
        Literal [3:32-3:33]: INT(1)
          # typed as: int
      Literal [3:35-3:36]: INT(2)
        # typed as: int
!end

!code:
fun f(a : integer) : integer = g(a);
fun g(b : integer) : integer = f(b)
!expected:
Defs [1:1-2:36]
  FunDef [1:1-1:36]: f
    # typed as: (int) -> int
    # framed as: FRAME [f]: level=1,locals_size=0,arguments_size=8,parameters_size=8,size=12
    Parameter [1:7-1:18]: a
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [1:11-1:18]: INT
        # typed as: int
    Atom [1:22-1:29]: INT
      # typed as: int
    Call [1:32-1:36]: g
      # defined at: [2:1-2:36]
      # typed as: int
      Name [1:34-1:35]: a
        # defined at: [1:7-1:18]
        # typed as: int
  FunDef [2:1-2:36]: g
    # typed as: (int) -> int
    # framed as: FRAME [g]: level=1,locals_size=0,arguments_size=8,parameters_size=8,size=12
    Parameter [2:7-2:18]: b
      # typed as: int
      # accessed as: Parameter: size[4],offset[4],sl[1]
      Atom [2:11-2:18]: INT
        # typed as: int
    Atom [2:22-2:29]: INT
      # typed as: int
    Call [2:32-2:36]: f
      # defined at: [1:1-1:36]
      # typed as: int
      Name [2:34-2:35]: b
        # defined at: [2:7-2:18]
        # typed as: int
!end