/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/*
Class for both bot and user player
 */
abstract class Player{
    /**
     * Player constructor
     * @param m money
     */
    public Player(int m){
        setMoney(m);

        // setting it by default for now
        setAbsolutemoney(0);
        setTaxiDistance(0);
        setOwedmoney(0);
    }

    // money of bot/player
    private double money;

    /**
     * Getter for money
     * @return double money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Setter for money
     * @param money money to fill
     */
    public void setMoney(double money) {
        this.money = money;
    }

    //name on the table
    private String uname;

    /**
     * Getter for name
     * @return name on the table
     */
    public String getUname() {
        return uname;
    }

    /**
     * Setter for name
     * @param uname name on the table
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    // To realize in child classes
    abstract void Action(Bank b);
    abstract void Action(Shop s);

    /**
     * Methods below are universal between bot/player
     * @param p penalty cell
     */
    public void Action(PenaltyCell p){
        System.out.printf("Bad luck! %s just lost %.2f money! %n",
                getUname(), Coeffs.getPenaltyCoeff() * getMoney());
        // Player loses money on penalty
        setMoney(getMoney() - getMoney() * Coeffs.getPenaltyCoeff());
    }

    /**
     * Method to report that you got in a taxi
     * @param t taxi instance
     */
    public void Action(Taxi t){
        // random taxi ride
        setTaxiDistance(MyRand.rndInt(3, 6));
        System.out.printf("%s shifted forward by %d cells %n", getUname(), getTaxiDistance());
    }

    /**
     * Empty cell logic
     * @param e empty instance
     */
    public void Action(EmptyCell e){
        System.out.printf("%s will relax here. %n", getUname());
    }

    // save the amount of taxi shifts
    private int taxiDistance;

    /**
     * Getter for taxi slides
     * @return cnt for table moves
     */
    public int getTaxiDistance() {
        return taxiDistance;
    }

    /**
     * Setting taxi distance
     * @param taxiDistance between 3 and 5
     */
    public void setTaxiDistance(int taxiDistance) {
        this.taxiDistance = taxiDistance;
    }

    // absolute money spent
    private double absolutemoney;

    /**
     * Getter for spent money;
     * @return spent money;
     */
    public double getAbsolutemoney() {
        return absolutemoney;
    }

    /**
     * Setter for spent money
     * @param moneyspent money from buys/improvs
     */
    public void setAbsolutemoney(double moneyspent) {
        this.absolutemoney = moneyspent;
    }

    // money owed to the other player
    private double owedmoney;

    /**
     * Getter for owed money
     * @return owed money
     */
    public double getOwedmoney() {
        return owedmoney;
    }

    /**
     * Setter for owed money
     * @param m charged money from shop
     */
    public void setOwedmoney(double m) {
        this.owedmoney = m;
    }
}
