import os

java_dir = r"src"
class_dir = r"src/Main.java"
pins_dir = r"SYN"
jar_file = r"lib/ArgPar-0.1.jar"

classpath = f"{java_dir};{class_dir};{jar_file}"

compile_command = f"javac -d {class_dir} -classpath {classpath} {java_dir}\*.java > nul 2>&1"
compile_result = os.system(compile_command)
if compile_result != 0:
    print("Error compiling Java source files")
    exit()

for filename in os.listdir(pins_dir):
    if filename.endswith(".pins"):
        pins_file = os.path.join(pins_dir, filename)
        with open(pins_file, 'r') as f:
            first_line = f.readline()
        execute_command = f"java -classpath {classpath} Main PINS {pins_file} --dump SYN --exec SYN > nul 2>&1"
        result = os.system(execute_command)
        if result == 0:
            if "fail" in first_line:
                print(f"{filename:<20}{'Failed':>10}")
            elif "pass" in first_line:
                print(f"{filename:<20}{'Success':>10}")
        elif result !=0:
            if "fail" in first_line:
                print(f"{filename:<20}{'Success':>10}")
            elif "pass" in first_line:
                print(f"{filename:<20}{'Fail':>10}")