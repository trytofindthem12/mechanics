class Autoassociator {
    private int weights[][];
    private int trainingCapacity;

    public Autoassociator(CourseArray courses) {
        int numNeurons = courses.length();
        weights = new int[numNeurons][numNeurons];
        trainingCapacity = numNeurons;
        for (int i = 0; i < numNeurons; i++) {
            for (int j = 0; j < numNeurons; j++) {
                if (i != j) {
                    weights[i][j] = Math.random() > 0.5 ? 1 : -1;
                } else {
                    weights[i][j] = 0;
                }
            }
        }
    }

    public void training(int pattern[]) {
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (i != j) {
                    weights[i][j] += pattern[i] * pattern[j];
                }
            }
        }
    }

    public int unitUpdate(int neurons[]) {
        int index = (int) (Math.random() * neurons.length);
        int sum = 0;

        for (int i = 0; i < neurons.length; i++) {
            if (i != index) {
                sum += weights[i][index] * neurons[i];
            }
        }
        neurons[index] = sum >= 0 ? 1 : -1;

        return index;
    }

    public void unitUpdate(int neurons[], int index) {
        int sum = 0;
        for (int i = 0; i < neurons.length; i++) {
            if (i != index) {
                sum += weights[i][index] * neurons[i];
            }
        }

        neurons[index] = sum >= 0 ? 1 : -1;
    }

    public void chainUpdate(int neurons[], int steps) {
        for (int i = 0; i < steps; i++) {
            unitUpdate(neurons);
        }
    }

    public void fullUpdate(int neurons[]) {
        boolean stable = false;
        while (!stable) {
            int[] prevNeurons = neurons.clone();
            unitUpdate(neurons);
            stable = java.util.Arrays.equals(neurons, prevNeurons);
        }
    }
}

       
