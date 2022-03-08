/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/**
 * Random coeffs that generate with table
 */
class Coeffs{

    // credit limit
    private static final double creditCoeff = MyRand.rndDouble(0.002, 0.2);;

    /**
     * Getter for credit
     * @return credit limit
     */
    public static double getCreditCoeff() {
        return creditCoeff;
    }

    // debt interest
    private static final double debtCoeff = MyRand.rndDouble(1.0, 3.0);

    /**
     * Getter for debt interest
     * @return debt interest rate
     */
    public static double getDebtCoeff() {
        return debtCoeff;
    }

    //penalty at penalty cell
    private static final double penaltyCoeff = (Math.random() + 0.00001) / 10;

    /**
     * Getter for penalty rate
     * @return penalty
     */
    public static double getPenaltyCoeff() {
        return penaltyCoeff;
    }
}
