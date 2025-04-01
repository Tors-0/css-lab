package hw;

public class TemperatureDriver {
    public static void main(String[] args) {
        Temperature temp1 = new Temperature();
        Temperature temp2 = new Temperature(-40, 'F');
        Temperature temp3 = new Temperature(100);
        Temperature temp4 = new Temperature(37, 'C');

        getComparison(temp1);
        getComparison(temp2);
        getComparison(temp3);

        System.out.println("temp1 vs temp3: " + temp1.compareTo(temp3));

        getComparison(temp4);
        temp4.setScale('F');
        getComparison(temp4);
        temp4.setScale('C');
        getComparison(temp4);

        temp2.setTemp(90);
        System.out.println("temp2: " + temp2.toString());

        temp3.setTemperature(40, 'F');
        System.out.println("temp3: " + temp3.toString());
    }
    public static void getComparison(Temperature temp) {
        if (temp.getScale().toString().equalsIgnoreCase("F")) {
            System.out.println(temp + " equals " + temp.getDegreesC() + " C");
        } else {
            System.out.println(temp + " equals " + temp.getDegreesF() + " F");
        }
    }
}
