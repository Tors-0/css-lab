import java.util.Scanner; //Importing Scanner

//Lab2A.java

public class Lab2A {

    static int secToHrFactor = 3600;
    static int secToMinFactor = 60;

    public static void main(String[] args){
        // kelvins and temperaturePrinter tests
        System.out.println("\nTesting kelvins and temperaturePrinter: ");
        double f1 = 32.00;
        temperaturePrinter(f1, kelvins(f1));

        // secondTime tests
        System.out.println("\nTesting secondTime:");

        Scanner sc = new Scanner(System.in);
        System.out.println("Please input total seconds as an integer");
        
        int val = sc.nextInt();            
        secondTime(val);

        // inSeconds tests
        System.out.println("\nTesting inSeconds:");
        inSeconds(1, 5, 1); // Expect: 3901

        System.out.println("\ntesting kelvins():");

        f1 = 54.52;
        temperaturePrinter(f1, kelvins(f1));

        f1 = 294.43;
        temperaturePrinter(f1, kelvins(f1));

        f1 = 1.04;
        temperaturePrinter(f1, kelvins(f1));

        f1 = 0;
        temperaturePrinter(f1, kelvins(f1));

        f1 = 3984.52;
        temperaturePrinter(f1, kelvins(f1));


        inSeconds( 0, 2, 0 ); // output: 120 seconds
        secondTime( 9001 ); // output: 02:30:01
        inSeconds( 24, 0, 0 ); // output: 86400 seconds
        secondTime( 3600 ); // output: 01:00:00
        inSeconds( -1, 61, -60 ); // output: 0 seconds

    }

    /**
     * Converts a temperature provided in Fahrenheit to Kelvins
     * @param f the temperature in Fahrenheit
     * @return the temperature in Kelvins
     */
    public static double kelvins(double f) {
        double fahrenheitToKelvinConversionFactor = 5.0/9.0;
        int fahrenheitOffset = 32;
        float kelvinOffset = 273.15f;

        return fahrenheitToKelvinConversionFactor * ( f - fahrenheitOffset ) + kelvinOffset;
    }
	
	// temperaturePrinter
     	// Prints the message "<f> F corresponds to <k> K"
	// f: the temperature in Farenheit
	// k: the temperature in Kelvins
    public static void temperaturePrinter(double f, double k) {
        System.out.printf("%.2f deg F equals %.2f deg. K %n", f, k);
    }

    
     	// secondTime
     	// Converts from seconds to hour:minutes:seconds, and prints the
     	// result to the console.
	// seconds: the time in seconds
    public static void secondTime(int seconds){
        int originalSecVal = seconds;

        int hours = seconds / secToHrFactor;
        seconds %= secToHrFactor;

        int min = seconds / secToMinFactor;
        seconds %= secToMinFactor;

        System.out.printf("%s seconds == %02d:%02d:%02d %n", originalSecVal, hours, min, seconds);
    }
	
	// inSeconds
     	// This method converts from hours:minutes:seconds to seconds, and prints the
	// result to the console.
    public static void inSeconds(int hr, int min, int sec) {
        int seconds = sec;

        seconds += min * secToMinFactor;
        seconds += hr * secToHrFactor;

        System.out.printf("%02d:%02d:%02d == %s seconds %n", hr, min, sec, seconds);
    }

}
