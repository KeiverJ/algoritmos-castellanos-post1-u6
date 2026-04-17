package dp;

import java.util.Objects;

public class Knapsack {

    /**
     * Knapsack 0/1 con tabla completa.
     *
     * @param weights peso de cada objeto
     * @param values valor de cada objeto
     * @param W capacidad máxima
     * @return valor óptimo alcanzable
     * Complejidad: O(n*W) tiempo, O(n*W) espacio.
     */
    public static int solve01(int[] weights, int[] values, int W) {
        validateInputs(weights, values, W);
        int n = weights.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            int wi = weights[i - 1];
            int vi = values[i - 1];
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wi <= w) {
                    dp[i][w] = Math.max(dp[i][w], vi + dp[i - 1][w - wi]);
                }
            }
        }
        return dp[n][W];
    }

    /**
     * Reconstruye los objetos seleccionados para Knapsack 0/1.
     *
     * @param weights peso de cada objeto
     * @param values valor de cada objeto
     * @param W capacidad máxima
     * @return arreglo booleano donde true indica objeto seleccionado
     * Complejidad: O(n*W) tiempo, O(n*W) espacio.
     */
    public static boolean[] reconstruct(int[] weights, int[] values, int W) {
        validateInputs(weights, values, W);
        int n = weights.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            int wi = weights[i - 1];
            int vi = values[i - 1];
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wi <= w) {
                    dp[i][w] = Math.max(dp[i][w], vi + dp[i - 1][w - wi]);
                }
            }
        }

        boolean[] selected = new boolean[n];
        int w = W;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected[i - 1] = true;
                w -= weights[i - 1];
            }
        }
        return selected;
    }

    /**
     * Unbounded Knapsack usando DP 1D.
     *
     * @param weights peso de cada objeto
     * @param values valor de cada objeto
     * @param W capacidad máxima
     * @return valor óptimo permitiendo repetir objetos
     * Complejidad: O(n*W) tiempo, O(W) espacio.
     */
    public static int solveUnbounded(int[] weights, int[] values, int W) {
        validateInputs(weights, values, W);
        int[] dp = new int[W + 1];

        for (int w = 0; w <= W; w++) {
            for (int i = 0; i < weights.length; i++) {
                int wi = weights[i];
                int vi = values[i];
                if (wi <= w) {
                    dp[w] = Math.max(dp[w], vi + dp[w - wi]);
                }
            }
        }
        return dp[W];
    }

    /**
     * Knapsack 0/1 optimizado en memoria con DP 1D.
     *
     * @param weights peso de cada objeto
     * @param values valor de cada objeto
     * @param W capacidad máxima
     * @return valor óptimo sin repetir objetos
     * Complejidad: O(n*W) tiempo, O(W) espacio.
     */
    public static int solveMemOpt(int[] weights, int[] values, int W) {
        validateInputs(weights, values, W);
        int[] dp = new int[W + 1];

        for (int i = 0; i < weights.length; i++) {
            int wi = weights[i];
            int vi = values[i];
            for (int w = W; w >= wi; w--) {
                dp[w] = Math.max(dp[w], vi + dp[w - wi]);
            }
        }
        return dp[W];
    }

    private static void validateInputs(int[] weights, int[] values, int W) {
        Objects.requireNonNull(weights, "weights no puede ser null");
        Objects.requireNonNull(values, "values no puede ser null");
        if (weights.length != values.length) {
            throw new IllegalArgumentException("weights y values deben tener la misma longitud");
        }
        if (W < 0) {
            throw new IllegalArgumentException("W no puede ser negativa");
        }
        for (int w : weights) {
            if (w <= 0) {
                throw new IllegalArgumentException("todos los pesos deben ser positivos");
            }
        }
    }
}
