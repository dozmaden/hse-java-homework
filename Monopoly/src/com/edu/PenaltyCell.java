/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/*
Class for penalty logic.
 */
class PenaltyCell extends Cell{
    /**
     * Penalty constructor
     */
    public PenaltyCell(){
        setName("%");
    }

    /**
    Parent arrives + overloaded method of player
     */
    public void Arrives(Player p){
        super.Arrives(p);
        p.Action(this);
    }
}
