/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/**
 * Taxi class
 */
class Taxi extends Cell{
    /**
     * Setting only name in constructor
     */
    public Taxi(){
        setName("T");
    }

    /**
     * Arriving in taxi + calls overloaded method
     * @param p
     */
    public void Arrives(Player p){
        super.Arrives(p);
        p.Action(this);
    }
}
