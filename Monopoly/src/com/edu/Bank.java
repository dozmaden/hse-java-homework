/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */


package com.edu;

/**
 * Bank class
 */
class Bank extends Cell{
    public Bank(){
        setName("$");

        //setting default debt
        setBotdebt(0);
        setUserdebt(0);
    }

    /**
     * Arrives at bank + player overloaded method
     * @param p player/bot
     */
    public void Arrives(Player p){
        super.Arrives(p);
        p.Action(this);
    }

    //debt of player
    private double userdebt;

    /**
     * Getter for user debt
     * @return debt
     */
    public double getUserdebt() {
        return userdebt;
    }

    /**
     * Setter for user debt
     * @param debt new debt from bank
     */
    public void setUserdebt(double debt) {
        this.userdebt = debt;
    }

    //debt of bot
    private double botdebt;

    /**
     * Getter for bot debt
     * @return debt
     */
    public double getBotdebt() {
        return botdebt;
    }

    /**
     * Setter for bot debt
     * @param debt new debt
     */
    public void setBotdebt(double debt) {
        this.botdebt = debt;
    }

}
