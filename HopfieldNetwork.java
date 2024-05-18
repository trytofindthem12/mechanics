import java.util.Arrays;

public class HopfieldNetwork {
    private int[][] weights;
    private int numNeurons;

    public HopfieldNetwork(int numNeurons) {
        this.numNeurons = numNeurons;
        this.weights = new int[numNeurons][numNeurons];
    }

    public void train(int[][] patterns) {
        for (int[] pattern : patterns) {
            for (int i = 0; i < numNeurons; i++) {
                for (int j = 0; j < numNeurons; j++) {
                    if (i != j) {
                        weights[i][j] += pattern[i] * pattern[j];
                    }
                }
            }
        }
    }

    public int[] run(int[] pattern, int steps) {
        int[] result = Arrays.copyOf(pattern, pattern.length);
        for (int step = 0; step < steps; step++) {
            for (int i = 0; i < numNeurons; i++) {
                int sum = 0;
                for (int j = 0; j < numNeurons; j++) {
                    sum += weights[i][j] * result[j];
                }
                result[i] = sum >= 0 ? 1 : -1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int numNeurons = 10;
        int[][] patterns = {
            // Sample data, replace with actual data from kfu-s-93.stu
            {1, -1, 1, -1, 1, -1, 1, -1, 1, -1},
            {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1}
        };

        HopfieldNetwork hn = new HopfieldNetwork(numNeurons);
        hn.train(patterns);

        int[] initialPattern = {1, 1, 1, 1, 1, -1, -1, -1, -1, -1}; // Replace with actual data
        int[] result = hn.run(initialPattern, 10);

        System.out.println("Optimized Pattern:");
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
