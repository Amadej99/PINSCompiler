import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class TimeFunction {

    public static void timeFunction(Runnable function) {
        // Get ThreadMXBean for CPU time tracking
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // Check if CPU time measurement is supported
        if (!threadMXBean.isThreadCpuTimeSupported()) {
            System.out.println("CPU time measurement is not supported on this JVM.");
            return;
        }

        // Start measurements
        long startRealTime = System.nanoTime();
        long startCpuTime = threadMXBean.getCurrentThreadCpuTime();

        // Run the function
        function.run();

        // End measurements
        long endRealTime = System.nanoTime();
        long endCpuTime = threadMXBean.getCurrentThreadCpuTime();

        // Calculate elapsed times in seconds
        double realTime = (endRealTime - startRealTime) / 1_000_000_000.0;
        double cpuTime = (endCpuTime - startCpuTime) / 1_000_000_000.0;

        System.out.printf("Real time (wall clock): %.3f seconds\n", realTime);
        System.out.printf("CPU time: %.3f seconds\n", cpuTime);
    }
}