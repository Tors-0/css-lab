package hw;

public class Temperature {
    private float temp;
    private TempScale scale;

    // constructors
    public Temperature() {
        temp = 0;
        scale = TempScale.C;
    }
    public Temperature(float temp) {
        this.temp = temp;
        this.scale = TempScale.C;
    }
    public Temperature(float temp, TempScale scale) {
        this.temp = temp;
        this.scale = scale;
    }
    public Temperature(float temp, char scale) {
        this.temp = temp;
        this.scale = TempScale.valueOf(String.valueOf(scale));
    }
    // end constructors

    // accessor methods
    public float getDegreesC() {
        if (scale == TempScale.C) {
            return temp;
        } else {
            return 5 * (temp - 32) / 9;
        }
    }
    public float getDegreesF() {
        if (scale == TempScale.F) {
            return temp;
        } else {
            return ( 9 * temp / 5) + 32;
        }
    }
    public TempScale getScale() {
        return scale;
    }
    // end accessor methods

    // mutator methods
    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setScale(TempScale scale) {
        this.scale = scale;
    }
    public void setScale(char scale) {
        this.scale = TempScale.valueOf(String.valueOf(scale));
    }

    public void setTemperature(float temp, char scale) {
        this.temp = temp;
        this.scale = TempScale.valueOf(String.valueOf(scale));
    }
    // end mutator methods

    public int compareTo(Temperature other) {
        if (this.scale == other.scale) {
            return Float.compare(this.temp, other.temp);
        } else {
            return Float.compare(this.getDegreesC(), other.getDegreesC());
        }
    }

    @Override
    public String toString() {
        return String.format("%.1f %s", this.temp, this.scale);
    }


    /**
     * Enum values for temperature scales
     */
    public enum TempScale {
        F,
        C;
    }
}