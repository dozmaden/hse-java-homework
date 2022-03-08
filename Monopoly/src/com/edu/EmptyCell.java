/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/**
 * Empty cell class
 */
class EmptyCell extends Cell{

    /**
     * Empty cell constructor
     */
    public EmptyCell(){
        // setting name
        setName("E");
    }

    /**
     * Player arriving at empty cells + calls overloaded method of player
     * @param p player/bot
     */
    public void Arrives(Player p){
        super.Arrives(p);
        p.Action(this);
    }
}
