import java.util.*;

public class GeneticAlgorithm {

    private int numExams;
    private int numTimeslots;
    private Map<Integer, List<Integer>> studentsExams;

    public GeneticAlgorithm(int numExams, int numTimeslots, Map<Integer, List<Integer>> studentsExams) {
        this.numExams = numExams;
        this.numTimeslots = numTimeslots;
        this.studentsExams = studentsExams;
    }

    public int evaluateTimetable(int[] timetable) {
        int clashes = 0;
        for (List<Integer> student : studentsExams.values()) {
            Set<Integer> timeslots = new HashSet<>();
            for (int exam : student) {
                timeslots.add(timetable[exam]);
            }
            clashes += student.size() - timeslots.size();
        }
        return clashes;
    }

    public List<int[]> generateInitialPopulation(int size) {
        List<int[]> population = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            int[] timetable = new int[numExams];
            for (int j = 0; j < numExams; j++) {
                timetable[j] = rand.nextInt(numTimeslots);
            }
            population.add(timetable);
        }
        return population;
    }

    public List<int[]> select(List<int[]> population) {
        population.sort(Comparator.comparingInt(this::evaluateTimetable));
        return new ArrayList<>(population.subList(0, population.size() / 2));
    }

    public int[] crossover(int[] parent1, int[] parent2) {
        Random rand = new Random();
        int crossoverPoint = rand.nextInt(numExams);
        int[] child = new int[numExams];
        System.arraycopy(parent1, 0, child, 0, crossoverPoint);
        System.arraycopy(parent2, crossoverPoint, child, crossoverPoint, numExams - crossoverPoint);
        return child;
    }

    public int[] mutate(int[] timetable) {
        Random rand = new Random();
        int exam = rand.nextInt(numExams);
        int newTimeslot = rand.nextInt(numTimeslots);
        timetable[exam] = newTimeslot;
        return timetable;
    }

    public int[] geneticAlgorithm(List<int[]> population, int generations) {
        for (int generation = 0; generation < generations; generation++) {
            List<int[]> selected = select(population);
            List<int[]> nextGeneration = new ArrayList<>();
            while (nextGeneration.size() < population.size()) {
                int[] parent1 = selected.get(new Random().nextInt(selected.size()));
                int[] parent2 = selected.get(new Random().nextInt(selected.size()));
                int[] child1 = crossover(parent1, parent2);
                int[] child2 = crossover(parent2, parent1);
                nextGeneration.add(mutate(child1));
                nextGeneration.add(mutate(child2));
            }
            population = nextGeneration;
            int[] bestTimetable = Collections.min(population, Comparator.comparingInt(this::evaluateTimetable));
            System.out.println("Generation " + generation + ": Best fitness = " + evaluateTimetable(bestTimetable));
        }
        return Collections.min(population, Comparator.comparingInt(this::evaluateTimetable));
    }
}
