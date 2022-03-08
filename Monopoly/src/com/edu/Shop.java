/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/**
 * Shop class
 */
class Shop extends Cell{

    /**
     * Shop constructor
     */
    public Shop(){
        setName("S");

        // Below are randomly generated characteristics
        setN(MyRand.rndInt(50, 501));
        setK(getN() * MyRand.rndDouble(0.5, 0.9));
        setCompensationCoeff(MyRand.rndDouble(0.1, 1));
        setImprovementCoeff(MyRand.rndDouble(0.1, 2));

        // by default
        setHasOwner(false);
    }

    // cost of shop
    private double N;

    /**
     * Getter for cost N
     * @return cost N
     */
    public double getN() {
        return N;
    }

    /**
     * Setter for cost N
     * @param n new cost N
     */
    public void setN(double n) {
        N = n;
    }

    //compensation
    private double K;

    /**
     * Getter for K
     * @return K
     */
    public double getK() {
        return K;
    }

    /**
     * Setter for K
     * @param k new K
     */
    public void setK(double k) {
        K = k;
    }

    // to multiply for profit
    private double compensationCoeff;

    /**
     * Get Compensation
     * @return compensation coeff
     */
    public double getCompensationCoeff() {
        return compensationCoeff;
    }

    /**
     * Set new compensation
     * @param compensationCoeff
     */
    private void setCompensationCoeff(double compensationCoeff) {
        this.compensationCoeff = compensationCoeff;
    }

    // improv coeff
    private double improvementCoeff;

    /**
     * Getter for improv coeff
     * @return improve coeff
     */
    public double getImprovementCoeff() {
        return improvementCoeff;
    }

    /**
     * Setter for new improv coeff
     * @param improvementCoeff new improv coeff
     */
    private void setImprovementCoeff(double improvementCoeff) {
        this.improvementCoeff = improvementCoeff;
    }

    /**
     * Arriving at new cell, then calls overloaded method
     * @param p player who arrives
     */
    public void Arrives(Player p){
        super.Arrives(p);
        p.Action(this);
    }

    // has owner or not
    private boolean hasOwner;

    /**
     * returns yes if has owner
     * @return bool condition
     */
    public boolean getHasOwner() {
        return hasOwner;
    }

    /**
     * Set new owner
     * @param cond updating boolean
     */
    public void setHasOwner(boolean cond) {
        this.hasOwner = cond;
    }
}
