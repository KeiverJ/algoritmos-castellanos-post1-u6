package dp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchedulingTest {

    @Test
    void solveWithSingleJobReturnsItsValue() {
        List<WeightedScheduling.Job> jobs = List.of(new WeightedScheduling.Job(1, 3, 50));

        assertEquals(50, WeightedScheduling.solve(jobs));
    }

    @Test
    void solveWhenAllJobsCompatibleThenTakeAll() {
        List<WeightedScheduling.Job> jobs = List.of(
                new WeightedScheduling.Job(1, 2, 10),
                new WeightedScheduling.Job(2, 4, 20),
                new WeightedScheduling.Job(4, 7, 30)
        );

        assertEquals(60, WeightedScheduling.solve(jobs));
    }

    @Test
    void solveCanBeatNaiveEarliestFinishGreedyChoice() {
        List<WeightedScheduling.Job> jobs = List.of(
                new WeightedScheduling.Job(1, 2, 5),
                new WeightedScheduling.Job(2, 3, 5),
                new WeightedScheduling.Job(3, 4, 5),
                new WeightedScheduling.Job(1, 4, 20)
        );

        assertEquals(20, WeightedScheduling.solve(jobs));
    }

    @Test
    void solveWithAllOverlappingJobsChoosesBestSingleJob() {
        List<WeightedScheduling.Job> jobs = List.of(
                new WeightedScheduling.Job(1, 5, 10),
                new WeightedScheduling.Job(2, 6, 30),
                new WeightedScheduling.Job(3, 7, 25)
        );

        assertEquals(30, WeightedScheduling.solve(jobs));
    }

    @Test
    void solveWithNoJobsReturnsZero() {
        assertEquals(0, WeightedScheduling.solve(List.of()));
    }

    @Test
    void reconstructJobsMatchesOptimalValue() {
        List<WeightedScheduling.Job> jobs = List.of(
                new WeightedScheduling.Job(1, 3, 5),
                new WeightedScheduling.Job(2, 5, 6),
                new WeightedScheduling.Job(4, 6, 5),
                new WeightedScheduling.Job(6, 7, 4),
                new WeightedScheduling.Job(5, 8, 11),
                new WeightedScheduling.Job(7, 9, 2)
        );

        int optimal = WeightedScheduling.solve(jobs);
        List<WeightedScheduling.Job> chosen = WeightedScheduling.reconstructJobs(jobs);
        int sum = chosen.stream().mapToInt(WeightedScheduling.Job::value).sum();

        assertEquals(optimal, sum);
        assertEquals(17, optimal);
    }
}
