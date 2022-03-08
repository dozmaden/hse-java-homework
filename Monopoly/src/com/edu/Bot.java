/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/**
 * Bot class
 */
class Bot extends Player{
    /**
     * Bot constructor
     * @param m initial money
     */
    public Bot(int m){
        super(m);
        // set table name
        setUname("BOT");
    }

    /**
     * When bot comes to bank - he does nothing
     * @param b bank instance
     */
    @Override
    void Action(Bank b) {
        System.out.println("Good news! Bot can't do anything in the bank.");
    }

    /**
     * What bot does in shop
     * @param s shop instance
     */
    @Override
    void Action(Shop s) {
        if(!s.getHasOwner()){
            // randomly buyes if has money
            if (MyRand.rndInt(0, 2) == 0 && s.getN() < getMoney()){
                System.out.println("Bot buys the shop!.");
                s.setHasOwner(true);
                s.setName("O");
                setMoney(getMoney() - s.getN());
                setAbsolutemoney(getAbsolutemoney() + s.getN());
            }
        } else if (s.getHasOwner() && s.getName().equals("O") &&
                s.getN() * s.getImprovementCoeff() < getMoney()) {
            //randomly improves if has money
            if (MyRand.rndInt(0,2) == 0){
                System.out.println("Bot improves his shop!");
                setMoney(getMoney() - s.getN() * s.getImprovementCoeff());
                setAbsolutemoney(getAbsolutemoney() + s.getN() * s.getImprovementCoeff());
                s.setN(s.getN() + s.getImprovementCoeff() * s.getN());
                s.setK(s.getK() + s.getCompensationCoeff() * s.getK());
            }
        } else if (s.getHasOwner() && s.getName().equals("M")){
            System.out.printf("Good news! Bot got trapped in your shop and lost %.2f %n", s.getK());
            setMoney(getMoney() - s.getK());
            setOwedmoney(s.getK());
        }
    }
}
