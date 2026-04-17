package dp.bench;

import dp.Knapsack;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(0)
@State(Scope.Benchmark)
public class KnapsackBench {

    public enum ProblemSize {
        SMALL(100, 1000),
        MEDIUM(500, 5000),
        LARGE(1000, 10000);

        final int n;
        final int W;

        ProblemSize(int n, int W) {
            this.n = n;
            this.W = W;
        }
    }

    @Param({"SMALL", "MEDIUM", "LARGE"})
    public ProblemSize size;

    private int n;
    private int W;

    private int[] weights;
    private int[] values;

    @Setup
    public void setup() {
        n = size.n;
        W = size.W;
        Random rng = new Random(42);
        weights = new int[n];
        values = new int[n];
        int maxWeight = Math.max(2, W / 4);

        for (int i = 0; i < n; i++) {
            weights[i] = rng.nextInt(maxWeight) + 1;
            values[i] = rng.nextInt(100) + 1;
        }
    }

    @Benchmark
    public int bench01() {
        return Knapsack.solve01(weights, values, W);
    }

    @Benchmark
    public int benchMemOpt() {
        return Knapsack.solveMemOpt(weights, values, W);
    }
}
