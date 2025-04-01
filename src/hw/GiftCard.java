package hw;

/**
 * hw.GiftCard.java
 * CSS142
 * 
 * Skeleton file for a basic giftcard class; useful to set, report, and
 * manage deductions
 */

public class GiftCard {
    //todo: class-level instance variables go here
    private double currentBalance;


    // Constructor
    public GiftCard(double balance) {
        this.currentBalance = balance;
    }
    public GiftCard() {
        this.currentBalance = 0;
    }
     

    //an example method that needs instance data to be declared first
    public void resetToZero() {
        currentBalance = 0;
        System.out.println("resetter to zero: " + currentBalance + "\n");
    }
    public void setBalance(double amount) {
        if (amount < 0) {
            System.out.println("Error: negative balance");
            return;
        }

        this.currentBalance = amount;
    }
    public void report() {
        System.out.println("$" + currentBalance);
    }
    public void deduct(double amount) {
        if (amount > currentBalance) {
            System.out.println("Error: negative balance");
            return;
        }

        this.currentBalance -= amount;
    }
    
    
    public static void main(String[] args) {
        GiftCard card1 = new GiftCard();
        
        card1.setBalance(50);
        
        System.out.print("card1 value is: ");
        card1.report();
        
        GiftCard card2 = new GiftCard();
        
        card2.setBalance(100);
        System.out.print("card2 value is: ");
        card2.report();
        
        card2.deduct(101);
        System.out.println(" After deducting $101, card2's new balance is :");
        card2.report();
        
        card1.deduct(12.50);
        System.out.println(" After deducting $12.50, card1's new balance is :");
        card1.report();
        
        card1.resetToZero();
        card1.report();
        card2.resetToZero();
        card2.report();
        
        // User defined constructor
        GiftCard card3 = new GiftCard(5000);
        System.out.println("\nSetting the new balance to 7500.99 ");
        card3.setBalance(7500.99);
    }

}
