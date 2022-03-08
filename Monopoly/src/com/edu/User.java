/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * User class, meaning your playable class
 */
class User extends Player{
    /**
     * Constructor for money and name
     * @param m initial money
     */
    public User(int m){
        super(m);
        setUname("YOU");
    }

    /**
     * Method for asking yes or no to user
     * @return bool indicating yes or no
     */
    public boolean YesOrNo(){
        String dec = "";
        boolean cond = false;
        while(!cond) {
            try{
                Scanner in = new Scanner(System.in);
                System.out.println("Input Yes or No: ");
                dec = in.nextLine();
                if(dec.equals("Yes") || dec.equals("No")){
                    cond = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Error! Please enter a string!");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return (dec.equals("Yes"));
    }

    /**
     * Action to take when user comes to bank
     * @param b
     */
    @Override
    void Action(Bank b) {
        // if you have debt
        if(b.getUserdebt() > 0){
            System.out.printf("Oh no! The bank requires your debt! You just lost %.2f amount of money %n",
                    b.getUserdebt());
            setMoney(getMoney()-b.getUserdebt());
            b.setUserdebt(0);
        } else if (b.getUserdebt() <= 0){
            // if you don't have debt
            System.out.println("You are in the bank office. Would you like to get a credit?");

            boolean yon = YesOrNo();

            if(yon){
                double debt = 0;
                while(debt <= 0 || debt > Coeffs.getCreditCoeff() * getAbsolutemoney()) {
                    // Can't get credit, if spent money is 0. Otherwise will be exception.
                    if(getAbsolutemoney() < 0.1){
                        System.out.println("You spent nothing on your shops! The Bank rejects the credit.");
                        return;
                    }
                    try{
                        Scanner in = new Scanner(System.in);
                        System.out.println("Input a number. ");
                        System.out.printf("Can't be higher then %.2f (creditcoeff * spentmoney). %n",
                                Coeffs.getCreditCoeff() * getAbsolutemoney());
                        debt = in.nextDouble();
                    } catch (InputMismatchException e){
                        System.out.println("Error! Please enter a double!");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                setMoney(getMoney() + debt);
                b.setUserdebt(debt*Coeffs.getDebtCoeff());
                System.out.printf("You now owe this bank %.2f %n", b.getUserdebt());
            } else{
                System.out.println("Understandable, have a nice day.");
            }
        }
    }

    /**
     * Action to take when user comes to shop
     * @param s shop instanceS
     */
    @Override
    void Action(Shop s) {
        if(!s.getHasOwner()){
            System.out.printf("You arrived in a shop with no owner. " +
                    "%n Would you like to pass it by or buy it for %.2f money?%n" +
                    "%n Input ‘Yes’ if you agree or ‘No’ otherwise%n", s.getN());

            boolean yon = YesOrNo();

            if (yon){
                s.setHasOwner(true);
                setMoney(getMoney() - s.getN());
                setAbsolutemoney(getAbsolutemoney() + s.getN());
                s.setName("M");
            } else{
                System.out.print("You pass it by.");
            }
        } else if (s.getHasOwner() && s.getName().equals("M")) {
            System.out.printf("You are in your shop. " +
                    "Would you like to upgrade it for %.2f? %n" +
                    "Input ‘Yes’ if you agree or ‘No’ otherwise %n", s.getN() * s.getImprovementCoeff());

            boolean yon = YesOrNo();

            if (yon){
                setMoney(getMoney() - s.getN() * s.getImprovementCoeff());
                setAbsolutemoney(getAbsolutemoney() + s.getN() * s.getImprovementCoeff());
                s.setN(s.getN() + s.getImprovementCoeff() * s.getN());
                s.setK(s.getK() + s.getCompensationCoeff() * s.getK());
                System.out.print("You improved your shop.");
            } else{
                System.out.print("You pass it by. ");
            }
        } else if (s.getHasOwner() && s.getName().equals("O")){
            System.out.printf("Bad luck! It's the opponent's shop. You need to pay %.2f ", s.getK());
            setMoney(getMoney() - s.getK());
            setOwedmoney(s.getK());
        }
    }
}
