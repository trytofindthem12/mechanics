import java.io.IOException;
import java.util.*;

public class Timetable {

    public static void main(String[] args) throws IOException {
        String studentFile = "kfu-s-93.stu";
        String courseFile = "kfu-s-93.crs";

        Map<Integer, List<Integer>> studentExams = DataParser.parseStudents(studentFile);
        Map<Integer, Integer> courses = DataParser.parseCourses(courseFile);

        int numExams = courses.size();
        int numTimeslots = 20; // Adjust this as necessary

        GeneticAlgorithm ga = new GeneticAlgorithm(numExams, numTimeslots, studentExams);
        List<int[]> population = ga.generateInitialPopulation(50);
        int[] bestTimetable = ga.geneticAlgorithm(population, 100);
        System.out.println("Best Timetable:");
        for (int i : bestTimetable) {
            System.out.print(i + " ");
        }

        // Train Hopfield Network with the best timetable
        HopfieldNetwork hn = new HopfieldNetwork(numExams);
        int[][] patterns = {bestTimetable}; // Use the best timetable as the training pattern
        hn.train(patterns);

        // Optimize the timetable using Hopfield Network
        int[] optimizedTimetable = hn.run(bestTimetable, 10);
        System.out.println("\nOptimized Timetable:");
        for (int i : optimizedTimetable) {
            System.out.print(i + " ");
        }
    }
}
