package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KnapsackTest {

    @Test
    void solve01WhenCapacityZeroThenResultIsZero() {
        int[] weights = {1, 2, 3};
        int[] values = {10, 15, 40};

        assertEquals(0, Knapsack.solve01(weights, values, 0));
        assertEquals(0, Knapsack.solveMemOpt(weights, values, 0));
        assertEquals(0, Knapsack.solveUnbounded(weights, values, 0));
    }

    @Test
    void solve01WhenNoItemFitsThenResultIsZero() {
        int[] weights = {5, 6, 7};
        int[] values = {10, 20, 30};

        assertEquals(0, Knapsack.solve01(weights, values, 4));
    }

    @Test
    void solve01WhenAllItemsFitThenTakeAll() {
        int[] weights = {1, 2, 3};
        int[] values = {10, 20, 30};

        assertEquals(60, Knapsack.solve01(weights, values, 10));
    }

    @Test
    void solve01OptimalDoesNotIncludeHighestValueItem() {
        int[] weights = {10, 6, 4};
        int[] values = {100, 60, 41};

        assertEquals(101, Knapsack.solve01(weights, values, 10));
        assertArrayEquals(new boolean[]{false, true, true}, Knapsack.reconstruct(weights, values, 10));
    }

    @Test
    void solveMemOptMatchesSolve01ForSameInput() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 8};
        int W = 10;

        assertEquals(Knapsack.solve01(weights, values, W), Knapsack.solveMemOpt(weights, values, W));
    }

    @Test
    void solveUnboundedCanReuseItems() {
        int[] weights = {5, 7};
        int[] values = {10, 13};

        assertEquals(20, Knapsack.solveUnbounded(weights, values, 10));
    }
}
