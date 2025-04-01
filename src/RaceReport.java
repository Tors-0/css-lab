public class RaceReport {
    double[] orderedRaceTimes;

    public RaceReport(double[] inputArr) {
        orderedRaceTimes = new double[inputArr.length];
        System.arraycopy(inputArr, 0, orderedRaceTimes, 0, inputArr.length);
    }

    public void writeReport() {
        
    }
}
