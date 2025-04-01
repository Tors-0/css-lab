public class Race {
    double[] arr;

    public Race(double[] array) {
        arr = new double[array.length];
        // demonstrate that we know how to copy an array manually
        for (int i = 0; i < array.length; i++) {
            arr[i] = array[i];
        }

        // sort the races from fastest to shortest
        while (!isSorted()) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    double temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    private boolean isSorted() {
        for (int i = 0; i < arr.length - 1; i++) {
            if (!(arr[i] < arr[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public double[] getTimes() {
        double[] out = new double[arr.length];
        // but arraycopy is so much nicer to write (see line 6)
        System.arraycopy(arr, 0, out, 0, arr.length);
        return out;
    }
}
