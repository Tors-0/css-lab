import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private String size;
    private int meatNum;
    private int veggieNum;
    private boolean vegan;

    private static final List<String> validSizes = new ArrayList<>();
    static {
        validSizes.add("small");
        validSizes.add("medium");
        validSizes.add("large");
    }

    public Pizza() {
        size = "medium";
        meatNum = 0;
        veggieNum = 0;
        vegan = false;
    }

    public Pizza(String size, int meatToppings, int veggieToppings, boolean vegan) {
        if (validSizes.contains(size)) {
            this.size = size;
        } else {
            throw new IllegalArgumentException(
                    "Invalid pizza size: " + size);
        }

        this.meatNum = meatToppings;
        this.veggieNum = veggieToppings;

        if (vegan && meatToppings > 0) {
            throw new IllegalArgumentException(
                    "Vegan pizza cannot have meat: " + meatToppings);
        } else {
            this.vegan = vegan;
        }
    }


    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        if (vegan && meatNum > 0) {
            throw new IllegalArgumentException(
                    "Vegan pizza cannot have meat: " + meatNum);
        } else {
            this.vegan = vegan;
        }
    }

    public int getVeg() {
        return veggieNum;
    }

    public void setVeggieNum(int veggieNum) {
        this.veggieNum = veggieNum;
    }

    public int getMeat() {
        return meatNum;
    }

    public void setMeatNum(int meatNum) {
        if (this.vegan) {
            throw new IllegalArgumentException(
                    "Vegan pizza cannot have meat: " + meatNum);
        } else {
            this.meatNum = meatNum;
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (validSizes.contains(size)) {
            this.size = size;
        } else {
            throw new IllegalArgumentException(
                    "Invalid pizza size: " + size);
        }
    }

    public double calcCost() {
        double cost = 0;
        switch (size) {
            case "small" -> cost += 10;
            case "medium" -> cost += 12;
            case "large" -> cost += 14;
        }

        cost += (meatNum * 2);
        cost += (veggieNum);

        if (vegan) {
            cost += 2;
        }

        return cost;
    }

    public String getDescription() {
        return String.format(
                "A %s pizza with %d meat toppings, %d veggie toppings, and %s vegan-friendly.",
                size,
                meatNum,
                veggieNum,
                vegan ? "is" : "isn't"
        );
    }
}
