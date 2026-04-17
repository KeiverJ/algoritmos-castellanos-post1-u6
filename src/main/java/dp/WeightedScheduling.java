package dp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WeightedScheduling {

    public record Job(int start, int finish, int value) {
        public Job {
            if (start < 0 || finish < 0 || value < 0) {
                throw new IllegalArgumentException("start, finish y value deben ser >= 0");
            }
            if (finish < start) {
                throw new IllegalArgumentException("finish debe ser mayor o igual que start");
            }
        }
    }

    /**
     * Resuelve Weighted Interval Scheduling con DP y p(j) por búsqueda binaria.
     *
     * @param jobs lista de trabajos (start, finish, value)
     * @return valor óptimo de trabajos compatibles
     * Complejidad: O(n log n) tiempo, O(n) espacio.
     */
    public static int solve(List<Job> jobs) {
        List<Job> sorted = sortByFinish(jobs);
        int n = sorted.size();
        int[] p = computeP(sorted);
        int[] dp = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            int include = sorted.get(j - 1).value() + dp[p[j]];
            int exclude = dp[j - 1];
            dp[j] = Math.max(include, exclude);
        }
        return dp[n];
    }

    /**
     * Reconstruye los trabajos seleccionados en la solución óptima.
     *
     * @param jobs lista de trabajos (start, finish, value)
     * @return lista de trabajos óptimos en orden ascendente por finish
     * Complejidad: O(n log n) tiempo, O(n) espacio.
     */
    public static List<Job> reconstructJobs(List<Job> jobs) {
        List<Job> sorted = sortByFinish(jobs);
        int n = sorted.size();
        int[] p = computeP(sorted);
        int[] dp = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            int include = sorted.get(j - 1).value() + dp[p[j]];
            int exclude = dp[j - 1];
            dp[j] = Math.max(include, exclude);
        }

        List<Job> chosen = new ArrayList<>();
        int j = n;
        while (j > 0) {
            int include = sorted.get(j - 1).value() + dp[p[j]];
            if (include >= dp[j - 1]) {
                chosen.add(sorted.get(j - 1));
                j = p[j];
            } else {
                j--;
            }
        }

        chosen.sort(Comparator.comparingInt(Job::finish));
        return chosen;
    }

    /**
     * p[j] es el índice i (0..j-1) del último trabajo compatible con j.
     *
     * @param jobs lista ordenada por finish
     * @return arreglo p con base 1 para uso directo en DP
     * Complejidad: O(n log n) tiempo, O(n) espacio.
     */
    static int[] computeP(List<Job> jobs) {
        int n = jobs.size();
        int[] finishes = jobs.stream().mapToInt(Job::finish).toArray();
        int[] p = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            int startJ = jobs.get(j - 1).start();
            int lo = 0;
            int hi = j - 1;

            while (lo < hi) {
                int mid = (lo + hi + 1) >>> 1;
                if (finishes[mid - 1] <= startJ) {
                    lo = mid;
                } else {
                    hi = mid - 1;
                }
            }
            p[j] = lo;
        }
        return p;
    }

    private static List<Job> sortByFinish(List<Job> jobs) {
        List<Job> sorted = new ArrayList<>(jobs);
        sorted.sort(Comparator.comparingInt(Job::finish));
        return sorted;
    }
}
